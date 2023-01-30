package pk;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Strategies {
//Strategies class containing all the strategies as functional interfaces
    private static int reRollCount;  //The number of dice to be rerolled, created as a class attribute since it can

    public static Function<Player, Integer[]> combo = (player) -> {  //The combo strategy functional interface

        reRollCount = 0;  //Initializing the reroll count to 0

        Integer[] pickedIndices = new Integer[8];  //The array which specifies which dice are to be rerolled, if any

        Arrays.fill(pickedIndices, 8);  //Filling the array with 8's since 8's won't be rerolled(see the method reRoll in the Player class)

        ArrayList<Integer> reRollFaces = new ArrayList<>(3);  //An arraylist of which faces should be rerolled

        int[] faceCounts = Score.faceCounts(player);  //An array containing the counts of each face


        if(Arrays.stream(faceCounts).noneMatch(n->(n==1)) && !Arrays.stream(faceCounts).noneMatch(n->(n==2))) {  //Checking if all the dice contribute to a combination
            player.setReRoll(false);  //If so, keep the reroll(don't reroll)
            return pickedIndices;  //Return the picked indices array(filled with 8's)
        }
        else {  //If there are dice that don't contribute to combinations
            for(int i = 0; i < faceCounts.length; i++){  //Loop through all of the indices of face counts
                if ((faceCounts[i] == 1 || faceCounts[i] == 2) && !(i == Faces.GOLD.ordinal() || i == Faces.DIAMOND.ordinal())) {  //If the face in question has a count of 1 or 2, and isn't a diamond or gold
                    reRollCount += faceCounts[i];  //Add its count to th reroll count array

                    reRollFaces.add(i);  //Add its index to the reroll faces arraylist
                }
            }

            if (reRollCount < 2) {  //If there are less than 2 dice chosen to be rerolled, the player can't reroll
                player.setReRoll(false);  //If so, keep the reroll(don't reroll)
                return pickedIndices;  //Return the picked indices array(filled with 8's)
            }

            for (int i = 0; i < 8; i++) {  //For each index in the player's roll
                if (reRollFaces.contains(player.getRoll()[i].ordinal())) {  //Check if that index corresponds to a face that needs to be rerolled
                    pickedIndices[i] = i;  //If so, add its index to picked indices
                }
            }

            player.setReRoll(true);  //Do reroll
            return pickedIndices;  //Return the picked indices array
        }
    };

    public static BiFunction<Player, Card, Integer[]> seaBattle = (player, draw) -> {  //The sea battle strategy functional interface

        reRollCount = 0;  //This section is the exact same as the combo strategy commentary

        Integer[] pickedIndices = new Integer[8];

        Arrays.fill(pickedIndices, 8);

        ArrayList<Integer> reRollFaces = new ArrayList<>(3);

        int[] faceCounts = Score.faceCounts(player);  // until here

        SeaBattle drawnCard = (SeaBattle) draw;  //Create a sea battle card by casting the player's draw

        if(faceCounts[4] >= drawnCard.getSwords()){  //If the player already rolled the required amount of sabers
            player.setReRoll(false);  //Don't reroll
            return pickedIndices;
        }
        else{  //If not
            for(int i = 0; i < faceCounts.length; i++){  //Loop through all of the indices of face counts
                if (i != Faces.SABER.ordinal()) {  //If the index doesn't correspond to a saber
                    reRollCount += faceCounts[i];  //Increase reroll count by the count of the face indicated by the index, which is stored in the face counts array

                    reRollFaces.add(i);  //Add that index to the reroll faces arraylist
                }
            }

            if (reRollCount < 2) {  //If less than 2 dices are chosen to be rerolled, the player can't reroll
                //player.reRoll = false;
                player.setReRoll(false);  //Don't reroll
                return pickedIndices;
            }

            for (int i = 0; i < 8; i++) {  //For each index in the player's roll
                if (reRollFaces.contains(player.getRoll()[i].ordinal())) {  //Check if that index corresponds to a face that needs to be rerolled
                    pickedIndices[i] = i;  //If so, add its index to picked indices
                }
            }


            player.setReRoll(true);  //Do reroll
            return pickedIndices;  //Return the picked indices array
        }

    };

    public static BiFunction<Player, Card, Integer[]> monkeyBusiness = (player, draw) -> {  //The monkey business strategy functional interface

        reRollCount = 0;  //This section is the exact same as the combo strategy commentary

        Integer[] pickedIndices = new Integer[8];

        Arrays.fill(pickedIndices, 8);

        ArrayList<Integer> reRollFaces = new ArrayList<>(3);

        int[] faceCounts = Score.faceCounts(player);  // until here

        if(faceCounts[2] == 0 && faceCounts[3] == 0 && faceCounts[4] == 0){  //If all of the dice are either monkeys or parrots(sabers, diamonds, and gold are 0, skulls are not included in face counts)
            player.setReRoll(false);  //Keep the roll
            return pickedIndices;
        }
        else{  //If not
            for(int i = 0; i < faceCounts.length; i++){  //Loop through all of the indices of face counts
                if (i != Faces.MONKEY.ordinal() && i != Faces.PARROT.ordinal()) {  //If the face is not a monkey or a parrot
                    reRollCount += faceCounts[i];  //Add its count to reroll count

                    reRollFaces.add(i);  //Add its index to the reroll faces arraylist
                }
            }

            if (reRollCount < 2) {  //If less than 2 dices are chosen to be rerolled, the player can't reroll
                //player.reRoll = false;
                player.setReRoll(false);  //Don't reroll
                return pickedIndices;
            }

            for (int i = 0; i < 8; i++) {  //For each index in the player's roll
                if (reRollFaces.contains(player.getRoll()[i].ordinal())) {  //Check if that index corresponds to a face that needs to be rerolled
                    pickedIndices[i] = i;  //If so, add its index to picked indices
                }
            }

            player.setReRoll(true);  //Do reroll
            return pickedIndices;  //Return the picked indices array

        }

    };

    public static Function<Player, Integer[]> random = (player) -> {  //The random strategy funnctional interface

        Random rnd = new Random();  //Creating a new random object

        int diceToRoll = rnd.nextInt(2,9-player.getSkulls());  //Picking a number of dice to reroll randomly, have to pick at least 2 dice, but can't pick more than the all the dice except the skulls( 8 dice - skulls, 9 is used since the bound is exclusive)

        Integer[] pickedIndices = new Integer[diceToRoll];  //Creating a new array of size equal to the number of dice randomly chosen to be rerolled
        Arrays.fill(pickedIndices, 9);  //Filling the array with inaccessible values

        for(int i = 0; i < diceToRoll; i++){  //Looping to fill the picked indices array
            int pick = rnd.nextInt(8);  //Picking a random index
            if(Arrays.stream(pickedIndices).anyMatch(n->(n==pick)) || player.getRoll()[pick] == Faces.SKULL){  //Checking if that index was already chosen or corresponds to a skull
                i--;  //If so, i is decremented to pick another index
            }
            else{  //If not,
                pickedIndices[i] = pick; //The random index is added to picked indices
            }
        }

        player.setReRoll(rnd.nextBoolean());  //Setting the reroll boolean randomly, making it so that the decision to reroll or not is random

        return pickedIndices;  //Return the picked indices array, which will be used only if the bool reroll is true

    };


}
