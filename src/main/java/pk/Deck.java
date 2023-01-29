package pk;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

public class Deck {

    private Stack<Card> cardDeck = new Stack<>();  //The actual stack of cards

    public Card draw(){ //Draw a card
        Card draw = cardDeck.pop();  //Pop the card from the top
        cardDeck.add(0,draw);  //Add it to the bottom of the card deck
        return draw; //Return the popped card
    }

    public void newDeck(){  //Creating a new card deck

        this.cardDeck.clear();  //Clearing the previous deck

        this.cardDeck.push(new SeaBattle(300,2));
        this.cardDeck.push(new SeaBattle(300,2));
        this.cardDeck.push(new SeaBattle(500,3));
        this.cardDeck.push(new SeaBattle(500,3));
        this.cardDeck.push(new SeaBattle(1000,4));
        this.cardDeck.push(new SeaBattle(1000,4));

        for(int i = 0; i < 4; i++){
            this.cardDeck.push(new MonkeyBusiness());
        }
        for(int i = 0; i < 25; i++){
            this.cardDeck.push(new NOP());
        }

        Collections.shuffle(this.cardDeck);


    }

    public static void main(String[] args) {
        Deck d = new Deck();

        d.newDeck();

        for(int i = 0; i < 1; i++){

            Card draw = d.draw();

            if(draw.isSea()){
                System.out.println(draw.getName());
            }
        }

    }

}
