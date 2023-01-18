package pk;

public class GameSimulation {

    public void simulation(Player player1, Player player2, int numberOfGames){

        for(int i = 0; i < numberOfGames; i++){

            player1.totalScore = 0;
            player2.totalScore = 0;

            System.out.println("------------------------------------Game " + (i+1) + "------------------------------------");

            System.out.println("Player 1's first turn:");
            player1.play(true);

            System.out.println("Player 2's first turn:");
            player2.play(true);

            while (player1.totalScore < 6000 && player2.totalScore < 6000){
                System.out.println("Player 1's turn");
                player1.play(false);
                System.out.println("Player 2's turn");
                player2.play(false);
            }


            if(player1.totalScore>player2.totalScore){
                player1.wins++;
                System.out.println("Player 1 wins!");
                System.out.println("Player 1 wins: "+ player1.wins);
                System.out.println("Player 2 wins: "+ player2.wins);
                //scanner.nextLine();
            }
            else {
                player2.wins++;
                System.out.println("Player 2 wins!");
                System.out.println("Player 1 wins: "+ player1.wins);
                System.out.println("Player 2 wins: "+ player2.wins);
                //scanner.nextLine();
            }


        }

        float player1Percentage = ((player1.wins)/42f) * 100;
        float player2Percentage = ((player2.wins)/42f) * 100;



        System.out.println("Simulation is over. GG!");
        System.out.printf("Player 1's win percentage: %.2f", player1Percentage);
        System.out.print("%\n");
        System.out.printf("Player 2's win percentage: %.2f", player2Percentage);
        System.out.print("%\n");
    }
}
