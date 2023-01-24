package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class GameSimulation {

    public GameSimulation(boolean traceOption){
        trace = traceOption;
    }

    static Logger logger = LogManager.getLogger("GameSimulation");

    private static boolean trace = false;


    private static void turn(Player player, String text){
        if(trace){
            logger.trace(player.name+text);
        }
        player.play();
    }

    private static void finalTurns(Score scorer,Player... players){
        if(trace){
            logger.trace(players[scorer.initialWinner].name+" has reached 6000 points, final turns.");
        }
        for(int i = 0; i < players.length; i++){
            if(i != scorer.initialWinner){
                turn(players[i],"'s final turn:");
                scorer.finalRoundScores.add(players[i].totalScore);
            }
        }
        scorer.getFinalRoundMax();
    }

    private static void printWinPercentages(float numOfGames,Player... players){
        for(int i = 0; i < players.length; i++){
            System.out.printf(players[i].name+"'s win percentage: %.2f", (((players[i].wins)/numOfGames) * 100));
            System.out.print("%\n");
        }
    }

    private static void win(int winner, Player... players){
        players[winner].wins++;

        if(trace){
            logger.trace(players[winner].name+" wins");
        }
        Arrays.stream(players).forEach(player -> {
            if(trace){
                logger.trace(player.name+"'s score: "+player.totalScore);
            }
        });
    }

    public void simulation(int numberOfGames,Player... players){

        for(int i = 0; i < numberOfGames; i++){

            int winner = 6;

            Score scorer = new Score(players);

            if(trace){
                logger.trace("------------------------------------Game " + (i+1) + "------------------------------------");
            }

            scorer.resetScore(players);

            Dice.resetSkulls(players);

            Arrays.stream(players).forEach(player -> {
                turn(player,"'s first turn:");
            });

            scorer.addAllScores();

            boolean keepPlaying = true;

            do{
                for(int j = 0; j < players.length; j++){

                    turn(players[j],"'s turn:");

                    if(players[j].totalScore>=6000){
                        scorer.initialWinner = j;
                        keepPlaying = false;
                        break;
                    }

                }

            }
            while (keepPlaying);

            finalTurns(scorer,players);

            if(scorer.finalRoundWinner != -1){
                if(players[scorer.finalRoundWinner].totalScore < players[scorer.initialWinner].totalScore){
                    winner = scorer.initialWinner;

                    win(winner,players);

                }
                else if(players[scorer.finalRoundWinner].totalScore == players[scorer.initialWinner].totalScore){
                    i--;

                    if(trace){
                        logger.trace("Tie, replaying");
                    }
                }
                else {
                    winner = scorer.finalRoundWinner;

                    win(winner,players);

                }
            }
            else {
                if(scorer.finalRoundMaxScore < players[scorer.initialWinner].totalScore){
                    winner = scorer.initialWinner;

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
        printWinPercentages(numberOfGames,players);
    }
}
