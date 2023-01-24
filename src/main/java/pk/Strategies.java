package pk;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;

public class Strategies {

    private static int reRollCount;

    /*private static int factorial(int n){
        for(int i = n-1; i > 0; i--){
            n = n*i;
        }
        if(n == 0){
            return 1;
        }
        return n;
    }*/


    public static Function<Player, Integer[]> combo = (player) -> {

        reRollCount = 0;

        Integer[] pickedIndices = new Integer[8];

        Arrays.fill(pickedIndices, 8);

        ArrayList<Integer> reRollFaces = new ArrayList<>(3);

        int[] faceCounts = Score.faceCounts(player);


        if(Arrays.stream(faceCounts).noneMatch(n->(n==1)) && !Arrays.stream(faceCounts).noneMatch(n->(n==2))) {
            player.reRoll = false;
            return pickedIndices;
        }
        else {
            for(int i = 0; i < faceCounts.length; i++){
                if ((faceCounts[i] == 1 || faceCounts[i] == 2) && !(i == Faces.GOLD.ordinal() || i == Faces.DIAMOND.ordinal())) {
                    reRollCount += faceCounts[i];

                    reRollFaces.add(i);
                }
            }

            if (reRollCount < 2) {
                player.reRoll = false;
                return pickedIndices;
            }

            for (int i = 0; i < 8; i++) {
                if (reRollFaces.contains(player.roll[i].ordinal())) {
                    pickedIndices[i] = i;
                }
            }

            /*int nCk = (factorial(reRollCount)) / ( (factorial(3- player.skulls)) * (factorial(reRollCount-(3- player.skulls))) );

            double lossProbability = Math.pow((1/6),(3 - player.skulls))*Math.pow((5/6),(reRollCount-(3-player.skulls)))*nCk;

            if(lossProbability < 0.5){
                player.reRoll = true;
                return pickedIndices;
            }

            player.reRoll = false;
            return pickedIndices;*/

            player.reRoll = true;
            return pickedIndices;
        }
    };

    public static Function<Player, Integer[]> random = (player) -> {

        Random rnd = new Random();

        int diceToRoll = rnd.nextInt(2,9-player.skulls);

        Integer[] pickedIndices = new Integer[diceToRoll];
        Arrays.fill(pickedIndices, 9);

        for(int i = 0; i < diceToRoll; i++){
            int pick = rnd.nextInt(8);
            if(Arrays.stream(pickedIndices).anyMatch(n->(n==pick)) || player.roll[pick] == Faces.SKULL){
                i--;
            }
            else{
                pickedIndices[i] = pick;
            }
        }

        player.reRoll = rnd.nextBoolean();

        return pickedIndices;

    };


    public static void main(String[] args) {

        /*Player player = new Player("Player 1",Strategies.random);

        player.roll = new Faces[]{Faces.DIAMOND, Faces.MONKEY, Faces.SABER, Faces.SKULL, Faces.SABER, Faces.PARROT, Faces.PARROT, Faces.PARROT};

        combo.apply(player);*/

    }


}
