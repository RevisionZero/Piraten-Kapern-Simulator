package pk;

import java.util.*;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

    public Player(String playerName, Function<Player, Integer[]> chosenStrategy, boolean traceOption){
        name = playerName;
        strategy = chosenStrategy;
        trace = traceOption;
    }

    private boolean trace = false;
    public String name;

    private Function<Player, Integer[]> strategy;

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};
    Faces[] roll = new Faces[8];

    public int totalScore = 0;

    public int skulls = 0;

    public int wins = 0;

    boolean reRoll;

    Logger logger = LogManager.getLogger(this.getClass());

    private void reRoll(Integer[] pickedIndices){
        if(trace){
            logger.trace("Re-rolling the dice: ");
        }

        for (int index: pickedIndices){
            if(index != 8){
                roll[index] = playerDice[index].roll();
            }
        }
    }

    public void play(){

        roll = Dice.roll8(playerDice);

        do{

            if(trace){
                logger.trace(name+"'s roll is:");
            }
            logger.trace(Dice.printRoll(roll,trace));

            //Check how many skulls
            for(int i = 0; i < 8; i++){
                if(roll[i] == Faces.SKULL){
                    skulls++;
                }
            }

            //If skulls are > 3, end the turn
            if(skulls >= 3){
                if(trace){
                    logger.trace("Rolled 3 skulls, turn over");
                }
                break;
            }

            //Keep n dice at random

            Integer[] pickedIndices = strategy.apply(this);

            if(reRoll){
                reRoll(pickedIndices);
            }

        } while(reRoll);

        totalScore += Score.calcScore(this);

        if(trace){
            logger.trace("Turn ended. Score: "+ totalScore +"\n");
        }
    }



}
