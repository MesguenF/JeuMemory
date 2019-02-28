package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jeu.cartes.Game;

public class PartieDAO extends DAO<Game>{
			
		private static final String TABLE  = "PARTIE";
		private static final String CLE_PRIMAIRE = "idPartie";
		private static ArrayList<Game> listePartieBD;
		private static PartieDAO instance = null;
		
		public static 	PartieDAO getInstance() {
			if(instance == null) {
				instance =new PartieDAO();
				}
			return instance;
		}
		
		/*Méthode pour créer une partie*/
		public boolean create(Game partie) {
			boolean succes = true;
			try {
				String requete = "INSERT INTO "+ TABLE +" (nomPartie) VALUES (?)";		
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				System.out.println("la partie = " + partie);
				pst.setString(1, (partie).getGameName());
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
					if (rs.next()) {
						partie.setGameNumber(rs.getInt(1));   /*Le 1 de getInt(1) indique la colonne de la table Partie*/
					}
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
				return succes;
		}

		/*Méthode pour effacer une partie*/
		public boolean delete(Game partie) {
			boolean succes = true;
			try {
				String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteDelete, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, partie.getGameNumber());
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}
		
		/*M�thode pour mettre � jour une partie*/
		public boolean update(Game partie) {
				boolean succes = true;
				try {
					String requete = "UPDATE "+TABLE+" SET nomPartie = ?,  WHERE "+CLE_PRIMAIRE+" = ?";			
					PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
					pst.setString(1, partie.getGameName());
					pst.executeUpdate();
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
				return succes;
			}
		
		/*Méthode pour retourner une partie présente dans BD*/
		public Game read(int id) {
			Game partieDAO = null;
			try {
				String requeteRead = "SELECT nomPartie FROM PARTIE WHERE idPartie = " + id;
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
				/*pst.setInt(1,id);
				pst.executeUpdate();*/
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					partieDAO = new Game(id,rs.getString("nomPartie"));
					}
				} catch (SQLException e) {
				//succes=false;
				e.printStackTrace();
				}
				return partieDAO;
			}
		
		/*Méthode pour lire toutes les parties dans la BD*/
		public ArrayList<Game> readAll() {
			listePartieBD = new ArrayList<Game>();
			Game partieDAO = null;
			try {
				String requeteRead = "SELECT * FROM PARTIE";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
				/*pst.setInt(1,id);
				pst.executeUpdate();*/
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					do{
						partieDAO = new Game(rs.getInt("idPartie"),rs.getString("nomPartie"));
						listePartieBD.add(partieDAO);
					}while(rs.next() != false);
				}
				} catch (SQLException e) {
				//succes=false;
				e.printStackTrace();
				}
				System.out.println(listePartieBD);
				return listePartieBD;
			}
		}

	
	

