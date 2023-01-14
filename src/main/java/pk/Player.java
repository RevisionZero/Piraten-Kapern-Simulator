package pk;

import java.util.Arrays;
import java.util.Random;

public class Player {

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};

    public Faces[] roll8(){
        Faces[] roll = new Faces[8];

        for(int i = 0; i < 8; i++){
            roll[i] = playerDice[i].roll();
        }
        return roll;
    }

    public void play(){
        int skulls = 0;
        int score = 0;
        int dag = 0; //dag = diamonds and gold
        Faces[] roll = roll8();
        boolean[] available = new boolean[8];
        int unavailabledice = 0;

        do{
            for(int i = 0; i < 8; i++){
                if(playerDice[i] != null){
                    if(roll[i] == Faces.SKULL){
                        skulls += 1;
                        playerDice[i] = null;
                        available[i] = false;
                    }
                    else{
                        available[i] = true;
                    }
                }
            }
            if(skulls > 3){
                break;
            }

            for(boolean bool: available){
                if(!bool){
                    unavailabledice += 1;
                }
            }

            Random rnd = new Random();

            if(8-unavailabledice-2>2){
                Integer[] selections = new Integer[8-unavailabledice-2];

                int numKeptDice = rnd.nextInt(1,8-unavailabledice-2);

                int j = 0;
                for(int i = 0; i < 8; i++){
                    if(available[i]){
                        selections[j] = i;
                        j++;
                    }
                }

                int[] keptDice = new int[numKeptDice];
                for(int i = 0; i < numKeptDice; i++){
                    int rndIndex = rnd.nextInt(selections.length);
                    if(selections[rndIndex] != null){
                        keptDice[i] = selections[rndIndex];
                        selections[rndIndex] = null;
                    }
                    else{
                        i--;
                    }
                }

                for(int i = 0; i < keptDice.length; i++){
                    if( (roll[keptDice[i]] == Faces.GOLD) || (roll[keptDice[i]] == Faces.DIAMOND) ){
                        dag++;
                    }
                }

                score = dag*100;

            }

            //dag = (roll[i] == Faces.DIAMOND | roll[i] == Faces.GOLD) ? dag+1 : dag;


        }
        while (true);

    }

    public static void main(String[] args) {
        Player pl = new Player();
        for(int i = 0; i < 8; i++){
            System.out.println(pl.roll8()[i]);
        }
        pl.play();
    }


}
