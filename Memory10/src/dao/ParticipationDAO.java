package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jeu.cartes.PaquetCartes;
import jeu.cartes.Partie;
import jeu.cartes.carte.Carte;
import jeu.cartes.carte.Symbole;

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
	
	/*M�thode pour lire une partie pr�sente dans BD*///TODO
	public static int [] getParticipation(Partie partie) {
		int [] tab = new int[4];
		try {
			String requeteRead1 = "SELECT idJoueur, main, scoreJoueur, positionTour FROM PARTICIPATION WHERE idPartie = " + partie.getNumPartie();
			PreparedStatement pst1 = Connexion.getInstance().prepareStatement(requeteRead1, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs1 = pst1.executeQuery();
			if(rs1.next()) {
				do {
					tab[0] = rs1.getInt("idJoueur");
					tab[1] = rs1.getInt("main");
					tab[2] = rs1.getInt("scoreJoueur");
					tab[3] = rs1.getInt("positionTour");
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
					
				}while(rs1.next()!= false);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tab;
	}
}
