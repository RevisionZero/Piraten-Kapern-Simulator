import pk.Dice;
import pk.GameSimulation;
import pk.Player;
import pk.Strategies;

import java.util.Scanner;
import java.util.function.Function;

public class PiratenKarpen {



    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");

        boolean trace = false;

        Function<Player, Integer[]> player1Strategy = Strategies.random;
        Function<Player, Integer[]> player2Strategy = Strategies.random;

        try{
            trace = args[0].contains("T");

            if(args[1].equals("combo")){
                player1Strategy = Strategies.combo;
            }
            if(args[2].equals("combo")){
                player2Strategy = Strategies.combo;
            }

        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Please type in the strategies of each player and desired trace mode");
            return;
        }



        Player player1 = new Player("Player 1", player1Strategy,trace);
        Player player2 = new Player("Player 2",player2Strategy,trace);

        GameSimulation simulator = new GameSimulation(trace);
        simulator.simulation(42,player1,player2);

    }
    
}
