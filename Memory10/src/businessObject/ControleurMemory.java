package businessObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import dao.CardDAO;
import dao.Connexion;
import dao.DistributionDAO;
import dao.PlayerDAO;
import dao.ParticipationDAO;
import dao.GameDAO;
import jeu.cartes.Player;
import jeu.cartes.CardPack;
import jeu.cartes.Game;
import jeu.cartes.carte.Card;
import jeu.cartes.carte.Symbol;

public class ControleurMemory {
	private static final int NBR_JOUEURS = 4;		/*Nombre de joueurs max*/
	public static CardPack paquet; 				// mod�le
	public Scanner sc = new Scanner(System.in);
	public VueMemory vueMemory = new VueMemory(); 	// vu
	public static List<Player> joueurs = new ArrayList <Player>();	//Liste des joueurs
	public int choix;
	public int numPartie;	//Pour BD
	public String nomPartie;
	public boolean saveGame = false;
	public int main;
				
	public ControleurMemory(){
		super();
		
		initialiserPartie();			/*Initialisation du paquet de cartes + Menu principal avec choix*/
		
		/*//Si TABLE CRATE VIDE : Stockage des cartes dans TABLE CARTE BD FONCTIONNE (10 cartes avec un symbole différent)
		//TODO TEST SI TABLE VIDE
		for(int i = 0; i < 10; i++) {
			Carte nouvCarte = new Carte(Symbole.getSymbole(i),false);
			CarteDAO.getInstance().create(nouvCarte);
			}	
		Connexion.fermer();*/
				
		/*----------------------------------------SI CHARGER PARTIE-------------------------------------*/
		if (choix == 2) {
			
			/*Lecture des parties de la table*/
			vueMemory.listOfGamesTitle();
			ArrayList<Game> listeDesParties = GameDAO.getInstance().readAll();
			int tailleListe = listeDesParties.size();
			
			/*Choix partie*/
			//TODO TEST SI PAS DE PARTIE DANS BD
			vueMemory.gameToChooseTitle();
			int choixPartie = vueMemory.getChoice((listeDesParties.get(0).getGameNumber()),(listeDesParties.get(tailleListe - 1).getGameNumber()));
			System.out.println(choixPartie);
			/*Chargement de la partie*/
			Game newPartie = GameDAO.getInstance().read(choixPartie);
			System.out.println(newPartie);
			/*Création de la distribution*/
			CardPack newpaquet = DistributionDAO.readDistribution(newPartie);
			
			/*Création de la participation*/
			ParticipationDAO.readParticipation(newPartie);
						
			
			System.out.println("CHARGEE PARTIE");
			//ihmConsole.afficherChargerPartie();
			//TODO restauration de partie
			//ResultSet res = Connexion.executeQuery("SELECT *FROM JOUEUR");
			//Connexion.fermer();
		}
		/*---------------------------------SI NOUVELLE PARTIE-------------------------------------*/
		else if(choix == 1) {
			/*GESTION DES JOUEURS*/
			saisieDesJoueurs();
			
			/*GESTION DES CARTES*/							
			int decomptePairesDeCartes = CardPack.NBR_CARDS / 2;				/*Pour decompte paires de cartes*/
			do {	
				int comptageJoueurs = 0;
				main = comptageJoueurs + 1;
				do {
					int carte1 = 0;
					int carte2 = 0;
					boolean bool = false;	
					
					/*Affichage Demande coup joueur avec caract�ristiques du joueur*/
					vueMemory.askPlayerTitle(joueurs.get(comptageJoueurs).playerPosition, joueurs.get(comptageJoueurs).playerHandle, joueurs.get(comptageJoueurs).playerScore);

					/*Pour affichage du paquet*/
					String[] lesLignesDuPaquet = this.genererStringPaquet();			
					vueMemory.packDisplay(lesLignesDuPaquet);
					System.out.println("Tour Joueur: " + main);
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
									if(carte2 == carte1) { vueMemory.cardAlreadyChooseTitle();}
									vueMemory.askCardTitle();
									carte2 = vueMemory.getChoice(1,CardPack.NBR_CARDS);
								}while(carte2 == carte1);
								//-----------
								cptCartes+= 1;
								paquet.modifyCardVisibility(carte2 - 1, true);
								lesLignesDuPaquet = this.genererStringPaquet();	
								vueMemory.packDisplay(lesLignesDuPaquet);
							}
							/*Pour carte 1*/
							if(cptCartes == 0) {
								vueMemory.askCardTitle();
								carte1 = vueMemory.getChoice(1,CardPack.NBR_CARDS);
								cptCartes+= 1;
								paquet.modifyCardVisibility(carte1 - 1, true);
								lesLignesDuPaquet = this.genererStringPaquet();	
								vueMemory.packDisplay(lesLignesDuPaquet);
							}
						}
						/*Pour tester si paire de cartes identiques*/
						System.out.println((CardPack.theCards.get(carte1-1).getSymbol()).equals((CardPack.theCards.get(carte2-1)).getSymbol()));
						if((CardPack.theCards.get(carte1-1).getSymbol()).equals((CardPack.theCards.get(carte2-1)).getSymbol())) {
							/*Si oui*/
							bool = true;
							decomptePairesDeCartes-= 1;
							vueMemory.pairOfCardsTitle();
							joueurs.get(comptageJoueurs).setPlayerScore();
							
						}else{
							/*Si non*/
							bool = false;
							vueMemory.noPairOfCardsTitle();
							paquet.modifyCardVisibility(carte1 - 1, false);
							paquet.modifyCardVisibility(carte2 - 1, false);
							long start=System.nanoTime();
							while((System.nanoTime()-start)<1000000000);
						}
					}while(bool == true);
					//-----------
					/*Pour roulement des joueurs*/
					if(comptageJoueurs == joueurs.size()-1) {
						comptageJoueurs = 0;
						main = comptageJoueurs + 1;
					}else{
						comptageJoueurs+= 1;
						main = comptageJoueurs + 1;
					}
				}while(comptageJoueurs != 0);
				
				
				/*SAUVEGARDE PARTIE*/
				vueMemory.nextOrSaveTitle();	
				int testSauvegarde = vueMemory.getChoice(1,2);
				if(testSauvegarde == 2) {
					saveGame = true;
					
					//Création partie dans TABLE PARTIE BD FONCTIONNE
					vueMemory.nameForGameTitle();
					nomPartie = vueMemory.getStringText();
					Game nouvPartie = new Game(nomPartie);
					GameDAO.getInstance().create(nouvPartie);
					System.out.println(nouvPartie.getGameName());
					
					//Ajout des joueurs dans TABLE JOUEUR BD FONCTIONNE
					for(int i  = 0; i < joueurs.size(); i++) {
						Player nouvJoueur = new Player(joueurs.get(i).playerLastName,joueurs.get(i).playerFirstName,joueurs.get(i).playerHandle);
						PlayerDAO.getInstance().create(nouvJoueur);
						System.out.println(nouvJoueur);
						//Remplissage TABLE PARTICIPATION BD FONCTIONNE
						ParticipationDAO.createParticipation(nouvJoueur.getPlayerNumber(), nouvPartie.getGameNumber(), main , nouvJoueur.getPlayerScore(), i + 1);
					}
					
					//Stockage des cartes dans TABLE CARTE BD FONCTIONNE (10 cartes avec un symbole différent)
					for(int i = 0; i < 10; i++) {
						Card nouvCarte = new Card(Symbol.getSymbol(i),false);
						CardDAO.getInstance().create(nouvCarte);
						for(int j = 0; j < CardPack.NBR_CARDS; j++){
							if(nouvCarte.getOrdinal(nouvCarte.getSymbol()) == paquet.getCard(j).getOrdinal(paquet.getCard(j).getSymbol())){
								//Remplissage TABLE DISTRIBUTION BD FONCTIONNE
								DistributionDAO.createDistribution(nouvPartie.getGameNumber(),nouvCarte.getCardNumber(), j ,nouvCarte.visibleBool);
							}
						}
					}	
					Connexion.fermer();
					vueMemory.backupTitle();
					//TODO TEMPO
					//TODO FERMER CONSOLE
				}
			}while(decomptePairesDeCartes != 0 && saveGame != true); 	//Fin de partie quand plus de paires de cartes 			
			
			
			if(decomptePairesDeCartes == 0) {
				vueMemory.noMoreCardsTitle();
				//TODO CLASSEMENT
			}
			vueMemory.endOfGameTitle();
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
		vueMemory.newGameTitle();
		int test;
		int compteurJoueur = 0;				//Pour compter nombre de joueur
		do {
			/*Saisie Joueur*/
			vueMemory.askPlayerName();
			String nom = vueMemory.getStringText();
			vueMemory.askPlayerFirstName();
			String prenom = vueMemory.getStringText();
			vueMemory.askPlayerHandle();
			String pseudo = vueMemory.getStringText();
			/*Enregistrement joueur*/
			vueMemory.askSavePlayer();
			test = vueMemory.getChoice(1,2);
					/*Si oui*/
			if(test == 1) {
				compteurJoueur+= 1;
				Player j = new Player(nom, prenom, pseudo, compteurJoueur,0);
				joueurs.add(j);
				vueMemory.playerSaveTitle();
				vueMemory.playersListDisplay(joueurs);
			}else{	/*Si non*/
				vueMemory.playerNotSaveTitle();
			}
			/*Autre joueur ?*/
			if(joueurs.size() < NBR_JOUEURS ) {
				vueMemory.askIfNewPlayer();
				test = vueMemory.getChoice(1,2);
			}else {
				vueMemory.numberMaxPlayersTitle();
				fairePause();
				test = 2;
			}
		}while(test == 1);
	}
	
	/* MAIN */
	public static void main(String[] args){
		new ControleurMemory();
		}

	/*M�thode pour initialiser la partie*/
	public void initialiserPartie() {
		paquet = new CardPack();								/*Cr�ation d'un paquet de cartes*/
		/*System.out.println(PaquetCartes.cartes);*/
		vueMemory.memoryTitle();							/*Appel titre console*/
		String[] lesLignesDuPaquet = this.genererStringPaquet();
		vueMemory.packDisplay(lesLignesDuPaquet);				/*Affichage Plateau de cartes*/
		vueMemory.choiceTitle();								/*Affichage menu principal*/
		choix = vueMemory.getChoice(1,3);							/*R�ception du choix du menu principal*/
	}
	
	/*M�thode pour afficher le paquet de cartes*/ 
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
	
	/*M�thode pour afficher la liste des joueurs*/
	public void afficheListeJoueurs() {
		for(int i = 0 ; i < joueurs.size(); i++) {
			joueurs.get(i).toString();
		}
	}
	
	/* M�thode tempo */
	public void fairePause() {
		long depart = new Date().getTime();
		boolean fini = false;
		while (!fini) {
			fini = (new Date().getTime() - depart) > 3000;
		}
	}

	/*public int getNumPartie() { return numPartie; }
	public void setNumPartie(int numPartie) { this.numPartie = numPartie; }
	public String getNomPartie() { return nomPartie; }
	public void setNomPartie(String nomPartie) { this.nomPartie = nomPartie; }
	public java.sql.Date getDatePartie() { return (java.sql.Date) datePartie; }
	public void setDatePartie(Date datePartie) { this.datePartie = datePartie; }*/

}