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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    private int totalScore = 0;

    public int getSkulls() {
        return skulls;
    }

    public void setSkulls(int skulls) {
        this.skulls = skulls;
    }

    private int skulls = 0;

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    private int wins = 0;

    public void setReRoll(boolean reRoll) {
        this.reRoll = reRoll;
    }

    private boolean reRoll;

    static Logger logger = LogManager.getLogger("PLL");

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
                logger.trace(Dice.printRoll(roll,trace));
            }
            //logger.trace(Dice.printRoll(roll,trace));

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
