package pk;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
//The player class
    public Player(String playerName, Function<Player, Integer[]> chosenStrategy, boolean traceOption){  //Constructing the player object with the desired name and strategy, there's a boolean for tracing
        name = playerName;
        strategy = chosenStrategy;
        trace = traceOption;
    }

    private boolean trace = false;  //Tracing is initialized to false in case of errors
    public String name;  //The player's name is declared as public since it has no real effect on the game

    private Function<Player, Integer[]> strategy;  //The player's strategy is an interface function object that is private, no getters/setters are provided since that strategy is assigned during construction and will be used throughout the rest of the simulation

    private Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};  //The set of dice for the player with private access modifier

    private Faces[] roll = new Faces[8];  //The current roll of the player, set as private as altering it could heavily alter the game
    public Faces[] getRoll() {
        return roll;  //Getter for the roll array
    }


    public int getTotalScore() {
        return totalScore;
    }  //The following are various getters and setters for the player's attributes

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

    private boolean reRoll;  //Bool to determine whether to re-roll

    static Logger logger = LogManager.getLogger("Player");  //Logger for tracing

    private void reRoll(Integer[] pickedIndices){  //Method to reroll the dice with the indices specified by the picked indices array, the strategies used ensure that there are at least 2 picked indices in the array, also all skull indices have values of 8
        if(trace){
            logger.trace("Re-rolling the dice: ");  //Tracing
        }

        for (int index: pickedIndices){  //Go through all the indices in the picked indices array
            if(index != 8){  //If an index is 8, that means it's assigned to not be re-rolled(8 is chosen since it's the first int after 7, the maximum possible index in a roll)
                roll[index] = playerDice[index].roll();  //Rerolling the dice
            }
        }
        skulls = 0;  //With each reroll, the player's skull count is reset so the skulls are counted only in the current roll and not accumulated
    }

    public void play(Card draw){  //The main play method, which takes in a drawn card which was drawn during the turn method

        roll = Dice.roll8(playerDice);  //Rolling all dice at the beginning of the turn

        if(trace){
            logger.trace(draw.getName());
            if(draw.isSea()){
                SeaBattle card = (SeaBattle) draw;  //Tracing, if the drawn card is sea battle, the number of sabers and bonus score is also logged
                logger.trace("Swords: "+card.getSwords()+", Bonus: "+card.getBonus());
            }
        }

        do{  //The loop of checking the skulls, applying the strategy, then rerolling if the value returned by the strategy makes the bool reroll true

            if(trace){
                logger.trace(name+"'s roll is:");
                logger.trace(Dice.printRoll(roll,trace));  //Tracing
            }

            //Check how many skulls
            for(int i = 0; i < 8; i++){
                if(roll[i] == Faces.SKULL){
                    skulls++;
                }
            }

            //If skulls are > 3, end the turn
            if(skulls >= 3){
                if(trace){
                    logger.trace("Rolled 3 skulls, turn over");  //Tracing
                }
                break;  //Breaking the loop immediately so strategies aren't applied
            }

            Integer[] pickedIndices;  //Creating the picked indices array to be used in the reroll method

            if(strategy == Strategies.combo){  //Checking if the player uses the combo strategy
                if(draw.isSea()){  //If the drawn card is the sea battle card
                    pickedIndices = Strategies.seaBattle.apply(this,draw);  //Apply the sea battle strategy with the player and the drawn card as parameters
                }
                else if(draw.isMonkey()){//If the drawn card is the monkey business card
                    pickedIndices = Strategies.monkeyBusiness.apply(this,draw);  //Apply the monkey business strategy with the player and the drawn card as parameters
                }
                else{
                    pickedIndices = strategy.apply(this);  //The only other option is the nop card, so the normal combo strategy is employed
                }
            }
            else{  //If the player doesn't use the combo strategy
                pickedIndices = strategy.apply(this);  //The random strategy is applied
            }

            if(reRoll){  //If the strategy used set the reroll boolean to true
                reRoll(pickedIndices);  //Reroll is called with the returned picked indices by the strategy
            }

        } while(reRoll);  //The loop will start again if the strategy wants to reroll

        totalScore += Score.calcScore(this, draw);  //After the bool reroll becomes false, whether it's by strategy or if the loop is broken because 3 skulls were found, the total score is calculated

        if(trace){
            logger.trace("Turn ended. Score: "+ totalScore +"\n");  //Tracing
        }
    }



}
