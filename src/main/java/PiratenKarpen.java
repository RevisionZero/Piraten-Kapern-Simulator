import pk.Dice;
import pk.GameSimulation;
import pk.Player;
import pk.Strategies;

import java.util.Properties;
import java.util.Scanner;
import java.util.function.Function;

public class PiratenKarpen {



    public static void main(String[] args) {
        //Important Remark: A lot of the comments talk as if there are more than 2 players, the implementation of this simulator supports up to 5 people, so that's what's meant by other players
        System.out.println("Welcome to Piraten Karpen Simulator!");

        boolean trace = System.getProperty("TRACING_ON") != null;  //Enabling tracing or not


        Function<Player, Integer[]> player1Strategy = Strategies.combo; //Declaring player strategies, setting 1 to be combo and 2 to be random by default
        Function<Player, Integer[]> player2Strategy = Strategies.random;

        try{ //Manually assigning strategies through args

            if(args[0].equals("random")){
                player1Strategy = Strategies.random;
            }
            if(args[1].equals("combo")){
                player2Strategy = Strategies.combo;
            }

        }
        catch (IndexOutOfBoundsException e){ //If user doesn't use proper syntax, a message is displayed and default strategy assignment is used
            System.out.println("No strategy chosen for players:\nPlayer 1 will use the combo strategy and Player 2 will use the random strategy.");
        }





        Player player1 = new Player("Player 1", player1Strategy,trace);  //Creating the 2 players with their names and strategies
        Player player2 = new Player("Player 2",player2Strategy,trace);

        GameSimulation simulator = new GameSimulation(trace);  //Creating the simulation object,
        simulator.simulation(42,player1,player2);  // then running it

    }
    
}
