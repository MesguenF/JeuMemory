package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jeu.cartes.Partie;

public class DistributionDAO{
	private static final String TABLE  = "DISTRIBUTION";

	public boolean createDistribution(int idPartie) {
			boolean succes = true;
			try {
				String requete = "INSERT INTO "+ TABLE +" (idCarte,positionCarte,visibleCarte) VALUES (?,?,?)";		
				PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, idPartie);
				pst.setInt(2, idCarte);
				pst.setInt(3, positionCarte);
				pst.setByte(0, x);(4, visibleCarte);
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
		
		
	

