package pk;

import java.util.*;

public class Player {

    public Player(boolean redOrNot, String playerName){
        red = redOrNot;
        name = playerName;
    }

    private boolean red;

    public String name;

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};
    Faces[] roll = new Faces[8];

    public int totalScore = 0;

    public int skulls = 0;

    public int wins = 0;

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

    private void reRoll(int[] pickedIndices){
        System.out.print("\nRe-rolling the dice: ");

        for (int index: pickedIndices){
            roll[index] = playerDice[index].roll();
        }
    }

    private void turnScore(int dag){
        if(skulls < 3){
            totalScore += 100*dag;
        }
        else{
            roll = Dice.roll8(playerDice);
        }
        skulls = 0;
    }
    public void play(){

        int dag = 0; //dag = diamonds and gold

        boolean reRoll;

        roll = Dice.roll8(playerDice);

        do{
            dag = 0;

            System.out.println("Your roll is: ");
            printRoll(roll,red);

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

            int diceToRoll = rnd.nextInt(2,9-skulls);

            int[] pickedIndices = new int[diceToRoll];
            Arrays.fill(pickedIndices, 9);

            for(int i = 0; i < diceToRoll; i++){
                int pick = rnd.nextInt(8);
                if(Arrays.stream(pickedIndices).anyMatch(n->(n==pick)) || roll[pick] == null){
                    i--;
                }
                else{
                    pickedIndices[i] = pick;
                }

            }

            //Check how many of n dice are diamond/gold and add score


            for(int i  = 0; i < 8; i++){
                if(roll[i] == Faces.GOLD || roll[i] == Faces.DIAMOND){
                    dag += 1;
                }
            }
            System.out.println();

            //Re-roll without skulls and kept dice

            reRoll = rnd.nextBoolean();

            if(reRoll){
                reRoll(pickedIndices);
            }

        } while(reRoll);

        turnScore(dag);

        System.out.println("Turn ended. Score: "+ totalScore +"\n");


    }



}
