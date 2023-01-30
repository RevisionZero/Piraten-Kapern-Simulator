package pk;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Dice {
//Implementation of the dice
    public Faces roll() {
        int howManyFaces = Faces.values().length;
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    public static Faces[] roll8(Dice[] playerDice){ //Rolling all 8 dice
        Faces[] roll = new Faces[8];

        for(int i = 0; i < 8; i++){
            roll[i] = playerDice[i].roll();
        }
        return roll;
    }

    public static String printRoll(Faces[] roll, boolean trace){ //Printing the roll in question
        if(trace){  //If tracing is on, the roll is printed
            String stringToPrint = "["; //Adding [ for formatting
            for(int i = 0; i < 8; i++){
                stringToPrint += roll[i] + ", ";
            }
            stringToPrint = stringToPrint.substring(0,stringToPrint.length()-2); //Removing the final space and comma for formatting
            return stringToPrint+"]\n";  //Returning the formatted string
        }
        return ""; //If tracing isn't on, an empty string is returned
    }

    public static void resetSkulls(Player... players){ //Resetting the skulls for all players
        Arrays.stream(players).forEach(player -> { //For each player
            //player.skulls = 0;
            player.setSkulls(0); //Set the skulls to 0
        });
    }

}
