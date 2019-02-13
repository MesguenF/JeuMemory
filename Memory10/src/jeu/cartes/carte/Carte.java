package jeu.cartes.carte;

public class Carte /*implements ICarte*/ {
	
	private static final String FACE_CACHEE_GAUCHE	="[---";
	private static final String FACE_CACHEE_DROITE1  ="---]";
	private static final String FACE_CACHEE_DROITE2  ="--]";
	public int numCarte;		//Pour BD
	public Symbole symboleCarte;
	public boolean visibleBool = false;
	
	/*Constructeur d'une carte vide*/
	public Carte(){ }
	
	/*Constructeur d'une carte avec Symbole et visiblité*/
	public Carte(Symbole symbole,boolean visible){
		this.symboleCarte = symbole; 
		this.visibleBool = visible;
	}
	
	/*Méthodes*/
	public Symbole getSymbole() { return symboleCarte; }
	public Symbole setSymbole(Symbole symbole) { return symbole; }
	public void setVisible(boolean visible) { this.visibleBool = visible; }
	public boolean isVisible() { return visibleBool; }
	public int getNumCarte() { return numCarte; }
	public void setNumCarte(int numCarte) { this.numCarte = numCarte; }

	public String afficherCarteAvecNumero(int i) {
		String rep = null;
		if (i < 10) { rep = FACE_CACHEE_GAUCHE +i+ FACE_CACHEE_DROITE1; }
		else { rep = FACE_CACHEE_GAUCHE +i+ FACE_CACHEE_DROITE2; }
		return rep;
	}
	
	@Override		//TODO A supprimer
	public String toString() {
		return "Carte [symboleCarte=" + symboleCarte + ", visibleBool=" + visibleBool + "]" + "\n";
	}
	
}
