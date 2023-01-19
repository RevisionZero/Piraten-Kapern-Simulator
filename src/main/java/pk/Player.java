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
        System.out.println("\nRe-rolling...");

        for(int i = 0; i < 8; i++){
            int current = i;
            if(!Arrays.stream(pickedIndices).anyMatch(n->(n==current)) && roll[i] != null){
                roll[i] = playerDice[i].roll();
            }
        }
    }

    private void turnScore(int dag){
        if(skulls < 3){
            totalScore += 100*dag;
        }
        else{
            roll = Dice.roll8(playerDice);
            skulls = 0;
        }
    }
    public void play(boolean firstRoll){

        int dag = 0; //dag = diamonds and gold

        boolean reRoll;

        if(firstRoll){
            roll = Dice.roll8(playerDice);
        }
        else{
            for(int i = 0; i < 8; i++){
                if(roll[i] != null){
                    roll[i] = playerDice[i].roll();
                }
            }
        }

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

            int diceToKeep = rnd.nextInt(6-skulls);

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

            if(diceToKeep != 0){
                System.out.println("The kept dice are: ");
            }
            else {
                System.out.println("No kept dice");
            }

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
                reRoll(pickedIndices);
            }

        } while(reRoll);

        turnScore(dag);

        System.out.println("Score: "+ totalScore +"\n");


    }



}
