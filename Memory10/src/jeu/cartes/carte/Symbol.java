package jeu.cartes.carte;

public enum Symbol {
	ours, lion, girafe, zebre, cheval, gnou, singe, chevre, chien, chat;		
	
	private static final Symbol[] board = Symbol.values();
	
	/**
	 * @param i
	 * @return
	 */
	public static Symbol getSymbol(int i) {	return board[i]; }
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		String rep="";
		switch (this) {
		case ours:
			rep="[  ours ]";
			break;
		case lion:
			rep="[  lion ]";
			break;
		case chevre:
			rep="[ chevre]";
			break;
		case gnou:
			rep="[ lapin ]";
			break;
		case girafe:
			rep="[ girafe]";
			break;
		case cheval:
			rep="[ cheval]";
			break;
		case zebre:
			rep="[  zebre]";
			break;
		case singe:
			rep="[  singe]";
			break;
		case chien:
			rep="[  chien]";
			break;
		case chat:
			rep="[  chat ]";
			break;		
		default:
			rep="[-------]";
			break;
		}
		return rep;
	}
}
