package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jeu.cartes.Partie;

public class ParticipationDAO {
	private static final String TABLE  = "PARTICIPATION";
		
	public static boolean createParticipation(int idJoueur,int idPartie,int main,int scoreJoueur, int positionTour) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO "+ TABLE +" (idJoueur,idPartie,main,scoreJoueur,positionTour) VALUES (?,?,?,?,?)";		
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, idJoueur);
			pst.setInt(2, idPartie);
			pst.setInt(3, main);
			pst.setInt(4, scoreJoueur);
			pst.setInt(5, positionTour);
			pst.executeUpdate();
			//TODO executeQuery ??
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
	}
	
	public boolean updateParticipation(int idJoueur,int idPartie, int main, int scoreJoueur, int positionTour) {
		boolean succes = true;
		try {
			String requete = "UPDATE "+TABLE+" SET main = ? SET scoreJoueur = ?,  WHERE idJoueur = "+ idJoueur + "AND idPartie = " + idPartie;			
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,main);
			pst.setInt(2,scoreJoueur);
			pst.executeUpdate();
			//TODO executeQuery ??
		} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}
		return succes;
	}
	
	public boolean deleteParticipation(Partie partie) {
		boolean succes = true;
		try {
			String requete = "DELETE "+TABLE+" WHERE idPartie = "+ partie.getNumPartie();			
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			//TODO executeQuery ??
		} catch (SQLException e) {
			succes=false;
			e.printStackTrace();
		}
		return succes;
	}
	
	public static int [] readParticipation(Partie partie) {
		int [] tab = new int[4];
		try {
			String requeteRead = "SELECT idJoueur, main, scoreJoueur, positionTour FROM PARTICIPATION WHERE idPartie = " + partie.getNumPartie();
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
