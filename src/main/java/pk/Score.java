package pk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Score {

    Player[] players;

    public int initialWinner;

    public int finalRoundWinner;

    public int finalRoundMaxScore;

    int[] scores;

    float[] winPercentages;
    public Score(Player... inputPlayers){
        players = inputPlayers;
        scores = new int[players.length];
        winPercentages = new float[players.length];
    }

    public void resetScore(Player... players){
        Arrays.stream(players).forEach(player -> {
            player.totalScore = 0;
        });
    }

    public void addAllScores(){
        for(int i = 0; i < players.length; i++){
            scores[i] = players[i].totalScore;
        }
    }

    public void addScore(int index, Player player){
        scores[index] = player.totalScore;
    }

    ArrayList<Integer> finalRoundScores = new ArrayList<Integer>(4);

    public void getFinalRoundMax(){
        Integer max = finalRoundScores.stream().max(Integer::compare).get();

        int winnerIndex = finalRoundScores.indexOf(max);

        finalRoundScores.remove(max);

        if(!finalRoundScores.contains(max)){
            finalRoundWinner = -1;
            finalRoundMaxScore = max;
        }
        else{
            finalRoundWinner = winnerIndex;
        }
    }




}
