package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import businessObject.ControleurMemory;
import jeu.cartes.Partie;

public class PartieDAO extends DAO<ControleurMemory>{
			
		private static final String TABLE  = "PARTIE";
		private static final String CLE_PRIMAIRE = "idPartie";
		private static PartieDAO instance = null;
		
		public static 	PartieDAO getInstance() {
			if(instance == null) {
				instance =new PartieDAO();
				}
			return instance;
		}
		
		/*M�thode pour cr�er une partie*/
		public boolean create(Partie partie) {
			boolean succes = true;
			try {
				Calendar cal = null; //Calendar?????
				String requete = "INSERT INTO "+ TABLE +" (nomPartie) VALUES (?)";		/*,datePartie*/
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, partie.getNomPartie());
				/*pst.setDate(2, partie.getDatePartie(), cal);*/ //TODO GREGORIAN CALENDAR
				pst.executeUpdate();
				ResultSet rs = pst.getGeneratedKeys();
					if (rs.next()) {
						partie.setNumPartie(rs.getInt(1));   /*Le 1 de getInt(1) indique la colonne de la table Joueur*/
					}
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
				return succes;
		}

		/*M�thode pour effacer une partie*/
		public boolean delete(Partie partie) {
			boolean succes = true;
		try {
			String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
			//String requete = "DELETE FROM "+ TABLE +" VALUES (?) numav== 14)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteDelete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, partie.getNumPartie());
			pst.executeUpdate();
		} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}
		return succes;
		}
		
		/*M�thode pour mettre � jour une partie*/
		public boolean update(Partie partie) {
				boolean succes = true;
				Calendar cal = null; //Calendar?????
				try {
					String requete = "UPDATE "+TABLE+" SET nomPartie = ?,  WHERE "+CLE_PRIMAIRE+" = ?";			/*datePartie = ?,*/
					//String requete = "UPDATE "+TABLE+" SET nomav = ?, loc = ?, capacite = ? WHERE "+CLE_PRIMAIRE+" = 9";
					PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
					pst.setString(1, partie.getNomPartie());
					/*pst.setDate(2, partie.getDatePartie(), cal);*/	//TODO GREGORIAN CALENDAR
					pst.executeUpdate();
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
				return succes;
			}
		
		/*M�thode pour lire une partie pr�sente dans BD*/
		public Partie read(int id) {
			Partie partieDAO = null;
			try {
				String requeteRead = "SELECT FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
				//String requete = "DELETE FROM "+ TABLE +" VALUES (?) numav== 08)";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1,id);	/*pst.setInt(4,id);???*/
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					partieDAO = new Partie(rs.getString("nomPartie"));   /* rs.getString("nomPartie")+rs.getDate("datePartie"); */
					}
			} catch (SQLException e) {
				//succes=false;
				e.printStackTrace();
			}
			return partieDAO;
		}
	}

	
	

