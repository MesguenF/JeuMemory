package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import dao.CardDAO;
import dao.Connexion;
import dao.DistributionDAO;
import dao.GameDAO;
import dao.ParticipationDAO;
import dao.PlayerDAO;
import model.cartes.CardPack;
import model.cartes.Game;
import model.cartes.Player;
import model.cartes.carte.Card;
import model.cartes.carte.Symbol;
import view.MemoryConsoleView;

public class MemoryController {
	private static final int NBR_PLAYERS = 4;		
	public static CardPack pack; 				//model
	public Scanner sc = new Scanner(System.in);
	public MemoryConsoleView memoryView = new MemoryConsoleView(); 	// vu
	public static List<Player> players = new ArrayList <Player>();	//Liste des joueurs
	public int choice;
	public int gameNumber;	//For Data Base
	public String gameName;
	public boolean saveGame = false;
	public int hand;
				
	public MemoryController(){
		super();
		
		initialiserPartie();			/*Initialisation du paquet de cartes + Menu principal avec choix*/
		
		/*//Si TABLE CRATE VIDE : Stockage des cartes dans TABLE CARTE BD FONCTIONNE (10 cartes avec un symbole différent)
		//TODO TEST SI TABLE VIDE
		for(int i = 0; i < 10; i++) {
			Carte nouvCarte = new Carte(Symbole.getSymbole(i),false);
			CarteDAO.getInstance().create(nouvCarte);
			}	
		Connexion.fermer();*/
				
		/*----------------------------------------SI CHARGEMENT DE PARTIE-------------------------------------*/
		if (choice == 2) {
			/*Affichage des parties sauvegardées*/
			memoryView.listOfGamesTitle();
			ArrayList<Game> listOfGames = GameDAO.getInstance().readAll();
						
			/*Choix partie*/
			//TODO TEST SI PAS DE PARTIE DANS BD
			memoryView.askGameToChooseTitle();
			int gameChoice = memoryView.getChoice((listOfGames.get(0).getGameNumber()), (listOfGames.get((listOfGames.size()) - 1).getGameNumber()));
						
			/*Chargement de la partie*/
			Game newPartie = GameDAO.getInstance().read(gameChoice);
			System.out.println(newPartie);
			
			/*Création de la distribution*/
			CardPack newpaquet = DistributionDAO.readDistribution(newPartie);
			
			/*Création de la participation*///TODO A CONTINUER
			ParticipationDAO.readParticipation(newPartie);
						
			memoryView.loadedGameTitle();
			System.out.println("CHARGEMENT PARTIE TERMINEE");
			}
		/*---------------------------------SI NOUVELLE PARTIE-------------------------------------*/
		else if(choice == 1) {
			/*GESTION DES JOUEURS*/
			saisieDesJoueurs();
			
			/*GESTION DES CARTES*/							
			int decomptePairesDeCartes = CardPack.NBR_CARDS / 2;				/*Pour decompte paires de cartes*/
			do {	
				int comptageJoueurs = 0;
				hand = comptageJoueurs + 1;
				do {
					int carte1 = 0;
					int carte2 = 0;
					boolean bool = false;	
					
					/*Affichage Demande coup joueur avec caract�ristiques du joueur*/
					memoryView.askPlayerTitle(players.get(comptageJoueurs).playerPosition, players.get(comptageJoueurs).playerHandle, players.get(comptageJoueurs).playerScore);

					/*Pour affichage du paquet*/
					String[] lesLignesDuPaquet = this.genererStringPaquet();			
					memoryView.packDisplay(lesLignesDuPaquet);
					System.out.println("Tour Joueur: " + hand);
					/*Pour le choix des cartes*/
					//------------
					do {
						bool = false;	
						int cptCartes = 0;
						for(int i = 0 ; i < 2 ; i++){
							/*Pour carte 2*/
							if(cptCartes == 1) {
								//------------
								do{
									if(carte2 == carte1) { memoryView.cardAlreadyChooseTitle();}
									memoryView.askCardTitle();
									carte2 = memoryView.getChoice(1,CardPack.NBR_CARDS);
								}while(carte2 == carte1);
								//-----------
								cptCartes+= 1;
								pack.modifyCardVisibility(carte2 - 1, true);
								lesLignesDuPaquet = this.genererStringPaquet();	
								memoryView.packDisplay(lesLignesDuPaquet);
							}
							/*Pour carte 1*/
							if(cptCartes == 0) {
								memoryView.askCardTitle();
								carte1 = memoryView.getChoice(1,CardPack.NBR_CARDS);
								cptCartes+= 1;
								pack.modifyCardVisibility(carte1 - 1, true);
								lesLignesDuPaquet = this.genererStringPaquet();	
								memoryView.packDisplay(lesLignesDuPaquet);
							}
						}
						/*Pour tester si paire de cartes identiques*/
						System.out.println((CardPack.theCards.get(carte1-1).getSymbol()).equals((CardPack.theCards.get(carte2-1)).getSymbol()));
						if((CardPack.theCards.get(carte1-1).getSymbol()).equals((CardPack.theCards.get(carte2-1)).getSymbol())) {
							/*Si oui*/
							bool = true;
							decomptePairesDeCartes-= 1;
							memoryView.pairOfCardsTitle();
							players.get(comptageJoueurs).setPlayerScore();
							
						}else{
							/*Si non*/
							bool = false;
							memoryView.noPairOfCardsTitle();
							pack.modifyCardVisibility(carte1 - 1, false);
							pack.modifyCardVisibility(carte2 - 1, false);
							long start=System.nanoTime();
							while((System.nanoTime()-start)<1000000000);
						}
					}while(bool == true);
					//-----------
					/*Pour roulement des joueurs*/
					if(comptageJoueurs == players.size()-1) {
						comptageJoueurs = 0;
						hand = comptageJoueurs + 1;
					}else{
						comptageJoueurs+= 1;
						hand = comptageJoueurs + 1;
					}
				}while(comptageJoueurs != 0);
				
				
				/*SAUVEGARDE PARTIE*/
				memoryView.nextOrSaveTitle();	
				int testSauvegarde = memoryView.getChoice(1,2);
				if(testSauvegarde == 2) {
					saveGame = true;
					
					//Création partie dans TABLE PARTIE BD FONCTIONNE
					memoryView.askNameForGameTitle();
					gameName = memoryView.getStringText();
					Game nouvPartie = new Game(gameName);
					GameDAO.getInstance().create(nouvPartie);
					System.out.println(nouvPartie.getGameName());
					
					//Ajout des joueurs dans TABLE JOUEUR BD FONCTIONNE
					for(int i  = 0; i < players.size(); i++) {
						Player nouvJoueur = new Player(players.get(i).playerLastName,players.get(i).playerFirstName,players.get(i).playerHandle);
						PlayerDAO.getInstance().create(nouvJoueur);
						System.out.println(nouvJoueur);
						//Remplissage TABLE PARTICIPATION BD FONCTIONNE
						ParticipationDAO.createParticipation(nouvJoueur.getPlayerNumber(), nouvPartie.getGameNumber(), hand , nouvJoueur.getPlayerScore(), i + 1);
					}
					
					//Stockage des cartes dans TABLE CARTE BD FONCTIONNE (10 cartes avec un symbole différent)
					for(int i = 0; i < 10; i++) {
						Card nouvCarte = new Card(Symbol.getSymbol(i),false);
						CardDAO.getInstance().create(nouvCarte);
						for(int j = 0; j < CardPack.NBR_CARDS; j++){
							if(nouvCarte.getOrdinal(nouvCarte.getSymbol()) == pack.getCard(j).getOrdinal(pack.getCard(j).getSymbol())){
								//Remplissage TABLE DISTRIBUTION BD FONCTIONNE
								DistributionDAO.createDistribution(nouvPartie.getGameNumber(),nouvCarte.getCardNumber(), j ,nouvCarte.visibleBool);
							}
						}
					}	
					Connexion.fermer();
					memoryView.backupTitle();
					//TODO TEMPO
					//TODO FERMER CONSOLE
				}
			}while(decomptePairesDeCartes != 0 && saveGame != true); 	//Fin de partie quand plus de paires de cartes 			
			
			
			if(decomptePairesDeCartes == 0) {
				memoryView.noMoreCardsTitle();
				//TODO CLASSEMENT
			}
			memoryView.endOfGameTitle();
			//TODO TEMPO
			//TODO FERMER CONSOLE
			
			//TODO AFFICHAGE CLASSEMENT AVEC TRI
			/*int MaxPoints = 0;
			int indiceJoueurGagnant = 0;
			for(int i = 0 ; i < joueurs.size(); i++) {
				if(MaxPoints < joueurs.get(i).getNbPointsJoueur()) { 
					MaxPoints = joueurs.get(i).getNbPointsJoueur();
					indiceJoueurGagnant = joueurs.get(i).getIndiceJoueur();
				}
			}*/
			
		}else {
			System.out.println("SORTIE PROGRAMME");
			/*System.exit(1);*/
			System.exit(1);
			//TODO FERMER CONSOLE
		}
		sc.close();
	}

	/*------------------------------------FIN CONSTRUCTEUR----------------------------------------*/

	public void saisieDesJoueurs() {
		memoryView.newGameTitle();
		int test;
		int compteurJoueur = 0;				//Pour compter nombre de joueur
		do {
			/*Saisie Joueur*/
			memoryView.askPlayerName();
			String nom = memoryView.getStringText();
			memoryView.askPlayerFirstName();
			String prenom = memoryView.getStringText();
			memoryView.askPlayerHandle();
			String pseudo = memoryView.getStringText();
			/*Enregistrement joueur*/
			memoryView.askSavePlayer();
			test = memoryView.getChoice(1,2);
					/*Si oui*/
			if(test == 1) {
				compteurJoueur+= 1;
				Player j = new Player(nom, prenom, pseudo, compteurJoueur,0);
				players.add(j);
				memoryView.playerSaveTitle();
				memoryView.playersListDisplay(players);
			}else{	/*Si non*/
				memoryView.playerNotSaveTitle();
			}
			/*Autre joueur ?*/
			if(players.size() < NBR_PLAYERS ) {
				memoryView.askIfNewPlayer();
				test = memoryView.getChoice(1,2);
			}else {
				memoryView.numberMaxPlayersTitle();
				breakTime();
				test = 2;
			}
		}while(test == 1);
	}
	
	/* MAIN */
	public static void main(String[] args){
		new MemoryController();
		}

	public void initialiserPartie() {
		pack = new CardPack();								/*Cr�ation d'un paquet de cartes*/
		/*System.out.println(PaquetCartes.cartes);*/
		memoryView.memoryTitle();							/*Appel titre console*/
		String[] lesLignesDuPaquet = this.genererStringPaquet();
		memoryView.packDisplay(lesLignesDuPaquet);				/*Affichage Plateau de cartes*/
		memoryView.choiceTitle();								/*Affichage menu principal*/
		choice = memoryView.getChoice(1,3);							/*R�ception du choix du menu principal*/
	}
	
	private String[] genererStringPaquet () {
		final int NBR_LIGNES = CardPack.NBR_CARDS/5;
		String[] retour = new String[NBR_LIGNES];
		int compteur = 0;
		
		for(int i = 0 ; i < NBR_LIGNES ; i++){
			String ligne ="";
			for(int j = 0; j < CardPack.NBR_CARDS/8 ; j++) {
				compteur+= 1;
				if(CardPack.theCards.get(compteur-1).isVisible()) {
					ligne+= (CardPack.theCards.get(compteur-1)).getSymbol();
				}else{
					ligne+= (CardPack.theCards.get(i).cardWithNumber(compteur));
				}
			}
			retour[i]=ligne;
		}
		return retour;
	}
	
	public void playersListDisplay() {
		for(int i = 0 ; i < players.size(); i++) {
			players.get(i).toString();
		}
	}
	
	public void breakTime() {
		long start = new Date().getTime();
		boolean end = false;
		while (!end) {
			end = (new Date().getTime() - start) > 3000;
		}
	}
}