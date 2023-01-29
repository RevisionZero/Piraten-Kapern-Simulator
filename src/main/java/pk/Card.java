package pk;

public abstract class Card {

    protected final String cardName;

    protected Card(String name){
        this.cardName = name;
    }

    protected String getName(){
        return this.cardName;
    };

    protected boolean isSea(){
        if(this.getName().equals("Sea Battle")){
            return true;
        }
        else{
            return false;
        }
    }

    protected boolean isMonkey(){
        if(this.getName().equals("Monkey Business")){
            return true;
        }
        else{
            return false;
        }
    }


}
