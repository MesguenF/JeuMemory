package jeu.cartes;

public class Partie {
	
	public int numPartie; //Pour BD
	public String nomPartie;
		
	/**
	 * @param nomPartie
	 */
	public Partie(String nomPartie) {
		super();
		this.nomPartie = nomPartie;
	}

	/**
	 * @param numPartie
	 * @param nomPartie
	 */
	public Partie(int numPartie,String nomPartie) {
		super();
		this.numPartie = numPartie;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Partie [numPartie=" + numPartie + ", nomPartie=" + nomPartie + "]";
	}
	
	

}
