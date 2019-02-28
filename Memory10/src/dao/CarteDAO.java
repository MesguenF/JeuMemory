package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import jeu.cartes.Joueur;
import jeu.cartes.PaquetCartes;
import jeu.cartes.carte.Carte;

public class CarteDAO extends DAO<Carte>{
	private static final String TABLE  = "CARTE";
	private static final String CLE_PRIMAIRE = "idCarte";
	private static CarteDAO instance = null;
	//TODO A SUPPRIMER
	private static ArrayList<Integer> listeIdCartes = new ArrayList<Integer>();	/*Pour stocker indices des crates pour la distribution BD*/
	
	public static 	CarteDAO getInstance() {
		if(instance == null) {
			instance =new CarteDAO();
			}
		return instance;
	}
	
	/*M�thode pour cr�er une carte*/
	public boolean create(Carte carte) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO "+ TABLE +" (symboleCarte) VALUES (?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, carte.getOrdinal(carte.getSymbole()));
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					carte.setNumCarte(rs.getInt(1)); 
					//TODO A SUPPRIMER
					listeIdCartes.add(carte.getNumCarte());   /*Le 1 de getInt(1) indique la colonne de la table Carte*/
					System.out.println(listeIdCartes);
				}
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
	}
	
	/*M�thode pour effacer une carte*/
	public boolean delete(Carte carte) {
		boolean succes = true;
	try {
		String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
		//String requete = "DELETE FROM "+ TABLE +" VALUES (?) numav== 14)";
		PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteDelete, Statement.RETURN_GENERATED_KEYS);
		pst.setInt(1, carte.getNumCarte());
		pst.executeUpdate();
	} catch (SQLException e) {
		succes=false;
		e.printStackTrace();
	}
	return succes;
	}
	
	/*M�thode pour mettre � jour une carte*/
	public boolean update(Carte carte) {
			boolean succes = true;
			try {
				String requete = "UPDATE "+TABLE+" SET symboleCarte = ?, WHERE "+CLE_PRIMAIRE+" = ?";
				//String requete = "UPDATE "+TABLE+" SET nomav = ?, loc = ?, capacite = ? WHERE "+CLE_PRIMAIRE+" = 9";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, carte.getSymbole().toString());
				pst.setInt(4, carte.getNumCarte());
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}

	/*M�thode pour lire une carte pr�sente dans BD*/
	public Carte read(int id) {
		Carte carteDAO = null;
		try {
			String requeteRead = "SELECT FROM "+TABLE+" WHERE "+CLE_PRIMAIRE+" = ?";
			//String requete = "DELETE FROM "+ TABLE +" VALUES (?) numav== 08)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,id);	/*pst.setInt(4,id);???*/
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				carteDAO = new Carte();		/*rs.getString("symboleCarte")+rs.getString("visibleBool"));*/
				}
		} catch (SQLException e) {
			//succes=false;
			e.printStackTrace();
		}
		return carteDAO;
	}
}
