package jeu.cartes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jeu.cartes.carte.Card;
import jeu.cartes.carte.Symbol;

public class CardPack {
	
	public final static int NBR_CARDS = 40;							
	public static List<Card> theCards = new ArrayList <Card>();			
	
	public CardPack() {
		super();
		for(int i = 0; i < NBR_CARDS/4; i++) { 
			Card c;
			for(int j = 0 ; j<4 ; j++) {
				c = new Card(Symbol.getSymbol(i),false);
				CardPack.theCards.add((Card) c);
			}
		}
		Collections.shuffle(theCards);		
	}
			
	public Card getCard(int i) { return CardPack.theCards.get(i); }
	
	public Card setCard(int i, Card c) { return CardPack.theCards.set(i,c); }
	
	public void modifyCardVisibility(int carteJouee, boolean boolVisible){ CardPack.theCards.get(carteJouee).setVisible(boolVisible); }
		
	public int size() { return CardPack.theCards.size(); }

}

