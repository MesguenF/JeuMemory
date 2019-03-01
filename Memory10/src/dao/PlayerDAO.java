package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.cartes.Player;

public class PlayerDAO extends DAO<Player>{
	private static final String TABLE  = "JOUEUR";
	private static final String PRIMARY_KEY = "idJoueur";
	private static PlayerDAO instance = null;
	
	public static 	PlayerDAO getInstance() {
		if(instance == null) {
			instance =new PlayerDAO();
			}
		return instance;
	}
	
	public boolean create(Player pl) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO "+ TABLE +" (nomJoueur,prenomJoueur,pseudoJoueur) VALUES (?,?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			System.out.println("le joueur = "+pl);
			pst.setString(1, pl.getPlayerLastName());
			pst.setString(2, pl.getPlayerFirstName());
			pst.setString(3, pl.getPlayerHandle());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					pl.setPlayerNumber(rs.getInt(1));   /*Le 1 de getInt(1) indique la colonne de la table Joueur*/
				}
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
	}

	public boolean delete(Player pl) {
		boolean succes = true;
	try {
		String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+PRIMARY_KEY+" = ?";
		PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteDelete, Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, pl.getPlayerNumber());
		pst.executeUpdate();
	} catch (SQLException e) {
		succes=false;
		e.printStackTrace();
	}
	return succes;
	}
	
	public boolean update(Player pl) {
			boolean succes = true;
			try {
				String requete = "UPDATE "+TABLE+" SET nomJoueur = ?, prenomjoueur = ?, pseudoJoueur = ?, WHERE "+PRIMARY_KEY+" = ?";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, pl.getPlayerLastName());
				pst.setString(2, pl.getPlayerFirstName());
				pst.setString(3, pl.getPlayerHandle());
				pst.setInt(4, pl.getPlayerNumber());
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}
	
	public Player read(int id) {
		Player playerBD = null;
		try {
			String requeteRead = "SELECT FROM "+TABLE+" WHERE "+PRIMARY_KEY+" = ?";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,id);	
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				playerBD = new Player(rs.getString("nomJoueur")+rs.getString("prenomJoueur")+rs.getString("pseudoJoueur")); 
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playerBD;
	}
}
