package dao;

/**
 * @author Mesquen Frédéric
 *
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.cartes.carte.Card;

public class CardDAO extends DAO<Card>{
	private static final String TABLE  = "CARTE";
	private static final String PRIMARY_KEY = "idCarte";
	private static CardDAO instance = null;
	//TODO A SUPPRIMER
	private static ArrayList<Integer> listeIdCartes = new ArrayList<Integer>();	//Pour stocker indices des cartes pour la distribution BD
	
	public static 	CardDAO getInstance() {
		if(instance == null) {
			instance =new CardDAO();
			}
		return instance;
	}
	
	public boolean create(Card c) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO "+ TABLE +" (symboleCarte) VALUES (?)";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, c.getOrdinal(c.getSymbol()));
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					c.setCardNumber(rs.getInt(1)); 
					//TODO A SUPPRIMER
					listeIdCartes.add(c.getCardNumber());   /*Le 1 de getInt(1) indique la colonne de la table Carte*/
					System.out.println(listeIdCartes);
				}
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
		return succes;
	}
	
	public boolean delete(Card carte) {
		boolean succes = true;
			try {
				String requeteDelete = "DELETE FROM "+TABLE+" WHERE "+PRIMARY_KEY+" = ?";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteDelete, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, carte.getCardNumber());
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
		return succes;
	}
	
	public boolean update(Card carte) {
			boolean succes = true;
			try {
				String requete = "UPDATE "+TABLE+" SET symboleCarte = ?, WHERE "+PRIMARY_KEY+" = ?";
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, carte.getSymbol().toString());
				pst.setInt(4, carte.getCardNumber());
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}

	public Card read(int id) {
		Card cardBD = null;
		try {
			String requeteRead = "SELECT FROM "+TABLE+" WHERE "+PRIMARY_KEY+" = ?";
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,id);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				cardBD = new Card();		
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cardBD;
	}
}
