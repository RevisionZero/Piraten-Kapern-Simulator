import pk.Dice;
import pk.GameSimulation;
import pk.Player;

import java.util.Scanner;

public class PiratenKarpen {



    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");
        Player player1 = new Player(true,"Player 1");
        Player player2 = new Player(false, "Player 2");

        GameSimulation simulator = new GameSimulation();
        simulator.simulation(42,player1,player2);

    }
    
}
