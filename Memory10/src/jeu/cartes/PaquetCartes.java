package jeu.cartes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jeu.cartes.carte.Carte;
import jeu.cartes.carte.Symbole;

public class PaquetCartes {
	public final static int NBR_CARTES = 40;							/*CONSTANTE nombre de 30 cartes du jeu*/
	public static List<Carte> cartes = new ArrayList <Carte>();			/*Création d'une liste de Cartes*/
	//TODO modifier paquet cartes
	/*Constructeur d'un PaquetCartes*/
	public PaquetCartes() {
		super();
		for(int i = 0; i < NBR_CARTES/4; i++) { 
			nouvelleSerieCarte(i);			/*On crée une paire de cartes avec même symbole et on l'ajoute dans la liste*/
			}
		Collections.shuffle(cartes);		/*On mélange le paquet de cartes*/
	}
	
	/*Création d'une paire de cartes et rajout dans le PaquetCartes*/
	public void nouvelleSerieCarte(int i) {
		Carte c;
		for(int j = 0 ; j<4 ; j++) {
			c = new Carte(Symbole.getSymbole(i),false);
			PaquetCartes.cartes.add((Carte) c);
		}
	}
	
	//TODO A SUPPRIMER
	/*Méthode retournant la carte en indice i de la liste*/
	/*public Carte get(int i) { return PaquetCartes.cartes.get(i); }*/
	
	/*Méthode pour modifier la visibilité d'une carte*/     
	public void modifierVisibiliteCarte(int carteJouee, boolean boolVisible){ PaquetCartes.cartes.get(carteJouee).setVisible(boolVisible); }
			
	/*Méthode retournant la taille du paquet*/
	public int size() { return PaquetCartes.cartes.size(); }
	
}

