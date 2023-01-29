package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameSimulation {

    public GameSimulation(boolean traceOption){
        trace = traceOption;
    }

    static Logger logger = LogManager.getLogger("GameSimulation");

    private static boolean trace = false;


    private static void turn(Player player, String text, Deck playDeck){
        if(trace){
            logger.trace(player.name+text);
        }
        //Card draw = playDeck.draw();
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("TEST");
        //scanner.nextLine();
        player.play(playDeck.draw());
    }

    private static void finalTurns(Score scorer, Deck playDeck,Player... players){
        if(trace){
            logger.trace(players[scorer.getInitialWinner()].name+" has reached 6000 points, final turns.");
        }
        for(int i = 0; i < players.length; i++){
            if(i != scorer.getInitialWinner()){
                turn(players[i],"'s final turn:", playDeck);
                //scorer.finalRoundScores.add(players[i].totalScore);
                //scorer.finalRoundScores[i] = players[i].totalScore;
                scorer.setFinalRoundScores(i,players[i].getTotalScore());
            }
            else{
                //scorer.finalRoundScores[i] = 0;
                scorer.setFinalRoundScores(i,0);
            }
        }
        scorer.getFinalRoundMax();
    }


    private static void win(int winner, Player... players){
        //players[winner].wins++;
        players[winner].setWins(players[winner].getWins()+1);

        if(trace){
            logger.trace(players[winner].name+" wins");
        }
        Arrays.stream(players).forEach(player -> {
            if(trace){
                logger.trace(player.name+"'s score: "+player.getTotalScore());
            }
        });
    }

    public void simulation(int numberOfGames,Player... players){

        for(int i = 0; i < numberOfGames; i++){

            Deck playDeck = new Deck();

            playDeck.newDeck();

            int winner = 6;

            Score scorer = new Score(players);

            if(trace){
                logger.trace("------------------------------------Game " + (i+1) + "------------------------------------");
            }

            scorer.resetScore(players);

            Dice.resetSkulls(players);

            Arrays.stream(players).forEach(player -> {
                turn(player,"'s first turn:", playDeck);
            });

            boolean keepPlaying = true;

            do{
                for(int j = 0; j < players.length; j++){

                    turn(players[j],"'s turn:", playDeck);

                    if(players[j].getTotalScore()>=6000){
                        scorer.setInitialWinner(j);
                        keepPlaying = false;
                        break;
                    }

                }

            }
            while (keepPlaying);

            finalTurns(scorer,playDeck,players);

            if(scorer.getFinalRoundWinner() != -1){
                if(players[scorer.getFinalRoundWinner()].getTotalScore() < players[scorer.getInitialWinner()].getTotalScore()){
                    winner = scorer.getInitialWinner();

                    win(winner,players);

                }
                else if(players[scorer.getFinalRoundWinner()].getTotalScore() == players[scorer.getInitialWinner()].getTotalScore()){
                    i--;

                    if(trace){
                        logger.trace("Tie, replaying");
                    }
                }
                else {
                    winner = scorer.getFinalRoundWinner();

                    win(winner,players);

                }
            }
            else {
                if(scorer.getFinalRoundMaxScore() < players[scorer.getInitialWinner()].getTotalScore()){
                    winner = scorer.getInitialWinner();

                    win(winner,players);

                }
                else{
                    i--;

                    if(trace){
                        logger.trace("Tie, replaying");
                    }
                }
            }

        }

        System.out.println("Simulation is over. GG!");
        //printWinPercentages(numberOfGames,players);
        Score.printWinPercentages(numberOfGames,players);
    }
}
