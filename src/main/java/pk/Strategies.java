package pk;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Strategies {

    private static int reRollCount;

    public static Function<Player, Integer[]> combo = (player) -> {

        reRollCount = 0;

        Integer[] pickedIndices = new Integer[8];

        Arrays.fill(pickedIndices, 8);

        ArrayList<Integer> reRollFaces = new ArrayList<>(3);

        int[] faceCounts = Score.faceCounts(player);


        if(Arrays.stream(faceCounts).noneMatch(n->(n==1)) && !Arrays.stream(faceCounts).noneMatch(n->(n==2))) {
            //player.reRoll = false;
            player.setReRoll(false);
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
                //player.reRoll = false;
                player.setReRoll(false);
                return pickedIndices;
            }

            /*for (int i = 0; i < 8; i++) {
                if (reRollFaces.contains(player.roll[i].ordinal())) {
                    pickedIndices[i] = i;
                }
            }*/

            for (int i = 0; i < 8; i++) {
                if (reRollFaces.contains(player.getRoll()[i].ordinal())) {
                    pickedIndices[i] = i;
                }
            }



            //player.reRoll = true;
            player.setReRoll(true);
            return pickedIndices;
        }
    };

    public static BiFunction<Player, Card, Integer[]> seaBattle = (player, draw) -> {

        reRollCount = 0;

        Integer[] pickedIndices = new Integer[8];

        Arrays.fill(pickedIndices, 8);

        ArrayList<Integer> reRollFaces = new ArrayList<>(3);

        int[] faceCounts = Score.faceCounts(player);

        SeaBattle drawnCard = (SeaBattle) draw;

        if(faceCounts[4] >= drawnCard.getSwords()){
            player.setReRoll(false);
            return pickedIndices;
        }
        else{
            for(int i = 0; i < faceCounts.length; i++){
                if (i != Faces.SABER.ordinal()) {
                    reRollCount += faceCounts[i];

                    reRollFaces.add(i);
                }
            }

            if (reRollCount < 2) {
                //player.reRoll = false;
                player.setReRoll(false);
                return pickedIndices;
            }

            /*for (int i = 0; i < 8; i++) {
                if (reRollFaces.contains(player.roll[i].ordinal())) {
                    pickedIndices[i] = i;
                }
            }*/

            for (int i = 0; i < 8; i++) {
                if (reRollFaces.contains(player.getRoll()[i].ordinal())) {
                    pickedIndices[i] = i;
                }
            }


            player.setReRoll(true);
            return pickedIndices;
        }

    };

    public static BiFunction<Player, Card, Integer[]> monkeyBusiness = (player, draw) -> {

        reRollCount = 0;

        Integer[] pickedIndices = new Integer[8];

        Arrays.fill(pickedIndices, 8);

        ArrayList<Integer> reRollFaces = new ArrayList<>(3);

        int[] faceCounts = Score.faceCounts(player);

        if(faceCounts[2] == 0 && faceCounts[3] == 0 && faceCounts[4] == 0){
            player.setReRoll(false);
            return pickedIndices;
        }
        else{
            for(int i = 0; i < faceCounts.length; i++){
                if (i != Faces.MONKEY.ordinal() && i != Faces.PARROT.ordinal()) {
                    reRollCount += faceCounts[i];

                    reRollFaces.add(i);
                }
            }

            if (reRollCount < 2) {
                //player.reRoll = false;
                player.setReRoll(false);
                return pickedIndices;
            }

            /*for (int i = 0; i < 8; i++) {
                if (reRollFaces.contains(player.roll[i].ordinal())) {
                    pickedIndices[i] = i;
                }
            }*/

            for (int i = 0; i < 8; i++) {
                if (reRollFaces.contains(player.getRoll()[i].ordinal())) {
                    pickedIndices[i] = i;
                }
            }

            player.setReRoll(true);
            return pickedIndices;

        }

    };

    public static Function<Player, Integer[]> random = (player) -> {

        Random rnd = new Random();

        int diceToRoll = rnd.nextInt(2,9-player.getSkulls());

        Integer[] pickedIndices = new Integer[diceToRoll];
        Arrays.fill(pickedIndices, 9);

        /*for(int i = 0; i < diceToRoll; i++){
            int pick = rnd.nextInt(8);
            if(Arrays.stream(pickedIndices).anyMatch(n->(n==pick)) || player.roll[pick] == Faces.SKULL){
                i--;
            }
            else{
                pickedIndices[i] = pick;
            }
        }*/

        for(int i = 0; i < diceToRoll; i++){
            int pick = rnd.nextInt(8);
            if(Arrays.stream(pickedIndices).anyMatch(n->(n==pick)) || player.getRoll()[pick] == Faces.SKULL){
                i--;
            }
            else{
                pickedIndices[i] = pick;
            }
        }

        //player.reRoll = rnd.nextBoolean();
        player.setReRoll(rnd.nextBoolean());

        return pickedIndices;

    };


}
