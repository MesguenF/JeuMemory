package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.plaf.synth.SynthStyle;
import javax.swing.plaf.synth.SynthStyleFactory;

import jeu.cartes.PaquetCartes;
import jeu.cartes.Partie;
import jeu.cartes.carte.Carte;
import jeu.cartes.carte.Symbole;

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
	
	/*public static boolean getDistribution(int idPartie,int idCarte,int positionCarte, boolean visibleCarte) {
		boolean succes = true;
		try {
			String requete = "INSERT INTO "+ TABLE +" (idPartie,idCarte,positionCarte,visibleCarte) VALUES (?,?,?,?)";		
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1,idPartie);
			pst.setInt(2, idCarte);
			pst.setInt(3, positionCarte);
			pst.setBoolean(4, visibleCarte);
			pst.executeUpdate();
			ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					partie.setNumPartie(rs.getInt(1));   Le 1 de getInt(1) indique la colonne de la table Partie
				}
			} catch (SQLException e) {
				succes=false;
				e.printStackTrace();
			}
			return succes;
	}*/
	
	/*M�thode pour lire une partie pr�sente dans BD*/
	public static PaquetCartes getDistribution(Partie partie) {
		PaquetCartes paquet = new PaquetCartes();
		try {
			
			String requeteRead = "SELECT idCarte FROM DISTRIBUTION WHERE idPartie = " + partie.getNumPartie();
			PreparedStatement pst = Connexion.getInstance().prepareStatement(requeteRead, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				
				System.out.println(rs.next());
				Carte carte = new Carte();
				carte.setNumCarte(rs.getInt("idCarte"));
				System.out.println(carte.numCarte);
				}
			
			
			
			} catch (SQLException e) {
			//succes=false;
			e.printStackTrace();
			
		}
			/*System.out.println(paquet);*/
			return paquet;
		}
	
	
}		
		
		
	

