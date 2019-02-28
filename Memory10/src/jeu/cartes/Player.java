package jeu.cartes;

public class Player {
	public int playerNumber; //For Data Base
	public String playerLastName, playerFirstName, playerHandle;
	public int playerPosition,playerScore; 
	
	/*Constructeurs*/
	public Player(String pln, String pfn, String ph) {
		super();
		this.playerLastName = pln;
		this.playerFirstName = pfn;
		this.playerHandle = ph;
	}

	public Player(String pln, String pfn, String ph, int pp, int ps) {
		super();
		this.playerLastName = pln;
		this.playerFirstName = pfn;
		this.playerHandle = ph;
		this.playerPosition = pp;
		this.playerScore = ps;
	}

	public int getPlayerNumber() { return playerNumber; }
	public void setPlayerNumber(int pn) { this.playerNumber = pn; }
	public String getPlayerLastName() { return playerLastName; }
	public void setPlayerLastName(String pln) { this.playerLastName = pln; }
	public String getPlayerFirstName() { return playerFirstName; }
	public void setPlayerFirstName(String pfn) { this.playerFirstName = pfn; }
	public String getPlayerHandle() { return playerHandle; }
	public void setPlayerHandle(String ph) { this.playerHandle = ph; }
	public int getPlayerPosition() { return playerPosition; }
	public void setPlayerPosition(int pp) { this.playerPosition = pp; }
	public int getPlayerScore() { return playerScore; }
	public void setPlayerScore(int ps) { this.playerScore = ps; }

	@Override
	public String toString() {
		return "[Last Name=" + playerLastName + ", First Name=" + playerFirstName + ", Player Handle=" + playerHandle
				+ ", Player Position=" + playerPosition  + ", Player Score=" + playerScore + "]" + "\n";
	}
}
