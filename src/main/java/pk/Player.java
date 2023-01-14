package pk;

import java.util.Arrays;
import java.util.Random;

public class Player {

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};

    public Faces[] roll8(){
        Faces[] roll = new Faces[8];

        for(int i = 0; i < 8; i++){
            roll[i] = playerDice[i].roll();
        }
        return roll;
    }

    public void play(){
        int skulls = 0;
        int score = 0;
        int dag = 0; //dag = diamonds and gold
        Faces[] roll = roll8();

        do{
            for(int i = 0; i < 8; i++){
                skulls = roll[i] == Faces.SKULL ? skulls+1 : skulls;
            }
            if(skulls > 3){
                break;
            }


            Random rnd = new Random();

            //dag = (roll[i] == Faces.DIAMOND | roll[i] == Faces.GOLD) ? dag+1 : dag;


        }
        while (true);

    }

    public static void main(String[] args) {
        Player pl = new Player();
        for(int i = 0; i < 8; i++){
            System.out.println(pl.roll8()[i]);
        }
        pl.play();
    }


}
