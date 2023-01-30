package pk;

public class SeaBattle extends Card{
//The sea battle card
    public SeaBattle(int bonus, int swords){  //Sea battle card initializer, which takes in the bonus it gives and the sabers required as parameters
        super("Sea Battle");  //Sets the name of the sea battle card using the card constructor

        this.bonus = bonus;  //Assigns the sea battle card's attributes according to the parameters of the constructor
        this.swords = swords;
    }

    public int getBonus() {  //Getter for the value of the bonus
        return bonus;
    }

    public int getSwords() {
        return swords;
    }  //Getter for the value of the required sabers

    private final int bonus;  //The value of the bonus

    private final int swords;  //The value of the required sabers

}
