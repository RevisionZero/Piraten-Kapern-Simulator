package pk;

public class SeaBattle extends Card{

    public SeaBattle(int bonus, int swords){
        super("Sea Battle");

        this.bonus = bonus;
        this.swords = swords;
    }

    public int getBonus() {
        return bonus;
    }

    public int getSwords() {
        return swords;
    }

    private final int bonus;

    private final int swords;

}
