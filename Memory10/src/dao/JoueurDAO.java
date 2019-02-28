package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jeu.cartes.Player;

public class JoueurDAO extends DAO<Player>{
	private static final String TABLE  = "JOUEUR";
	private static final String CLE_PRIMAIRE = "idJoueur";
	private static JoueurDAO instance = null;
	
	public static 	JoueurDAO getInstance() {
		if(instance == null) {
			instance =new JoueurDAO();
			}
		return instance;
	}
	
	/*M�thode pour cr�er un joueur*/
	public boolean create(Player joueur) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO "+ TABLE +" (nomJoueur,prenomJoueur,pseudoJoueur) VALUES (?,?,?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			System.out.println("le joueur = "+joueur);
			pst.setString(1, joueur.getPlayerLastName());
			pst.setString(2, joueur.getPlayerFirstName());
			pst.setString(3, joueur.getPlayerHandle());
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					joueur.setPlayerNumber(rs.getInt(1));   /*Le 1 de getInt(1) indique la colonne de la table Joueur*/
				}
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
	}

	/*M�thode pour effacer un joueur*/
	public boolean delete(Player joueur) {
		boolean succes = true;
	try {
		String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
		//String requete = "DELETE FROM "+ TABLE +" VALUES (?) numav== 14)";
		PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteDelete, Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, joueur.getPlayerNumber());
		pst.executeUpdate();
	} catch (SQLException e) {
		succes=false;
		e.printStackTrace();
	}
	return succes;
	}
	
	/*M�thode pour mettre � jour un joueur*/
	public boolean update(Player joueur) {
			boolean succes = true;
			try {
				String requete = "UPDATE "+TABLE+" SET nomJoueur = ?, prenomjoueur = ?, pseudoJoueur = ?, WHERE "+CLE_PRIMAIRE+" = ?";
				//String requete = "UPDATE "+TABLE+" SET nomav = ?, loc = ?, capacite = ? WHERE "+CLE_PRIMAIRE+" = 9";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, joueur.getPlayerLastName());
				pst.setString(2, joueur.getPlayerFirstName());
				pst.setString(3, joueur.getPlayerHandle());
				pst.setInt(4, joueur.getPlayerNumber());
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}
	
	/*M�thode pour lire un joueur pr�sent dans BD*/
	public Player read(int id) {
		Player joueurDAO = null;
		try {
			String requeteRead = "SELECT FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
			//String requete = "DELETE FROM "+ TABLE +" VALUES (?) numav== 08)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,id);	/*pst.setInt(4,id);???*/
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				joueurDAO = new Player(rs.getString("nomJoueur")+rs.getString("prenomJoueur")+rs.getString("pseudoJoueur")); /*+rs.getInt("indiceJoueur")+rs.getInt("nbPointsJoueur")*/ /*PAS BESOIN ???*/
				}
		} catch (SQLException e) {
			//succes=false;
			e.printStackTrace();
		}
		return joueurDAO;
	}

}
