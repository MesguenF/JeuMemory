package businessObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import dao.CarteDAO;
import dao.Connexion;
import dao.DistributionDAO;
import dao.JoueurDAO;
import dao.ParticipationDAO;
import dao.PartieDAO;
import jeu.cartes.Joueur;
import jeu.cartes.PaquetCartes;
import jeu.cartes.Partie;
import jeu.cartes.carte.Carte;
import jeu.cartes.carte.Symbole;


public class ControleurMemory {
	private static final int NBR_JOUEURS = 4;		/*Nombre de joueurs max*/
	public static PaquetCartes paquet; 				// mod�le
	public Scanner sc = new Scanner(System.in);
	public VueMemory vueMemory = new VueMemory(); 	// vu
	public static List<Joueur> joueurs = new ArrayList <Joueur>();	//Liste des joueurs
	public int choix;
	public int numPartie;	//Pour BD
	public String nomPartie;
	public boolean saveGame = false;
				
	public ControleurMemory(){
		super();
		
		initialiserPartie();			/*Initialisation du paquet de cartes + Menu principal avec choix*/
				
		/*----------------------------------------SI NOUVELLE PARTIE-------------------------------------*/
		if (choix == 1) {
			
			/*GESTION DES JOUEURS*/
			saisieDesJoueurs();
			
			/*GESTION DES CARTES*/							
			int decomptePairesDeCartes = PaquetCartes.NBR_CARTES / 2;				/*Pour decompte paires de cartes*/
			do {	
				int comptageJoueurs = 0;
				do {
					int carte1 = 0;
					int carte2 = 0;
					/*int cptCartes = 0;*/
					boolean bool = false;	
					
					/*Affichage Demande coup joueur avec caract�ristiques du joueur*/
					vueMemory.demanderCoupJoueur(joueurs.get(comptageJoueurs).indiceJoueur, joueurs.get(comptageJoueurs).pseudoJoueur, joueurs.get(comptageJoueurs).nbPointsJoueur);

					/*Pour affichage du paquet*/
					String[] lesLignesDuPaquet = this.genererStringPaquet();			
					vueMemory.afficherPaquet(lesLignesDuPaquet);

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
									if(carte2 == carte1) { vueMemory.afficherCarteDejaChoisie();}
									vueMemory.demanderCarte();
									carte2 = vueMemory.recupIntChoix(PaquetCartes.NBR_CARTES);
								}while(carte2 == carte1);
								//-----------
								cptCartes+= 1;
								paquet.modifierVisibiliteCarte(carte2 - 1, true);
								lesLignesDuPaquet = this.genererStringPaquet();	
								vueMemory.afficherPaquet(lesLignesDuPaquet);
							}
							/*Pour carte 1*/
							if(cptCartes == 0) {
								vueMemory.demanderCarte();
								carte1 = vueMemory.recupIntChoix(PaquetCartes.NBR_CARTES);
								cptCartes+= 1;
								paquet.modifierVisibiliteCarte(carte1 - 1, true);
								lesLignesDuPaquet = this.genererStringPaquet();	
								vueMemory.afficherPaquet(lesLignesDuPaquet);
							}
						}
						/*Pour tester si paire de cartes identiques*/
						System.out.println((PaquetCartes.cartes.get(carte1-1).getSymbole()).equals((PaquetCartes.cartes.get(carte2-1)).getSymbole()));
						if((PaquetCartes.cartes.get(carte1-1).getSymbole()).equals((PaquetCartes.cartes.get(carte2-1)).getSymbole())) {
							/*Si oui*/
							bool = true;
							decomptePairesDeCartes-= 1;
							vueMemory.affiherFelicitations();
							joueurs.get(comptageJoueurs).setNbPointsJoueur(1);
							
						}else{
							/*Si non*/
							bool = false;
							vueMemory.afficherDesole();
							paquet.modifierVisibiliteCarte(carte1 - 1, false);
							paquet.modifierVisibiliteCarte(carte2 - 1, false);
							long start=System.nanoTime();
							while((System.nanoTime()-start)<1000000000);
						}
					}while(bool == true);
					//-----------
					/*Pour roulement des joueurs*/
					if(comptageJoueurs == joueurs.size()-1) {
						comptageJoueurs = 0;
					}else{
						comptageJoueurs+= 1;
					}
				}while(comptageJoueurs != 0);
				
				
				/*SAUVEGARDE PARTIE*/
				vueMemory.afficherMenuContinuerOuSauvegarder();	
				int testSauvegarde = vueMemory.recupIntChoix(2);
				if(testSauvegarde == 2) {
					saveGame = true;
					//Création partie dans TABLE PARTIE BD FONCTIONNE
					vueMemory.donnerNomPartie();
					nomPartie = vueMemory.recupString();
					Partie nouvPartie = new Partie(nomPartie);
					PartieDAO.getInstance().create(nouvPartie);
					System.out.println(nouvPartie.getNomPartie());
					
					//Ajout des joueurs dans TABLE Joueur BD FONCTIONNE
					for(int i  = 0; i < joueurs.size(); i++) {
						Joueur nouvJoueur = new Joueur(joueurs.get(i).nomJoueur,joueurs.get(i).prenomJoueur,joueurs.get(i).pseudoJoueur);
						JoueurDAO.getInstance().create(nouvJoueur);
						System.out.println(nouvJoueur);
						//Remplissage TABLE PARTICIPATION BD FONCTIONNE
						ParticipationDAO.createParticipation(nouvJoueur.getNumJoueur(), nouvPartie.getNumPartie(), 0 , nouvJoueur.getNbPointsJoueur(), i + 1);
					}
					
					//Stockage des cartes dans TABLE CARTE BD FONCTIONNE (10 cartes avec un symbole différent)
					for(int i = 0; i < 10; i++) {
						Carte nouvCarte = new Carte(Symbole.getSymbole(i),false);
						CarteDAO.getInstance().create(nouvCarte);
						for(int j = 0; j < PaquetCartes.NBR_CARTES; j++){
							if(nouvCarte.getOrdinal(nouvCarte.getSymbole()) == paquet.get(j).getOrdinal(paquet.get(j).getSymbole())){
								//Remplissage TABLE DISTRIBUTION BD FONCTIONNE
								DistributionDAO.createDistribution(nouvPartie.getNumPartie(),nouvCarte.getNumCarte(), j ,nouvCarte.visibleBool);
							}
						}
					}	
					Connexion.fermer();
					vueMemory.afficherSauvegardeOK();
					//TODO TEMPO
					//TODO FERMER CONSOLE
				}
			}while(decomptePairesDeCartes != 0 && saveGame != true); 	//Fin de partie quand plus de paires de cartes 			
			if(decomptePairesDeCartes == 0) {
				vueMemory.afficherPlusDeCartes();
				//TODO CLASSEMENT
			}
			vueMemory.afficherFinDeParte();
			//TODO TEMPO
			//TODO FERMER CONSOLE
			
			//TODO AFFICHAGE CLASSEMENT tri
			/*int MaxPoints = 0;
			int indiceJoueurGagnant = 0;
			for(int i = 0 ; i < joueurs.size(); i++) {
				if(MaxPoints < joueurs.get(i).getNbPointsJoueur()) { 
					MaxPoints = joueurs.get(i).getNbPointsJoueur();
					indiceJoueurGagnant = joueurs.get(i).getIndiceJoueur();
				}
			}*/
		}

	
		/*---------------------------------SI CHARGEE PARTIE-------------------------------------*/
		else if(choix == 2) {
			PartieDAO.getInstance().read(9);			
			
			
			System.out.println("CHARGEE PARTIE");
			//ihmConsole.afficherChargerPartie();
			//TODO restauration de partie
			//ResultSet res = Connexion.executeQuery("SELECT *FROM JOUEUR");
			//Connexion.fermer();
			
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
		vueMemory.afficherNouvellePartie();
		int test;
		int compteurJoueur = 0;				//Pour compter nombre de joueur
		do {
			/*Saisie Joueur*/
			vueMemory.demanderNomJoueur();
			String nom = vueMemory.recupString();
			vueMemory.demanderPrenomJoueur();
			String prenom = vueMemory.recupString();
			vueMemory.demanderPseudoJoueur();
			String pseudo = vueMemory.recupString();
			/*Enregistrement joueur*/
			vueMemory.enregistrerJoueur();
			test = vueMemory.recupIntChoix(2);
					/*Si oui*/
			if(test == 1) {
				compteurJoueur+= 1;
				Joueur j = new Joueur(nom, prenom, pseudo, compteurJoueur,0);
				joueurs.add(j);
				vueMemory.afficheJoueurEnregistre();
				vueMemory.afficheListeJoueurs(joueurs);
			}else{	/*Si non*/
				vueMemory.afficheJoueurNonEnregistre();
			}
			/*Autre joueur ?*/
			if(joueurs.size() < NBR_JOUEURS ) {
				vueMemory.demanderAjoutJoueur();
				test = vueMemory.recupIntChoix(2);
			}else {
				vueMemory.afficheJoueurMaxAtteint();
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
		paquet = new PaquetCartes();								/*Cr�ation d'un paquet de cartes*/
		
		/*System.out.println(PaquetCartes.cartes);*/
		vueMemory.afficherTitreMemory();							/*Appel titre console*/
		String[] lesLignesDuPaquet = this.genererStringPaquet();
		vueMemory.afficherPaquet(lesLignesDuPaquet);				/*Affichage Plateau de cartes*/
		vueMemory.afficherMenuChoix();								/*Affichage menu principal*/
		choix = vueMemory.recupIntChoix(3);							/*R�ception du choix du menu principal*/
	}
	
	/*M�thode pour afficher le paquet de cartes*/ 
	private String[] genererStringPaquet () {
		final int NBR_LIGNES = PaquetCartes.NBR_CARTES/5;
		String[] retour = new String[NBR_LIGNES];
		int compteur = 0;
		
		for(int i = 0 ; i < NBR_LIGNES ; i++){
			String ligne ="";
			for(int j = 0; j < PaquetCartes.NBR_CARTES/8 ; j++) {
				compteur+= 1;
				if(PaquetCartes.cartes.get(compteur-1).isVisible()) {
					ligne+= (PaquetCartes.cartes.get(compteur-1)).getSymbole();
				}else{
					ligne+= (PaquetCartes.cartes.get(i).afficherCarteAvecNumero(compteur));
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