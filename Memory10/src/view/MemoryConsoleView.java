package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.cartes.Player;

public class MemoryConsoleView {
	private Scanner sc = new Scanner(System.in);
		
	public MemoryConsoleView() { 
		super();
	}

	public void packDisplay(String[] board) {
		for (String string : board) { System.out.println(string); }
	}

	public void memoryTitle() {
		System.out.println("*********************************************");
		System.out.println("*                 JEU MEMORY                *");
		System.out.println("*********************************************");
	}
	public void backupTitle() {
		System.out.println("---------------------------------------------");
		System.out.println("-            SAUVEGARDE REUSSIE             -");
		System.out.println("---------------------------------------------");
	}
	public void endOfGameTitle() {
		System.out.println("---------------------------------------------");
		System.out.println("-                FIN DE PARTIE              -");
		System.out.println("---------------------------------------------");
	}
	public void newGameTitle() {
		System.out.println("---------------------------------------------");
		System.out.println("-              NOUVELLE PARTIE              -");
		System.out.println("---------------------------------------------");
	}
	public void loadedGameTitle() {
		System.out.println("---------------------------------------------");
		System.out.println("-                CHARGER PARTIE             -");
		System.out.println("---------------------------------------------");
	}
	public void choiceTitle() {
		line();
		System.out.println("");
		System.out.println(" 1 : NOUVELLE PARTIE ");
		System.out.println("");
		System.out.println(" 2 : CHARGER PARTIE  ");
		System.out.println("");
		System.out.println(" 3 : QUITTER LE JEU  ");
		System.out.println("");
		line();
		System.out.println(" Votre choix : ");
	}
	
	public void nextOrSaveTitle() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("       TOUCHE 1 : CONTINUER / TOUCHE 2 : SAUVEGARDER ET QUITTER        ");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(" Choix : ? ");
	}
	
	public void line() { System.out.println("---------------------------------------------");}
	public void askPlayerName() { System.out.println("Entrez le nom du joueur : "); }
	public void askPlayerFirstName() { System.out.println("Entrer le prenom du joueur : "); }
	public void askPlayerHandle() { System.out.println("Entrer le pseudo du joueur : "); }
	public void askSavePlayer() { System.out.println("Enregistrer le joueur ? / OUI = 1 : NON = 2 ");}
	public void askIfNewPlayer() { System.out.println("Voulez-vous rentrer un nouveau joueur ?  OUI = 1 / NON = 2 "); }
	public void playerSaveTitle() { System.out.println("Joueur enregistr� ! "); }
	public void playerNotSaveTitle() { System.out.println("Joueur non enregistr� !"); }
	public void numberMaxPlayersTitle() { System.out.println("Nombre de joueurs maximal atteint ! "); }
	public void badSeazureTitle() { System.out.println("Erreur de saisie , recommencez ! "); }
	public void askCardTitle() { System.out.println("Carte : "); }
	public void noPairOfCardsTitle() { System.out.println("Vous n'avez pas trouvé de paire de cartes !"); }
	public void pairOfCardsTitle() { System.out.println("Bravo, vous avez trouvé une paire de cartes !"); }
	public void cardAlreadyChooseTitle() { System.out.println("Carte déjà choisie. Recommencez! "); }
	public void noMoreCardsTitle() { System.out.println("Plus de cartes à retourner!"); }
	public void askPlayerTitle(int indiceJoueur, String pseudoJoueur, int pointsJoueur) { 
		System.out.println("JOUEUR "+indiceJoueur+" / "+pseudoJoueur+" / Vous avez "+pointsJoueur+" point(s) : Veuillez choisir 2 cartes à retourner (de 1 à 40) :");
	}
	public void askNameForGameTitle() { System.out.println("Donner un nom à votre partie : "); }
	public void listOfGamesTitle() { System.out.println("Liste des parties disponibles: "); }
	public void askGameToChooseTitle() { System.out.println("Choisir une partie: "); }
	
	public int getChoice(int lowGap,int highGap) {
		int returnValue = 0;
		boolean bool = false;
		do {
			try {	
				int choice = 0;
				choice = sc.nextInt();
				if(choice < lowGap || choice > highGap) {
					System.out.println("Saisie incorrecte, recommencez!");
					sc.nextLine();
				}else {
					System.out.println("Saisie correcte!");
					bool = true;
					returnValue = choice;
				}	
			}catch(InputMismatchException e) { 
				System.out.println("Saisie invalide, recommencez!");
				sc.nextLine();
			}
		}
		while (!bool);
		return returnValue;
	}
	
	public String getStringText() {
		String text;
		text = sc.next();
		return text;
		
	}

	public String playersListDisplay(List<Player> j) {
		String text = null;
		for(int i = 0 ; i < j.size(); i++) {
			text+=j.get(i).toString();
		}
		return text;
	}
}
