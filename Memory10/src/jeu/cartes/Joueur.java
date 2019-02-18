package jeu.cartes;

public class Joueur {
	public int numJoueur; //Pour BD
	public String nomJoueur;
	public String prenomJoueur;
	public String pseudoJoueur;
	public int indiceJoueur;
	public int nbPointsJoueur; 
	
	/*Constructeurs*/
	public Joueur(String nomJoueur) {
		super();
		this.nomJoueur = nomJoueur;
	}
	
	public Joueur(String nomJoueur, String prenomJoueur, String pseudoJoueur) {
		super();
		this.nomJoueur = nomJoueur;
		this.prenomJoueur = prenomJoueur;
		this.pseudoJoueur = pseudoJoueur;
	}

	public Joueur(String nomJoueur, String prenomJoueur, String pseudoJoueur, int indiceJoueur, int nbPointsJoueur) {
		super();
		this.nomJoueur = nomJoueur;
		this.prenomJoueur = prenomJoueur;
		this.pseudoJoueur = pseudoJoueur;
		this.indiceJoueur = indiceJoueur;
		this.nbPointsJoueur = nbPointsJoueur;
	}

	/*M�thodes*/
	public int getNumJoueur() { return numJoueur; }
	public void setNumJoueur(int numJoueur) { this.numJoueur = numJoueur; }
	public String getNomJoueur() { return nomJoueur; }
	public void setNomJoueur(String nom) { this.nomJoueur = nom; }
	public String getPrenomJoueur() { return prenomJoueur; }
	public void setPrenomJoueur(String prenomJoueur) { this.prenomJoueur = prenomJoueur; }
	public String getPseudoJoueur() { return pseudoJoueur; }
	public void setPseudoJoueur(String pseudoJoueur) { this.pseudoJoueur = pseudoJoueur; }
	public int getIndiceJoueur() { return indiceJoueur; }
	public void setIndiceJoueur(int indiceJoueur) { this.indiceJoueur = indiceJoueur; }
	public int getNbPointsJoueur() { return nbPointsJoueur; }
	public void setNbPointsJoueur(int nbPointsJoueur) { this.nbPointsJoueur = nbPointsJoueur; }

	@Override
	public String toString() {
		return "[nomJoueur=" + nomJoueur + ", prenomJoueur=" + prenomJoueur + ", pseudoJoueur=" + pseudoJoueur
				+ ", indiceJoueur=" + indiceJoueur  + ", nbPointsJoueur=" + nbPointsJoueur + "]" + "\n";
	}
	
	
}
