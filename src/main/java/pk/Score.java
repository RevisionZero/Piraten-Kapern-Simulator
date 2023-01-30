package pk;

import java.util.*;

public class Score {
//The scoring class
    private Player[] players;  //THe players to be scored array

    public int getInitialWinner() {  //Getters and setters for the class's attributes
        return initialWinner;
    }

    public void setInitialWinner(int initialWinner) {
        this.initialWinner = initialWinner;
    }

    private int initialWinner;  //The index corresponding to which player is the first to reach 6000 points, eg player 1 would have index 0

    public int getFinalRoundWinner() {
        return finalRoundWinner;
    }

    private int finalRoundWinner;  //The index corresponding to which player has the highest score in the final rounds after 1 player reaches 6000 points

    public int getFinalRoundMaxScore() {
        return finalRoundMaxScore;
    }

    private int finalRoundMaxScore;  //The highest score achieved in the final rounds 1 player reaches 6000 points

    //private float[] winPercentages;

    //ArrayList<Integer> finalRoundScores = new ArrayList<Integer>(4);


    public void setFinalRoundScores(int index, Integer value) {
        this.finalRoundScores[index] = value;
    }

    private Integer[] finalRoundScores;  //Array which stores the scores of all the players who play in the final rounds, the player who won initially has their score as 0 in this array to not conflict with other scores
    public Score(Player... inputPlayers){ //Constructor, which takes in the players playing as a parameter
        players = inputPlayers;
        //winPercentages = new float[players.length];
        finalRoundScores = new Integer[players.length];  //Initializing the final round scores array to be as long as the number of players, so each player has a spot, final round scores are implemented as an array to ensure indices always match to other arrays
    }

    public void resetScore(Player... players){  //Method to reset all the player's scores at once
        Arrays.stream(players).forEach(player -> {  //For each player in the input players array
            player.setTotalScore(0);  //Set the score to 0
        });
    }

    public void getFinalRoundMax(){  //Method that gets what is the highest score in the final round
        //Integer max = finalRoundScores.stream().max(Integer::compare).get();

        Integer max = Arrays.asList(finalRoundScores).stream().max(Integer::compare).get();  //Using a list representation of final round scores to get access to the ability to determine the maximum value, returns the highest value in the array

        //int winnerIndex = finalRoundScores.indexOf(max);
        int winnerIndex = Arrays.asList(finalRoundScores).indexOf(max);  //Using a list representation, winnerIndex is the first occurrence of the highest score in the final round scores array

        //finalRoundScores.remove(max);
        finalRoundScores[winnerIndex] = 0;  //Setting the score at the index of the first occurrence of the highest score to 0, so that other occurrence of the highest score may be found

        /*if(finalRoundScores.contains(max)){
            finalRoundWinner = -1;
        }
        else{
            finalRoundWinner = winnerIndex;
        }
        finalRoundMaxScore = max;*/

        if(Arrays.asList(finalRoundScores).contains(max)){  //Checking if there's another player with the same highest score
            finalRoundWinner = -1;  //If that's the case, the final round winner index is set to -1, see how the GameSimulation class uses this
        }
        else{ //If not
            finalRoundWinner = winnerIndex;  //The player who got the highest score is the one who has the first(and only by extension) occurrence of the highest score
        }
        finalRoundMaxScore = max;  //Either way, the highest score is the value acquired above
    }

    public static int[] faceCounts(Player player){  //Method to count how many of each face are in the player's current roll

        int[] faceCounts = new int[5];  //Initializng the array to store the counts to 5, skulls will not be counted, and each index corresponds to the ordinal of the Faces enum(so the first one, monkey, is 0)

        /*Arrays.stream(player.roll).forEach(roll->{
            switch (roll) {
                case MONKEY -> faceCounts[0]++;
                case PARROT -> faceCounts[1]++;
                case GOLD -> faceCounts[2]++;
                case DIAMOND -> faceCounts[3]++;
                case SABER -> faceCounts[4]++;
                default -> {}
            }
        });*/

        Arrays.stream(player.getRoll()).forEach(roll->{  //For each element in the roll
            switch (roll) {  //Switch case checking the value of the roll
                case MONKEY -> faceCounts[0]++;
                case PARROT -> faceCounts[1]++;
                case GOLD -> faceCounts[2]++;     //Switching accordingly
                case DIAMOND -> faceCounts[3]++;
                case SABER -> faceCounts[4]++;
                default -> {}
            }
        });

        return faceCounts;  //Returning the counts of each face
    }

    public static int calcScore(Player player, Card draw){  //Method to calculate score acquired at turn end, taking in the player in question and their drawn card as parameters
        if(player.getSkulls() < 3){  //If the player has more than 3 skulls, no score will be added
            //MONKEY, PARROT, GOLD, DIAMOND, SABER, SKULL

            int score = 0;  //Creating the score int, representing the score to be added to the player's total score

            int[] faceCounts = faceCounts(player);  //Getting the counts of each face

            if(draw.isMonkey()){  //Checking if the player has drawn the monkey business card
                int monkeyCount = faceCounts[0]; int parrotCount = faceCounts[1];  //If that's the case, store the counts of monkeys and parrots in new ints(to ensure passing by value in the next lines)
                faceCounts[0] = monkeyCount + parrotCount;  //Setting monkey counts to monkeys + parrots
                faceCounts[1] = 0;  //The counts of the parrots are set to zero, now, in the switch statement following, monkeys and parrots are viewed as the same since their counts have been added
            }

            for (int index: faceCounts){  //For each index in the face counts array
                switch (index){  //Switch accordingly
                    case 3 -> score += 100;  // 3 of a kind means adding 100 points
                    case 4 -> score += 200;  // 4 of a kind means adding 200 points
                    case 5 -> score += 500;  // etc...
                    case 6 -> score += 1000;
                    case 7 -> score += 2000;
                    case 8 -> score += 4000;
                }
            }

            score += 100*(faceCounts[2]+faceCounts[3]);  //index 2 corresponds to gold, 3 corresponds to diamond, their collective sum is multiplied by 100 to get the score of their values
            if(draw.isSea()){  //If the player has drawn a sea battle card
                SeaBattle card = (SeaBattle) draw;  //Casting the drawn card to sea battle to use its methods
                if(faceCounts[4] >= card.getSwords()){  //Checking if the player rolled the required amount of sabers
                    score += card.getBonus();  //If so, the bonus score is added
                }
                else{
                    return 0;  //If not, the turn's score is nullified and nothing is added: 0 is returned
                }
            }
            //player.skulls = 0;
            player.setSkulls(0);  //The player's skulls are set to 0 in order to ensure that skulls do not accumulate

            return score;  //The score gained is returned
        }
        //player.skulls = 0;
        player.setSkulls(0);  //The player's skulls are set to 0 in order to ensure that skulls do not accumulate
        return 0;  //If the player got 3 skulls, no score is gained: 0 is returned

    }

    public static void printWinPercentages(float numOfGames,Player... players){  //Method to print the percentage of wins of each player
        for(int i = 0; i < players.length; i++){  //Loop through all the players
            System.out.printf(players[i].name+"'s win percentage: %.2f", (((players[i].getWins())/numOfGames) * 100));  //Print the player's name and their win percentage: (num of wins/num of games) * 100
            System.out.print("%\n");
        }
    }




}
