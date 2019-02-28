package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				} catch (SQLException e) {
					succes=false;
					e.printStackTrace();
				}
			return succes;
		}
	
	/*M�thode pour lire une partie pr�sente dans BD*/
	public static PaquetCartes getDistribution(Partie partie) {
		PaquetCartes paquet = new PaquetCartes();
		try {
			String requeteRead1 = "SELECT idCarte, positionCarte, visibleCarte FROM DISTRIBUTION WHERE idPartie = " + partie.getNumPartie();
			PreparedStatement pst1 = Connexion.getInstance().prepareStatement(requeteRead1, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs1 = pst1.executeQuery();
			if(rs1.next()) {
				do {
					Carte carte = new Carte();
					carte.setNumCarte(rs1.getInt("idCarte"));
					int positionCarte = (rs1.getInt("positionCarte"));
					carte.setVisible(rs1.getBoolean("visibleCarte"));
					System.out.println("num carte : " + carte.getNumCarte());
					System.out.println("position carte : " + positionCarte);
										
					String requeteRead2 = " SELECT symboleCarte FROM  CARTE  WHERE idCarte = " +  (rs1.getInt("idCarte"));
					PreparedStatement pst2 = Connexion.getInstance().prepareStatement(requeteRead2, Statement.RETURN_GENERATED_KEYS);
					ResultSet rs2 = pst2.executeQuery();
					if(rs2.next()) {
						//TODO VERIF SYMBOLE INT
						System.out.println("symbole INT:" + rs2.getInt("symboleCarte"));
						carte.setSymbole(Symbole.getSymbole(rs2.getInt("symboleCarte")));
						System.out.println(carte);
					}
					paquet.set(positionCarte, carte);
				}while(rs1.next()!= false);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(paquet);
		return paquet;
	}
}		
		
		
	

