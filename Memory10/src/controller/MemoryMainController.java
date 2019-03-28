package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MemoryMainController  {
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		System.out.println("Bienvenue dans votre jeu MEMORY!\n"); 
		System.out.println("Veuillez choisir un mode de jeu :  \n");
		System.out.println("Mode CONSOLE: Touche 1 / Mode GRAPHIQUE: Touche 2");
		int choice = 0;
		boolean bool = false;
		do {
			try {	
				choice = sc.nextInt();
				if(choice < 1 || choice > 2) {
					System.out.println("Saisie incorrecte, recommencez!");
					sc.nextLine();
				}else {
					System.out.println("Saisie correcte!");
					bool = true;
				}	
			}catch(InputMismatchException e) { 
				System.out.println("Saisie invalide, recommencez!");
				sc.nextLine();
			}
		}while (!bool);
		
		if(choice == 1) {
			new MemoryConsoleController();
		}
		if(choice == 2) {
			System.out.println("Memory JavaFX!");
			
			
		}
	}
	

}


