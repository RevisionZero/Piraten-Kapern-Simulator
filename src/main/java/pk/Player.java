package pk;

public class Player {

    Dice[] playerDice = {new Dice(), new Dice(), new Dice(),new Dice(),new Dice(),new Dice(),new Dice(),new Dice()};

    public Faces[] roll8(){
        Faces[] roll = new Faces[8];

        for(int i = 0; i < 8; i++){
            roll[i] = playerDice[i].roll();
        }
        return roll;
    }

    public static void main(String[] args) {
        Player pl = new Player();
        for(int i = 0; i < 8; i++){
            System.out.println(pl.roll8()[i]);
        }
    }


}
