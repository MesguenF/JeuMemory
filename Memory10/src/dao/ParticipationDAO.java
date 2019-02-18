package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
			/*ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					partie.setNumPartie(rs.getInt(1));   Le 1 de getInt(1) indique la colonne de la table Partie
				}*/
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
	}
}
