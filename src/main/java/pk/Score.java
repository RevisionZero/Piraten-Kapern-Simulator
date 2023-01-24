package pk;

import java.util.*;

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

    public static int[] faceCounts(Player player){

        int[] faceCounts = new int[5];

        Arrays.stream(player.roll).forEach(roll->{
            switch (roll) {
                case MONKEY -> faceCounts[0]++;
                case PARROT -> faceCounts[1]++;
                case GOLD -> faceCounts[2]++;
                case DIAMOND -> faceCounts[3]++;
                case SABER -> faceCounts[4]++;
                default -> {}
            }
        });

        return faceCounts;
    }

    public static int calcScore(Player player){
        if(player.skulls < 3){
            //MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL

            int score = 0;

            int[] faceCounts = faceCounts(player);

            for (int index: faceCounts){
                switch (index){
                    case 3 -> score += 100;
                    case 4 -> score += 200;
                    case 5 -> score += 500;
                    case 6 -> score += 1000;
                    case 7 -> score += 2000;
                    case 8 -> score += 4000;
                }
            }

            score += 100*(faceCounts[2]+faceCounts[3]);
            player.skulls = 0;

            return score;
        }
        player.skulls = 0;
        return 0;

    }




}
