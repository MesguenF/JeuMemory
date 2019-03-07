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
	public int decomptePairesDeCartes;
	public int tourJoueur;
				
	public MemoryController(){
		super();
		/** Démarrage avec titre du jeu et menu de choix. */
		memoryView.memoryTitle();	
		memoryView.choiceTitle();								
		choice = memoryView.getChoice(1,3);						
		/*//Si TABLE CRATE VIDE : Stockage des cartes dans TABLE CARTE BD FONCTIONNE (10 cartes avec un symbole différent)
		//TODO TEST SI TABLE VIDE
		for(int i = 0; i < 10; i++) {
			Carte nouvCarte = new Carte(Symbole.getSymbole(i),false);
			CarteDAO.getInstance().create(nouvCarte);
			}	
		Connexion.fermer();*/
		/*******************************************
		 * *GESTION CREATION D'UNE NOUVELLE PARTIE.*
		 * ****************************************/
		 if(choice == 1) {
			/** On crée un nouveau paquet de cartes */
			pack = new CardPack();
			
			/** On saisie les nouveaux joueurs */
			enterNewPlayers();
			
			/** Compteur pour le nombre de paires de cartes visibles (taille paquet divisée par 2) */
			decomptePairesDeCartes = CardPack.NBR_CARDS / 2; ;
			
			/** Détermine le joueur ayant la main dans la partie. Ici le joueur 1 pour un nouveau paquet */
			tourJoueur=1;
		}
		 
		/************************************
		 * *GESTION CHARGEMENT D'UNE PARTIE.*
		 * *********************************/
		 if (choice == 2) {
			/** On récupére et affiche la liste des parties stockées dans la base de données dans la table PARTIE */
			memoryView.listOfGamesTitle();
			ArrayList<Game> listOfGames = GameDAO.getInstance().readAllGames();
			System.out.println(listOfGames);
			//TODO TEST SI PAS DE PARTIE DANS BD
			
			/** On choisit une partie dans la liste */
			memoryView.askGameToChooseTitle();
			int gameChoice = memoryView.getChoice((listOfGames.get(0).getGameNumber()), (listOfGames.get((listOfGames.size()) - 1).getGameNumber()));
			
			/** On récupére la partie choisie dans la BD et on affiche ces caractèristiques: */
			Game gameLoaded = GameDAO.getInstance().read(gameChoice);
			System.out.println("La partie chargée est la suivante : " + gameLoaded);
			
			/** On récupére la distibution dans la BD correspondant à un paquet de cartes rangées en fonction de leurs positions sauvegardées à partir de la table DISTRIBUTION. */
			pack = DistributionDAO.readDistribution(gameLoaded);
			
			/** On parcourt le paquet chargé pour savoir combien de cartes sont visibles et remplir le compteur de paires de cartes. */
			for(int i = 0; i< pack.size();i++) {
				if(pack.getCard(i).isVisible()) { decomptePairesDeCartes += 1; }
			}
			decomptePairesDeCartes = decomptePairesDeCartes/2;
			
			/**
			 * On récupére la participation dans la BD.
			 * Cette distribution correspond à une liste de joueurs à partir de la table PARTICIPATION.
			 * loadPlayers est une liste contenant des tableaux contenant eux mêmes les caractéristiques pour chaque joueur:
			 * idJoueur
			 * main
			 * scoreJoueur
			 * positionTour
			 */
			ArrayList<int[]> loadPlayers = ParticipationDAO.readParticipation(gameLoaded);
			System.out.println("Le nombre de joueurs pour cette partie est de : " + loadPlayers.size());
			for(int i = 0; i<loadPlayers.size();i++) {
				int[]tab = loadPlayers.get(i);
				System.out.println("Joueur n° " + tab[0] +"/ main partie: " + tab[1] + "/ score dans partie: " + tab[2] + "/ position dans partie: " + tab[3]);
				tourJoueur = tab[1];				
			}
			System.out.println("C'est au joueur " + tourJoueur + " de jouer!");
			memoryView.loadedGameTitle();
		}
		 
		/***************************************
		 * ON FERME LE PROGRAMME ET LA CONSOLE *
		 ***************************************/
		if(choice == 3){
			System.out.println("SORTIE PROGRAMME");
			//TODO FERMER CONSOLE
		}
		
		/******************
		 ***GESTION DU JEU*
		 ******************/
		 
		do { /** DEBUT Faire Tant qu'il reste paires de cartes à trouver */	
			
			do {
				int card1 = 0;
				int card2 = 0;
				boolean bool = false;	
				/*Affichage Demande coup joueur avec caract�ristiques du joueur*/
				/*memoryView.askPlayerTitle(players.get(hand - 1).playerPosition, players.get(hand - 1).playerHandle, players.get(hand - 1).playerScore);*/
				
				/** Pour affichage du paquet de cartes */
				String[] lesLignesDuPaquet = this.genererStringPaquet();			
				memoryView.packDisplay(lesLignesDuPaquet);
				System.out.println("Tour Joueur: " + tourJoueur);
				
				do { /**DEBUT Faire choix de carte**/
					bool = false;	
					int cptCartes = 0;
					for(int i = 0 ; i < 2 ; i++){
						/**Pour carte 2**/
						if(cptCartes == 1) {
							do{
								if(card2 == card1) { memoryView.cardAlreadyChooseTitle();}
								memoryView.askCardTitle();
								card2 = memoryView.getChoice(1,CardPack.NBR_CARDS);
							}while(card2 == card1);
							//-----------
							cptCartes+= 1;
							pack.modifyCardVisibility(card2 - 1, true);
							lesLignesDuPaquet = this.genererStringPaquet();	
							memoryView.packDisplay(lesLignesDuPaquet);
						}
						/**Pour carte 1**/
						if(cptCartes == 0) {
							memoryView.askCardTitle();
							card1 = memoryView.getChoice(1,CardPack.NBR_CARDS);
							cptCartes+= 1;
							pack.modifyCardVisibility(card1 - 1, true);
							lesLignesDuPaquet = this.genererStringPaquet();	
							memoryView.packDisplay(lesLignesDuPaquet);
						}
					}
					/**Pour tester si paire de cartes identiques**/
					System.out.println((CardPack.theCards.get(card1-1).getSymbol()).equals((CardPack.theCards.get(card2-1)).getSymbol()));
					if((CardPack.theCards.get(card1-1).getSymbol()).equals((CardPack.theCards.get(card2-1)).getSymbol())) {
						/**Si oui**/
						bool = true;
						decomptePairesDeCartes-= 1;
						memoryView.pairOfCardsTitle();
						players.get(tourJoueur - 1).setPlayerScore();
					}else{
						/**Si non**/
						bool = false;
						memoryView.noPairOfCardsTitle();
						pack.modifyCardVisibility(card1 - 1, false);
						pack.modifyCardVisibility(card2 - 1, false);
						long start=System.nanoTime();
						while((System.nanoTime()-start)<1000000000);
					}
				}while(bool == true); /**FIN Faire choix de carte**/
				
				/**Pour roulement des joueurs*/
				if(tourJoueur == players.size()) {
					tourJoueur = 0;
					/*hand = tourJoueur + 1;*/
				}else{
					tourJoueur+= 1;
					/*hand = tourJoueur + 1;*/
				}
				}while(tourJoueur != 0);
				
				
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
						ParticipationDAO.createParticipation(nouvJoueur.getPlayerNumber(), nouvPartie.getGameNumber(), tourJoueur, nouvJoueur.getPlayerScore(), i + 1);
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
			}while(decomptePairesDeCartes != 0 && saveGame != true); /** FIN Faire Tant qu'il reste paires de cartes à trouver */		
			
			
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
		sc.close();
		}
		
	/**
	 * Méthode pour créer de nouveaux joueurs	
	 */
		/*------------------------------------FIN CONSTRUCTEUR----------------------------------------*/

public void enterNewPlayers() {
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

	/**
	 *  Méthode retournant un affichage du paquet
	 */
	public String[] genererStringPaquet () {
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