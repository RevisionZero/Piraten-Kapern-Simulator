package pk;

import java.util.*;

public class Player {

    public Player(boolean redOrNot){
        red = redOrNot;
    }

    private boolean red;

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};
    Faces[] roll = new Faces[8];

    public int totalScore = 0;

    private int skulls = 0;

    public int wins = 0;

    public Faces[] roll8(){
        Faces[] roll = new Faces[8];

        for(int i = 0; i < 8; i++){
            roll[i] = playerDice[i].roll();
        }
        return roll;
    }

    private void printRoll(Faces[] roll, boolean red){
        if(red){
            System.out.print("\u001B[31m"+"[");
        }
        else{
            System.out.print("\u001B[33m"+"[");
        }
        final String ANSI_RESET = "\u001B[0m";
        String stringToPrint = "";
        for(int i = 0; i < 8; i++){
            if(roll[i] == null){
                stringToPrint += "SKULL, ";
            }
            else{
                stringToPrint += roll[i] + ", ";
            }
        }
        stringToPrint = stringToPrint.substring(0,stringToPrint.length()-2);
        System.out.print(stringToPrint+"]\n"+ANSI_RESET);

    }

    public void play(boolean firstRoll){


        int dag = 0; //dag = diamonds and gold
        boolean reRoll;
        if(firstRoll){
            this.roll = roll8();
        }
        else{
            for(int i = 0; i < 8; i++){
                if(this.roll[i] != null){
                    this.roll[i] = playerDice[i].roll();
                }
            }
        }

        do{
            System.out.println("Your roll is: ");
            printRoll(roll,red);

            //Check how many skulls
            for(int i = 0; i < 8; i++){
                if(roll[i] == Faces.SKULL && roll[i] != null){
                    this.skulls++;
                    roll[i] = null;
                }
            }

            //If skulls are > 3, end the turn
            if(this.skulls >= 3){
                System.out.println("You rolled 3 skulls, your turn is over!");
                break;
            }

            //Keep n dice at random
            Random rnd = new Random();
            //int j = 7;
            //Faces[] rollCopy = new Faces[8];



            int diceToKeep = rnd.nextInt(6-this.skulls);

            /*if(skulls < 6){
                diceToKeep = rnd.nextInt(6-skulls);
            }*/


            int[] pickedIndices = new int[diceToKeep];
            Arrays.fill(pickedIndices, 9);

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
            }
            System.out.println();


            //Re-roll without skulls and kept dice

            reRoll = rnd.nextBoolean();

            if(reRoll){

                System.out.println("\nRe-rolling...");

                int reRollSize = rnd.nextInt(2,8-this.skulls);
                int[] reRollIndices = new int[reRollSize];
                Arrays.fill(reRollIndices,9);

                for(int i = 0; i < reRollSize; i++){
                    int pick = rnd.nextInt(8);
                    if(Arrays.stream(reRollIndices).anyMatch(n->(n==pick)) || roll[pick] == null){
                        i--;
                    }
                    else{
                        reRollIndices[i] = pick;
                        this.roll[pick] = playerDice[pick].roll();
                    }

                }



            }

        } while(reRoll);

        if(this.skulls < 3){
            totalScore += 100*dag;
        }
        else{
            this.roll = roll8();
            this.skulls = 0;
        }

        System.out.println("Score: "+ totalScore +"\n");


    }



}
