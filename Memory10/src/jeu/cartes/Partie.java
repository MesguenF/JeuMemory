package jeu.cartes;

import sun.util.calendar.BaseCalendar.Date;

public class Partie {
	
	public int numPartie; //Pour BD
	public String nomPartie;
	public Date datePartie;
	
	/**
	 * @param numPartie
	 * @param nomPartie
	 * @param datePartie
	 */
	public Partie(String nomPartie) {
		super();
		this.nomPartie = nomPartie;
		/*this.datePartie = datePartie;*/
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

	/**
	 * @return the datePartie
	 */
	public Date getDatePartie() {
		return datePartie;
	}

	/**
	 * @param datePartie the datePartie to set
	 */
	public void setDatePartie(Date datePartie) {
		this.datePartie = datePartie;
	}

}
