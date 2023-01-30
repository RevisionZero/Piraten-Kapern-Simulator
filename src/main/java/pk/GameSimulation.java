package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameSimulation {
//Implementation of the general game simulation, eg the turns and displaying the winner
    public GameSimulation(boolean traceOption){
        trace = traceOption;
    } //GameSimulation constructor

    static Logger logger = LogManager.getLogger("GameSimulation");  //Logger for tracing

    private static boolean trace = false;  //Set tracing to false by default


    private static void turn(Player player, String text, Deck playDeck){ //Turn method to let the player play, uses a card deck to draw a card
        if(trace){
            logger.trace(player.name+text);  //Tracing
        }
        player.play(playDeck.draw());  //Calling play with a card drawn from the deck passed in as a parameter
    }

    private static void finalTurns(Score scorer, Deck playDeck,Player... players){ //Special finalTurns method to allow everyone but the player who gets 6000 first to play
        if(trace){
            logger.trace(players[scorer.getInitialWinner()].name+" has reached 6000 points, final turns.");  //Tracing
        }
        for(int i = 0; i < players.length; i++){ //Iterating over all the players and checking that they aren't the initial winner
            if(i != scorer.getInitialWinner()){  //The initial winner's index is stored in the scorer object
                turn(players[i],"'s final turn:", playDeck);  //Letting said player play

                scorer.setFinalRoundScores(i,players[i].getTotalScore());  //Setting the scores obtained in the final round to the final round scores array while corresponding indices(player 1 has index 0 both in the players array and the final scores array)
            }
            else{
                scorer.setFinalRoundScores(i,0);  //If the player is the initial winner, their score in the final round scores array is set to 0 to ensure they don't get compared to the other players
            }
        }
        scorer.getFinalRoundMax();  //Calling a method to get which final round score is the highest
    }


    private static void win(int winner, Player... players){  //Function to add wins to the winner player, display the winner, and display the final scores of each player
        players[winner].setWins(players[winner].getWins()+1);  //Iterating the winner's wins by accessing their index in the players array and using the wins getter and setter

        if(trace){
            logger.trace(players[winner].name+" wins");  //Tracing
        }
        Arrays.stream(players).forEach(player -> {
            if(trace){
                logger.trace(player.name+"'s score: "+player.getTotalScore());  //Tracing
            }
        });
    }

    public void simulation(int numberOfGames,Player... players){  //The main simulation function

        for(int i = 0; i < numberOfGames; i++){  //Playing the number of games specified when calling the fucntion at the project's entry point

            Deck playDeck = new Deck();  //Creating a new deck object at the start of each game

            playDeck.newDeck();  //Creating a new deck and shuffling it at the start of each game

            int winner = 6;  //Initializing the winner's index to 6 to make sure it's inaccessible(since the max number of players is 5)

            Score scorer = new Score(players);  //Creating the scorer object for the current game

            if(trace){
                logger.trace("------------------------------------Game " + (i+1) + "------------------------------------");  //Tracing
            }

            scorer.resetScore(players);  //Resetting all the players' scores

            Dice.resetSkulls(players);  //Resetting all the players' skulls

            Arrays.stream(players).forEach(player -> {
                turn(player,"'s first turn:", playDeck);  //Letting each player play their first turn, with a special tracing text
            });

            boolean keepPlaying = true;  //boolean to keep letting players play as long as no one gets 6000 points

            do{
                for(int j = 0; j < players.length; j++){  //For loop to let each player play

                    turn(players[j],"'s turn:", playDeck);  //Player's turn

                    if(players[j].getTotalScore()>=6000){  //After each player's turn, checking if they just got 6000 points or more
                        scorer.setInitialWinner(j);  //If that's the case, this player is set as the initial winner
                        keepPlaying = false;  //Setting the keep playing boolean to false to stop the other players from playing
                        break;  //Breaking the loop to immediately stop the next player from playing
                    }

                }

            }
            while (keepPlaying);  //The turns continue while the keep playing bool is true

            finalTurns(scorer,playDeck,players);  //Calling the special method of final turns

            if(scorer.getFinalRoundWinner() != -1){  //getFinalRoundWinner returns -1 if only 1 player from those who played the final turns got the highest score
                if(players[scorer.getFinalRoundWinner()].getTotalScore() < players[scorer.getInitialWinner()].getTotalScore()){  //Checking if the initial winner still has the highest score
                    winner = scorer.getInitialWinner();  //The winner is assigned as the initial winner

                    win(winner,players);  //Calling the win function with the winner's index

                }
                else if(players[scorer.getFinalRoundWinner()].getTotalScore() == players[scorer.getInitialWinner()].getTotalScore()){  //If the initial player and the highest score final turn player have equal scores
                    i--;  //i is reduced by 1 to play the game again since it's a tie, no winner is assigned

                    if(trace){
                        logger.trace("Tie, replaying");  //Tracing
                    }
                }
                else {  //The only other possibility is that the player with the highest score in the final round got a higher score than the initial winner
                    winner = scorer.getFinalRoundWinner();  //The winner is assigned as the player who got the highest score in the final round

                    win(winner,players);  //Calling the win function with the winner's index

                }
            }
            else { //This condition is reached if 2 or more players got equal scores in the final turns
                if(scorer.getFinalRoundMaxScore() < players[scorer.getInitialWinner()].getTotalScore()){  //The highest score achieved by 2 or more players in the final round is less than the initial winner's score
                    winner = scorer.getInitialWinner();  //The winner is assigned as the initial winner

                    win(winner,players);  //Calling the win function with the winner's index

                }
                else{  //Even if the highest score achieved in the final round is higher than the initial winner's score, 2 or more players share it, so it's a tie
                    i--;  //i is reduced by 1 to play the game again since it's a tie, no winner is assigned

                    if(trace){
                        logger.trace("Tie, replaying");  //Tracing
                    }
                }
            }

        }

        System.out.println("Simulation is over. GG!");
        Score.printWinPercentages(numberOfGames,players);  //Calling a method in score to calculate and display the win percentages of each player after the simulation is over
    }
}
