package model.cartes;

public class Game {
	
	public int gameNumber; //Pour BD
	public String gameName;
		
	/**
	 * @param gn
	 */
	public Game(String gn) {
		super();
		this.gameName = gn;
	}

	/**
	 * @param gnum
	 * @param gnam
	 */
	public Game(int gnum,String gnam) {
		super();
		this.gameNumber = gnum;
		this.gameName = gnam;
		}

	/**
	 * @return the gameNumber
	 */
	public int getGameNumber() {
		return gameNumber;
	}

	/**
	 * @param gnum the gameNumber to set
	 */
	public void setGameNumber(int gnum) {
		this.gameNumber = gnum;
	}

	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param gnam the gameName to set
	 */
	public void setGameName(String gnam) {
		this.gameName = gnam;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Partie n°: " + gameNumber +" - "+"Nom de la partie: " + gameName + "\n";
	}
}
