package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jeu.cartes.CardPack;
import jeu.cartes.Game;
import jeu.cartes.carte.Card;
import jeu.cartes.carte.Symbol;

public class DistributionDAO{
	private static final String TABLE  = "DISTRIBUTION";

	public static boolean createDistribution(int idPartie,int idCarte,int positionCarte, boolean visibleCarte) {
			boolean succes = true;
			try {
				String requete = "INSERT INTO "+ TABLE +" (idPartie,idCarte,positionCarte,visibleCarte) VALUES (?,?,?,?)";		
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1,idPartie);
				pst.setInt(2, idCarte);
				pst.setInt(3, positionCarte);
				pst.setBoolean(4, visibleCarte);
				pst.executeUpdate();
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
			return succes;
		}
	
	//TODO TEST
	public boolean updateDistribution(int idPartie,int idCarte,int positionCarte, boolean visibleCarte) {
			boolean succes = true;
			try {
				String requete = "UPDATE "+TABLE+" SET visibleCarte = ?,  WHERE idPartie = "+ idPartie + "AND idCarte = " + idCarte + " AND positionCarte = " + positionCarte;			
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setBoolean(1,visibleCarte);
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}
	//TODO TEST
	public boolean deleteDistribution(Game partie) {
		boolean succes = true;
		try {
			String requete = "DELETE FROM "+TABLE+" WHERE idPartie = " + partie.getGameNumber();	
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
		} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}
		return succes;
	}
	
	public static CardPack readDistribution(Game partie) {
		CardPack paquet = new CardPack();
		try {
			String requeteRead1 = "SELECT idCarte, positionCarte, visibleCarte FROM DISTRIBUTION WHERE idPartie = " + partie.getGameNumber();
			PreparedStatement pst1 = Connexion.getInstance().prepareStatement(requeteRead1, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs1 = pst1.executeQuery();
			if(rs1.next()) {
				do {
					Card carte = new Card();
					carte.setCardNumber(rs1.getInt("idCarte"));
					int positionCarte = (rs1.getInt("positionCarte"));
					carte.setVisible(rs1.getBoolean("visibleCarte"));
					System.out.println("num carte : " + carte.getCardNumber());
					System.out.println("position carte : " + positionCarte);
										
					String requeteRead2 = " SELECT symboleCarte FROM  CARTE  WHERE idCarte = " +  (rs1.getInt("idCarte"));
					PreparedStatement pst2 = Connexion.getInstance().prepareStatement(requeteRead2, Statement.RETURN_GENERATED_KEYS);
					ResultSet rs2 = pst2.executeQuery();
					if(rs2.next()) {
						//TODO VERIF SYMBOLE INT
						System.out.println("symbole INT:" + rs2.getInt("symboleCarte"));
						carte.setSymbol(Symbol.getSymbol(rs2.getInt("symboleCarte")));
						System.out.println(carte);
					}
					paquet.setCard(positionCarte, carte);
				}while(rs1.next()!= false);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(paquet);
		return paquet;
	}
}		
