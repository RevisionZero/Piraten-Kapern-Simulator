package pk;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Strategies {

    private static int reRollCount;

    public static int[] combo(Player player){

        reRollCount = 0;

        int[] pickedIndices = new int[8];

        Arrays.fill(pickedIndices,8);

        ArrayList<Faces> reRollFaces = new ArrayList<>(3);

        Map<Faces, Integer> faceCounts = Score.faceCounts(player);


        if(!faceCounts.containsValue(1) && !faceCounts.containsValue(2)){
            player.reRoll = false;
            return pickedIndices;
        }
        else{
            faceCounts.forEach((face, count) -> {
                if( (count == 1 || count == 2) && !( face == Faces.GOLD || face == Faces.DIAMOND) ){
                    reRollCount += count;
                    reRollFaces.add(face);
                }
            });

            if(reRollCount < 2){
                player.reRoll = false;
                return pickedIndices;
            }

            for(int i = 0; i < 8; i++){
                if(reRollFaces.contains(player.roll[i])){
                    pickedIndices[i] = i;
                }
            }
            player.reRoll = true;
            return pickedIndices;
        }
    }


}
