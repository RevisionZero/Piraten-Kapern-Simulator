import pk.Dice;
import pk.GameSimulation;
import pk.Player;

import java.util.Scanner;

public class PiratenKarpen {



    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        Player player1 = new Player(true);
        Player player2 = new Player(false);

        GameSimulation simulator = new GameSimulation();
        simulator.simulation(player1,player2,42);

    }
    
}
