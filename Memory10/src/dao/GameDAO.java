package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jeu.cartes.Game;

public class GameDAO extends DAO<Game>{
			
		private static final String TABLE  = "PARTIE";
		private static final String PRIMARY_KEY = "idPartie";
		private static ArrayList<Game> listOfSavedGames;
		private static GameDAO instance = null;
		
		public static 	GameDAO getInstance() {
			if(instance == null) {
				instance =new GameDAO();
				}
			return instance;
		}
		
		public boolean create(Game game) {
			boolean succes = true;
			try {
				String requete = "INSERT INTO "+ TABLE +" (nomPartie) VALUES (?)";		
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				System.out.println("la partie = " + game);
				pst.setString(1, (game).getGameName());
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
					if (rs.next()) {
						game.setGameNumber(rs.getInt(1));   /*Le 1 de getInt(1) indique la colonne de la table Partie*/
					}
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
				return succes;
		}

		public boolean delete(Game game) {
			boolean succes = true;
			try {
				String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+PRIMARY_KEY+" = ?";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteDelete, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, game.getGameNumber());
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}
		
		public boolean update(Game game) {
				boolean succes = true;
				try {
					String requete = "UPDATE "+TABLE+" SET nomPartie = ?,  WHERE "+PRIMARY_KEY+" = ?";			
					PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
					pst.setString(1, game.getGameName());
					pst.executeUpdate();
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
			return succes;
		}
		
		public Game read(int id) {
			Game gameBD = null;
			try {
				String requeteRead = "SELECT nomPartie FROM PARTIE WHERE idPartie = " + id;
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					gameBD = new Game(id,rs.getString("nomPartie"));
					}
				} catch (SQLException e) {
				e.printStackTrace();
				}
			return gameBD;
		}
		
		public ArrayList<Game> readAll() {
			listOfSavedGames = new ArrayList<Game>();
			Game gamesBD = null;
			try {
				String requeteRead = "SELECT * FROM PARTIE";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					do{
						gamesBD = new Game(rs.getInt("idPartie"),rs.getString("nomPartie"));
						listOfSavedGames.add(gamesBD);
					}while(rs.next() != false);
				}
				} catch (SQLException e) {
				e.printStackTrace();
				}
				System.out.println(listOfSavedGames);
			return listOfSavedGames;
			}
		}
