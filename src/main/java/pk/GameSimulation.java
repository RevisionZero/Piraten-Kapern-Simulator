package pk;

import java.util.Arrays;
import java.util.List;

public class GameSimulation {



    private static void turn(Player player, String text){
        System.out.println(player.name+text);
        player.play();
    }

    private static void finalTurns(Score scorer,Player... players){
        System.out.println(players[scorer.initialWinner].name+" has reached 6000 points, the final turns begin!");
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

    private static void resetSkulls(Player... players){
        Arrays.stream(players).forEach(player -> {
            player.skulls = 0;
        });
    }

    private static void win(int winner, Player... players){
        players[winner].wins++;

        System.out.println(players[winner].name+" wins!");
        Arrays.stream(players).forEach(player -> {
            System.out.println(player.name+"'s score: "+player.totalScore);
        });
    }

    public void simulation(int numberOfGames,Player... players){

        for(int i = 0; i < numberOfGames; i++){

            int winner = 6;

            Score scorer = new Score(players);

            System.out.println("------------------------------------Game " + (i+1) + "------------------------------------");

            scorer.resetScore(players);

            resetSkulls(players);

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

                    System.out.println("TIE! Replaying the game...");
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

                    System.out.println("TIE! Replaying the game...");
                }
            }

        }

        System.out.println("Simulation is over. GG!");
        printWinPercentages(numberOfGames,players);
    }
}
