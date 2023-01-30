package pk;

public abstract class Card {
//Abstract card class which all other cards extend
    private final String cardName;  //Card name for each card

    protected Card(String name){
        this.cardName = name;
    } //Initialize a card with its name

    protected String getName(){
        return this.cardName;
    };  //Method to access the card's name

    protected boolean isSea(){ //Checking if the card in question is a sea battle card
        if(this.getName().equals("Sea Battle")){
            return true;
        }
        else{
            return false;
        }
    }

    protected boolean isMonkey(){ //Checking if the card in question is a monkey business card
        if(this.getName().equals("Monkey Business")){
            return true;
        }
        else{
            return false;
        }
    }


}
