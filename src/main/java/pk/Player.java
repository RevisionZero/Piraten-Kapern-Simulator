package pk;

import java.util.*;
//import java.util.logging.LogManager;
//import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

    public Player(String playerName){
        name = playerName;
    }

    public String name;

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};
    Faces[] roll = new Faces[8];

    public int totalScore = 0;

    public int skulls = 0;

    public int wins = 0;

    private void reRoll(int[] pickedIndices){
        System.out.print("\nRe-rolling the dice: ");

        for (int index: pickedIndices){
            roll[index] = playerDice[index].roll();
        }
    }

    public void play(){

        Logger logger = LogManager.getLogger(this.getClass());

        boolean reRoll;

        roll = Dice.roll8(playerDice);

        do{

            //System.out.println("Your roll is: ");
            logger.debug(name+"'s roll is:");
            Dice.printRoll(roll);

            //Check how many skulls
            for(int i = 0; i < 8; i++){
                if(roll[i] == Faces.SKULL){
                    skulls++;
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
                if(Arrays.stream(pickedIndices).anyMatch(n->(n==pick)) || roll[pick] == Faces.SKULL){
                    i--;
                }
                else{
                    pickedIndices[i] = pick;
                }

            }

            //Re-roll without skulls and kept dice

            reRoll = rnd.nextBoolean();

            if(reRoll){
                reRoll(pickedIndices);
            }

        } while(reRoll);

        Score.calcScore(this);

        System.out.println("Turn ended. Score: "+ totalScore +"\n");
    }



}
