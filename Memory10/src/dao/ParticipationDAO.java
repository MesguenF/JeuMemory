package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.cartes.Game;

public class ParticipationDAO {
	private static final String TABLE  = "PARTICIPATION";
		
	public static boolean createParticipation(int idPlayer,int idGame,int hand,int playerScore, int playerPosition) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO "+ TABLE +" (idJoueur,idPartie,main,scoreJoueur,positionTour) VALUES (?,?,?,?,?)";		
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idPlayer);
			pst.setInt(2, idGame);
			pst.setInt(3, hand);
			pst.setInt(4, playerScore);
			pst.setInt(5, playerPosition);
			pst.executeUpdate();
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
		
			return succes;
	}
	//TODO TEST
	public boolean updateParticipation(int idPlayer,int idGame, int hand, int playerScore, int playerPosition) {
		boolean succes = true;
		try {
			String requete = "UPDATE "+TABLE+" SET main = ? SET scoreJoueur = ?,  WHERE idJoueur = "+ idPlayer + "AND idPartie = " + idGame;			
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,hand);
			pst.setInt(2,playerScore);
			pst.executeUpdate();
			} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}
		return succes;
	}
	//TODO TEST
	public boolean deleteParticipation(Game game) {
		boolean succes = true;
		try {
			String requete = "DELETE "+TABLE+" WHERE idPartie = "+ game.getGameNumber();			
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}
		return succes;
	}
	//TODO TEST
	public static int [] readParticipation(Game game) {
		int [] tab = new int[4];
		try {
			String requeteRead = "SELECT idJoueur, main, scoreJoueur, positionTour FROM PARTICIPATION WHERE idPartie = " + game.getGameNumber();
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				do {
					tab[0] = rs.getInt("idJoueur");
					tab[1] = rs.getInt("main");
					tab[2] = rs.getInt("scoreJoueur");
					tab[3] = rs.getInt("positionTour");
					//A SUPPRIMER
					System.out.println(tab[0]);
					System.out.println(tab[1]);
					System.out.println(tab[2]);
					System.out.println(tab[3]);
				}while(rs.next()!= false);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return tab;
	}
}
