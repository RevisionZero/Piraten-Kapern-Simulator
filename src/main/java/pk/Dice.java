package pk;
import java.util.Arrays;
import java.util.Random;

public class Dice {

    public Faces roll() {
        int howManyFaces = Faces.values().length;
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    public static Faces[] roll8(Dice[] playerDice){
        Faces[] roll = new Faces[8];

        for(int i = 0; i < 8; i++){
            roll[i] = playerDice[i].roll();
        }
        return roll;
    }

    public static String printRoll(Faces[] roll, boolean trace){
        if(trace){
            String stringToPrint = "[";
            for(int i = 0; i < 8; i++){
                stringToPrint += roll[i] + ", ";
            }
            stringToPrint = stringToPrint.substring(0,stringToPrint.length()-2);
            return stringToPrint+"]\n";
        }
        return "";
    }

    public static void resetSkulls(Player... players){
        Arrays.stream(players).forEach(player -> {
            //player.skulls = 0;
            player.setSkulls(0);
        });
    }

}
