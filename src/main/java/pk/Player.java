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

    public int play(){

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
                System.out.println("You rolled 3 skulls, you're out!");
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
                }

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


            //Re-roll without skulls and kept dice

            for(int i = 0; i < 8; i++){
                if(roll[i] != null){
                    roll[i] = playerDice[i].roll();
                }
            }
        } while(true);

        score = 100*dag;

        System.out.println("Score: "+score);

        return score;


    }



}
