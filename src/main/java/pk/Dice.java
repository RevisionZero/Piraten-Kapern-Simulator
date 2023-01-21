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

    public static void printRoll(Faces[] roll){
        System.out.print("[");
        String stringToPrint = "";
        for(int i = 0; i < 8; i++){
            if(roll[i] == null){
                stringToPrint += "SKULL, ";
            }
            else{
                stringToPrint += roll[i] + ", ";
            }
        }
        stringToPrint = stringToPrint.substring(0,stringToPrint.length()-2);
        System.out.print(stringToPrint+"]\n");

    }

}
