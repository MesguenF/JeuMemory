package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import view.MemoryConsoleView;

public class MemoryMainController {
	private Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Bienvenue dans votre jeu MEMORY!\n"); 
		System.out.println("Veuillez choisir un mode de jeu :  \n");
		System.out.println("Mode CONSOLE: Touche 1 / Mode GRAPHIQUE: Touche 2");
		int testChoice = MemoryMainController.getChoice(1,2);
		
		new MemoryConsoleController();
		/*new MemoryJavaFXViewController();*/
	}
	
	
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
	
	
	
}


