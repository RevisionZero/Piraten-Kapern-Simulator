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

    public static void calcScore(Player player){
        if(player.skulls < 3){
            //MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL

            int[] faceCounts = new int[5];

            Arrays.stream(player.roll).forEach(roll->{
                switch (roll) {
                    case MONKEY -> faceCounts[0]++;
                    case PARROT -> faceCounts[1]++;
                    case GOLD -> {
                        player.totalScore += 100;
                        faceCounts[2]++;
                    }
                    case DIAMOND -> {
                        player.totalScore += 100;
                        faceCounts[3]++;
                    }
                    case SABER -> faceCounts[4]++;
                    default -> {}
                }
            });

            for (int index: faceCounts){
                switch (index){
                    case 3 -> player.totalScore += 100;
                    case 4 -> player.totalScore += 200;
                    case 5 -> player.totalScore += 500;
                    case 6 -> player.totalScore += 1000;
                    case 7 -> player.totalScore += 2000;
                    case 8 -> player.totalScore += 4000;
                }
            }
        }

        player.skulls = 0;
    }




}
