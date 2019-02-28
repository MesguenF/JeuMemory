package jeu.cartes.carte;

public enum Symbole {
	ours, lion, girafe, zebre, cheval, gnou, singe, chevre, chien, chat;		/*10 Motifs pour les cartes*/
	
	private static final Symbole[] TABLEAU = Symbole.values(); 					/*Les motifs sont mis dans un tableau*/

	/*Retourne le symbole de la carte à l'indice i du tableau*/
	public static Symbole getSymbole(int indice) { 	return TABLEAU[indice]; }
	/*public static int getOrdinal(Symbole symb) { 	return symb.ordinal(); }*/
	
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
