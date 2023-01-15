import pk.Dice;
import pk.Player;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        Player player1 = new Player();
        Player player2 = new Player();

        int player1Wins = 0;
        int player2Wins = 0;

        for(int i = 0; i < 42; i++){
            System.out.println("Player 1's turn:");
            int player1Score = player1.play();
            System.out.println("Player 2's turn:");
            int player2Score = player2.play();

            if(player1Score > player2Score){
                player1Wins++;
                System.out.println("Player 1 wins!");
            }
            else if (player2Score > player1Score){
                player2Wins++;
                System.out.println("Player 2 wins!");
            }
            else{
                System.out.println("Tie!!");
            }

        }

        float player1Percentage = ((player1Wins)/42f) * 100;
        float player2Percentage = ((player2Wins)/42f) * 100;
        float tiePercentage = ((42-player2Wins-player1Wins)/42f) * 100;;



        System.out.println("Simulation is over. GG!");
        System.out.printf("Player 1's win percentage: %.2f", player1Percentage);
        System.out.print("%\n");
        System.out.printf("Player 2's win percentage: %.2f", player2Percentage);
        System.out.print("%\n");
        System.out.printf("Percentage of ties: %.2f", tiePercentage);
        System.out.print("%\n");

    }
    
}
