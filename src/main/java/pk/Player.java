package pk;

import java.util.*;

public class Player {

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};

    public Faces[] roll8(){
        Faces[] roll = new Faces[8];

        for(int i = 0; i < 8; i++){
            roll[i] = playerDice[i].roll();
        }
        return roll;
    }

    private void printRoll(Faces[] roll){
        String stringToPrint = "";
        System.out.print("[");
        for(int i = 0; i < 8; i++){
            if(roll[i] == null){
                stringToPrint += "Unavailable, ";
            }
            else{
                stringToPrint += roll[i] + ", ";
            }
        }
        stringToPrint = stringToPrint.substring(0,stringToPrint.length()-2);
        System.out.print(stringToPrint+"]\n");

    }

    public void play(){

        int skulls = 0;
        int score = 0;
        int dag = 0; //dag = diamonds and gold
        Faces[] roll = roll8();

        do{
            System.out.println("Your roll is: ");
            printRoll(roll);

            //Check how many skulls
            for(int i = 0; i < 8; i++){
                if(roll[i] == Faces.SKULL && roll[i] != null){
                    skulls++;
                    roll[i] = null;
                }
            }

            //If skulls are > 3, end the turn
            if(skulls >= 3){
                System.out.println("You rolled 3 skulls, your turn is over!");
                break;
            }

            //Keep n dice at random
            Random rnd = new Random();
            int unavailableDice = 0;
            //int j = 7;
            //Faces[] rollCopy = new Faces[8];


            for(int i = 0; i < 8; i++){
                if(roll[i] == null){
                    unavailableDice++;
                    //rollCopy[i] = null;
                }
                /*else{
                    rollCopy[j] = roll[i];
                    j--;
                }*/
            }
            int diceToKeep = 0;

            if(unavailableDice < 6){
                diceToKeep = rnd.nextInt(6-unavailableDice);
            }


            int[] pickedIndices = new int[diceToKeep];

            for(int i = 0; i < diceToKeep; i++){
                int pick = rnd.nextInt(8);
                if(Arrays.stream(pickedIndices).anyMatch(n->(n==pick)) || roll[pick] == null){
                    i--;
                }
                else{
                    pickedIndices[i] = pick;
                }

            }




            //Check how many of n dice are diamond/gold and add score

            System.out.println("The kept dice are: ");
            for(int index:pickedIndices){
                System.out.print(index+1+" ");
                if(roll[index] == Faces.GOLD || roll[index] == Faces.DIAMOND){
                    dag += 1;
                }
                roll[index] = null;
            }
            System.out.println();

            int turnPoints = 100*dag;

            score += turnPoints;
            dag = 0;


            System.out.println("Points earned this roll: " +turnPoints);

            //Re-roll without skulls and kept dice

            for(int i = 0; i < 8; i++){
                if(roll[i] != null){
                    roll[i] = playerDice[i].roll();
                }
            }
        } while(true);

        System.out.println("Score: "+score);



        /*int skulls = 0;
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
                        if(j == selections.length){
                            break;
                        }
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

                score += dag*100;

            }

            for(int i = 0; i < 8; i++){
                if(playerDice[i] != null){
                    playerDice[i].roll();
                }
            }

            //dag = (roll[i] == Faces.DIAMOND | roll[i] == Faces.GOLD) ? dag+1 : dag;


        }
        while (true);*/


    }



}
