import pk.Dice;
import pk.GameSimulation;
import pk.Player;
import pk.Strategies;

import java.util.Properties;
import java.util.Scanner;
import java.util.function.Function;

public class PiratenKarpen {



    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");

        boolean trace = System.getProperty("TRACING_ON") != null;


        Function<Player, Integer[]> player1Strategy = Strategies.combo;
        Function<Player, Integer[]> player2Strategy = Strategies.random;

        try{
            if(args[0].equals("random")){
                player1Strategy = Strategies.random;
            }
            if(args[1].equals("combo")){
                player2Strategy = Strategies.combo;
            }

        }
        catch (IndexOutOfBoundsException e){
            System.out.println("No strategy chosen for players:\nPlayer 1 will use the combo strategy and Player 2 will use the random strategy.");
        }





        Player player1 = new Player("Player 1", player1Strategy,trace);
        Player player2 = new Player("Player 2",player2Strategy,trace);

        GameSimulation simulator = new GameSimulation(trace);
        simulator.simulation(42,player1,player2);

    }
    
}
