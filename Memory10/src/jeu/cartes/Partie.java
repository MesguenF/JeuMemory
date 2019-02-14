package jeu.cartes;

public class Partie {
	
	public int numPartie; //Pour BD
	public String nomPartie;
		
	/**
	 * @param numPartie
	 * @param nomPartie
	 * @param datePartie
	 */
	public Partie(String nomPartie) {
		super();
		this.nomPartie = nomPartie;
		}

	/**
	 * @return the numPartie
	 */
	public int getNumPartie() {
		return numPartie;
	}

	/**
	 * @param numPartie the numPartie to set
	 */
	public void setNumPartie(int numPartie) {
		this.numPartie = numPartie;
	}

	/**
	 * @return the nomPartie
	 */
	public String getNomPartie() {
		return nomPartie;
	}

	/**
	 * @param nomPartie the nomPartie to set
	 */
	public void setNomPartie(String nomPartie) {
		this.nomPartie = nomPartie;
	}

}
