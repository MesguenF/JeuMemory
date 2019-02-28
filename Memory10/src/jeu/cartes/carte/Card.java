package jeu.cartes.carte;

public class Card {
	
	private static final String LEFT_FACE	="[---";
	private static final String RIGHT_FACE1  ="---]";
	private static final String RIGHT_FACE2  ="--]";
	public int cardNumber;		//For Data Base
	public Symbol cardSymbol;
	public boolean visibleBool = false;
	
	/**
	 * 
	 */
	public Card(){ super(); }
	
	/**
	 * @param symb
	 * @param visible
	 */
	public Card(Symbol symb,boolean visible){
		super();
		this.cardSymbol = symb; 
		this.visibleBool = visible;
	}
	
	/*Méthodes*/
	public Symbol getSymbol() { return cardSymbol; }
	public Symbol setSymbol(Symbol symbole) { return symbole; }
	public void setVisible(boolean visible) { this.visibleBool = visible; }
	public boolean isVisible() { return visibleBool; }
	public int getCardNumber() { return cardNumber; }
	public void setCardNumber(int cn) { this.cardNumber = cn; }
	public int getOrdinal(Symbol symb) { 	return symb.ordinal(); }

	/**
	 * @param i
	 * @return
	 */
	public String cardWithNumber(int i) {
		String rep = null;
		if (i < 10) { rep = LEFT_FACE +i+ RIGHT_FACE1;
		}else {
			rep = LEFT_FACE +i+ RIGHT_FACE2;
		}
		return rep;
	}
	
	@Override		//TODO A supprimer
	public String toString() {
		return "Carte [symboleCarte=" + cardSymbol + ", visibleBool=" + visibleBool + "]" + "\n";
	}
}
