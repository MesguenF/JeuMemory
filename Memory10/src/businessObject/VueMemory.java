package businessObject;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import jeu.cartes.Joueur;

public class VueMemory {
	private Scanner sc = new Scanner(System.in);
		
	/*Constructeur*/
	public VueMemory() { 
		super();
	}

	/*Pour affichage du jeu  de cartes*/
	public void afficherPaquet(String[] plateau) {
		for (String string : plateau) { System.out.println(string); }
	}

	/*M�thodes pour affichage de textes*/
	public void afficherTitreMemory() {
		System.out.println("*********************************************");
		System.out.println("*                 JEU MEMORY                *");
		System.out.println("*********************************************");
	}
	public void afficherSauvegardeOK() {
		System.out.println("---------------------------------------------");
		System.out.println("-            SAUVEGARDE REUSSIE             -");
		System.out.println("---------------------------------------------");
	}
	public void afficherFinDeParte() {
		System.out.println("---------------------------------------------");
		System.out.println("-                FIN DE PARTIE              -");
		System.out.println("---------------------------------------------");
	}
	public void afficherNouvellePartie() {
		System.out.println("---------------------------------------------");
		System.out.println("-              NOUVELLE PARTIE              -");
		System.out.println("---------------------------------------------");
	}
	public void afficherChargerPartie() {
		System.out.println("---------------------------------------------");
		System.out.println("-                CHARGER PARTIE             -");
		System.out.println("---------------------------------------------");
	}
	public void afficherMenuChoix() {
		afficheTrait();
		System.out.println("");
		System.out.println(" 1 : NOUVELLE PARTIE ");
		System.out.println("");
		System.out.println(" 2 : CHARGER PARTIE  ");
		System.out.println("");
		System.out.println(" 3 : QUITTER LE JEU  ");
		System.out.println("");
		afficheTrait();
		System.out.println(" Votre choix : ");
	}
	
	public void afficherMenuContinuerOuSauvegarder() {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("       TOUCHE 1 : CONTINUER / TOUCHE 2 : SAUVEGARDER ET QUITTER        ");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(" Choix : ? ");
	}
	
	public void afficheTrait() { System.out.println("---------------------------------------------");}
	public void demanderNomJoueur() { System.out.println("Entrez le nom du joueur : "); }
	public void demanderPrenomJoueur() { System.out.println("Entrer le prenom du joueur : "); }
	public void demanderPseudoJoueur() { System.out.println("Entrer le pseudo du joueur : "); }
	public void enregistrerJoueur() { System.out.println("Enregistrer le joueur ? / OUI = 1 : NON = 2 ");}
	public void demanderAjoutJoueur() { System.out.println("Voulez-vous rentrer un nouveau joueur ?  OUI = 1 / NON = 2 "); }
	public void afficheJoueurEnregistre() { System.out.println("Joueur enregistr� ! "); }
	public void afficheJoueurNonEnregistre() { System.out.println("Joueur non enregistr� !"); }
	public void afficheJoueurMaxAtteint() { System.out.println("Nombre de joueurs maximal atteint ! "); }
	public void afficherErreurSaisie() { System.out.println("Erreur de saisie , recommencez ! "); }
	public void demanderCarte() { System.out.println("Carte : "); }
	public void afficherDesole() { System.out.println("Vous n'avez pas trouvé de paire de cartes !"); }
	public void affiherFelicitations() { System.out.println("Bravo, vous avez trouvé une paire de cartes !"); }
	public void afficherCarteDejaChoisie() { System.out.println("Carte déjà choisie. Recommencez! "); }
	public void afficherPlusDeCartes() { System.out.println("Plus de cartes à retourner!"); }
	public void demanderCoupJoueur(int indiceJoueur, String pseudoJoueur, int pointsJoueur) { 
		System.out.println("JOUEUR "+indiceJoueur+" / "+pseudoJoueur+" / Vous avez "+pointsJoueur+" point(s) : Veuillez choisir 2 cartes à retourner (de 1 à 40) :");
	}
	public void donnerNomPartie() { System.out.println("Donner un nom à votre partie : "); }
	
	/*M�thode pour r�cup�rer INT choix en sp�cifiant l'�cart souhait� ( 1 � 2 / 1 � 3 / 1 � 40) pour le INT attendu*/
	public int recupIntChoix(int ecart) {
		int valRetour = 0;
		boolean valide = false;
		do {
			try {	
				int choix = 0;
				choix = sc.nextInt();
				if(choix < 1 || choix > ecart) {
					System.out.println("Saisie incorrecte, recommencez!");
					sc.nextLine();
				}else {
					System.out.println("Saisie correcte!");
					valide = true;
					valRetour = choix;
				}	
			}catch(InputMismatchException e) { 
				System.out.println("Saisie invalide, recommencez!");
				sc.nextLine();
			}
		}
		while (!valide);
		return valRetour;
	}
	
	/*M�thode pour r�cup�rer un STRING*/
	public String recupString() {
		String texte;
		texte = sc.next();
		return texte;
		
	}

	/*M�thode pour afficher la liste des joueurs*/
	public String afficheListeJoueurs(List<Joueur> j) {
		String texte = null;
		for(int i = 0 ; i < j.size(); i++) {
			texte+=j.get(i).toString();
		}
		return texte;
	}
}
