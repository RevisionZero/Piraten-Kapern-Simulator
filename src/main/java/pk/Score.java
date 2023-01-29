package pk;

import java.util.*;

public class Score {

    private Player[] players;

    public int getInitialWinner() {
        return initialWinner;
    }

    public void setInitialWinner(int initialWinner) {
        this.initialWinner = initialWinner;
    }

    private int initialWinner;

    public int getFinalRoundWinner() {
        return finalRoundWinner;
    }

    private int finalRoundWinner;

    public int getFinalRoundMaxScore() {
        return finalRoundMaxScore;
    }

    private int finalRoundMaxScore;

    //private float[] winPercentages;

    //ArrayList<Integer> finalRoundScores = new ArrayList<Integer>(4);


    public void setFinalRoundScores(int index, Integer value) {
        this.finalRoundScores[index] = value;
    }

    private Integer[] finalRoundScores;
    public Score(Player... inputPlayers){
        players = inputPlayers;
        //winPercentages = new float[players.length];
        finalRoundScores = new Integer[players.length];
    }

    public void resetScore(Player... players){
        Arrays.stream(players).forEach(player -> {
            player.setTotalScore(0);
        });
    }

    public void getFinalRoundMax(){
        //Integer max = finalRoundScores.stream().max(Integer::compare).get();

        Integer max = Arrays.asList(finalRoundScores).stream().max(Integer::compare).get();

        //int winnerIndex = finalRoundScores.indexOf(max);
        int winnerIndex = Arrays.asList(finalRoundScores).indexOf(max);

        //finalRoundScores.remove(max);
        finalRoundScores[winnerIndex] = 0;

        /*if(finalRoundScores.contains(max)){
            finalRoundWinner = -1;
        }
        else{
            finalRoundWinner = winnerIndex;
        }
        finalRoundMaxScore = max;*/

        if(Arrays.asList(finalRoundScores).contains(max)){
            finalRoundWinner = -1;
        }
        else{
            finalRoundWinner = winnerIndex;
        }
        finalRoundMaxScore = max;
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

    public static int calcScore(Player player, Card draw){
        if(player.getSkulls() < 3){
            //MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL

            int score = 0;

            int[] faceCounts = faceCounts(player);

            if(draw.isMonkey()){
                int monkeyCount = faceCounts[0]; int parrotCount = faceCounts[1];
                faceCounts[0] = monkeyCount + parrotCount;
                faceCounts[1] = 0;
            }

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
            if(draw.isSea()){
                SeaBattle card = (SeaBattle) draw;
                if(faceCounts[4] >= card.getSwords()){
                    score += card.getBonus();
                }
                else{
                    return 0;
                }
            }
            //player.skulls = 0;
            player.setSkulls(0);

            return score;
        }
        //player.skulls = 0;
        player.setSkulls(0);
        return 0;

    }

    public static void printWinPercentages(float numOfGames,Player... players){
        for(int i = 0; i < players.length; i++){
            System.out.printf(players[i].name+"'s win percentage: %.2f", (((players[i].getWins())/numOfGames) * 100));
            System.out.print("%\n");
        }
    }




}
