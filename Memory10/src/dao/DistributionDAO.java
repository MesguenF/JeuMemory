package dao;

/**
 * @author Mesquen Frédéric
 *
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.cartes.CardPack;
import model.cartes.Game;
import model.cartes.carte.Card;
import model.cartes.carte.Symbol;


public class DistributionDAO{
	private static final String TABLE  = "DISTRIBUTION";

	public static boolean createDistribution(int idGame,int idCard,int cardPosition, boolean cardVisibility) {
			boolean succes = true;
			try {
				String requete = "INSERT INTO "+ TABLE +" (idPartie,idCarte,positionCarte,visibleCarte) VALUES (?,?,?,?)";		
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1,idGame);
				pst.setInt(2, idCard);
				pst.setInt(3, cardPosition);
				pst.setBoolean(4, cardVisibility);
				pst.executeUpdate();
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
			return succes;
		}
	
	//TODO TEST
	public boolean updateDistribution(int idGame,int idCard,int cardPosition, boolean cardVisibility) {
			boolean succes = true;
			try {
				String requete = "UPDATE "+TABLE+" SET visibleCarte = ?,  WHERE idPartie = "+ idGame + "AND idCarte = " + idCard + " AND positionCarte = " + cardPosition;			
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setBoolean(1,cardVisibility);
				pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
		}
	//TODO TEST
	public boolean deleteDistribution(Game game) {
		boolean succes = true;
		try {
			String requete = "DELETE FROM "+TABLE+" WHERE idPartie = " + game.getGameNumber();	
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
		} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}
		return succes;
	}
	
	public static CardPack readDistribution(Game game) {
		CardPack paquet = new CardPack();
		try {
			String requeteRead1 = "SELECT idCarte, positionCarte, visibleCarte FROM DISTRIBUTION WHERE idPartie = " + game.getGameNumber();
			PreparedStatement pst1 = Connexion.getInstance().prepareStatement(requeteRead1, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs1 = pst1.executeQuery();
			if(rs1.next()) {
				do {
					Card card = new Card();
					card.setCardNumber(rs1.getInt("idCarte"));
					int cardPosition = (rs1.getInt("positionCarte"));
					card.setVisible(rs1.getBoolean("visibleCarte"));
					System.out.println("num carte : " + card.getCardNumber());
					System.out.println("position carte : " + cardPosition);
										
					String requeteRead2 = " SELECT symboleCarte FROM  CARTE  WHERE idCarte = " +  (rs1.getInt("idCarte"));
					PreparedStatement pst2 = Connexion.getInstance().prepareStatement(requeteRead2, Statement.RETURN_GENERATED_KEYS);
					ResultSet rs2 = pst2.executeQuery();
					if(rs2.next()) {
						//TODO VERIF SYMBOLE INT
						System.out.println("symbole INT:" + rs2.getInt("symboleCarte"));
						card.setSymbol(Symbol.getSymbol(rs2.getInt("symboleCarte")));
						System.out.println(card);
					}
					paquet.setCard(cardPosition, card);
				}while(rs1.next()!= false);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(paquet);
		return paquet;
	}
}		
