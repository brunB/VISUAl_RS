/**
 *
 */
package rs.smsif.compteur.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.smsif.compteur.model.Comptage;

/**
 * @author neo
 *
 */
public class ComptageDAO extends DAO<Comptage> {

	public ComptageDAO(Connection conn) {
		super(conn);
	}

	public int create(Comptage obj) {
		int result=0;
		try {

			// Création de la requête sur la PK de DX.COMPTAGE_RS
			String query = "INSERT INTO DX.COMPTEURS_RS ";
			query += " (VERSION,MEDRO,RS,CPT_TOT,CPT_BAR,CPT_EVO,INDIC) values";
			query += " (?,?,?,?,?,?,?)";

			/*
			 * Création de l'objet preparedStatement TYPE_SCROLL_INSENSITIVE
			 * scroll de la table avant et arriere par contre pas de
			 * modification si BDD mise à jour, CONCUR_READ_ONLY : données
			 * consultables uniquement en lecture
			 */
			PreparedStatement prepare = connect.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			// Attibution des valeurs de la requête
			prepare.setString(1, obj.getVersion());
			prepare.setString(2, obj.getMedro());
			prepare.setString(3, obj.getRubriqueSolde());
			prepare.setInt(4, obj.getCompteurTot());
			prepare.setInt(5, obj.getCompteurBar());
			prepare.setInt(6, obj.getCompteurEvo());
			prepare.setInt(7, obj.getIndic());

			// Check requête prepare
			System.out.println(prepare.toString());

			// Execution de la requete SQL prepare
			result = prepare.executeUpdate();


			// On récupère l'objet et ses valeurs
			if (result > 0){
				System.out.println("Objet bien ajouté en base");
			}else if (result==0){
			System.out.println("Objet non injecté en base");
			}
			// Fermeture de la connexion
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(Comptage obj) {
		int result=0;
		try {

			// Création de la requête sur la PK de DX.COMPTAGE_RS
			String query = "DELETE FROM DX.COMPTEURS_RS ";
			query += " WHERE version = ?";
			query += " and medro = ?";
			query += " and RS = ?";

			/*
			 * Création de l'objet preparedStatement TYPE_SCROLL_INSENSITIVE
			 * scroll de la table avant et arriere par contre pas de
			 * modification si BDD mise à jour, CONCUR_READ_ONLY : données
			 * consultables uniquement en lecture
			 */
			PreparedStatement prepare = connect.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			// Attibution des valeurs de la requête
			prepare.setString(1, obj.getVersion());
			prepare.setString(2, obj.getMedro());
			prepare.setString(3, obj.getRubriqueSolde());

			// Check requête prepare
			System.out.println(prepare.toString());

			// Execution de la requete SQL prepare
			result = prepare.executeUpdate();


			// On récupère l'objet et ses valeurs
			if (result > 0){
				System.out.println("La ligne a bien été supprimée.");
			}else if (result==0){
			System.out.println("Aucune ligne n'a pas été supprimée.");
			}
			// Fermeture de la connexion
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result;
	}

	public boolean update(Comptage obj) {
		return false;
	}

	/**
	 * méthode pour trouver une ligne unique de comptage
	 *
	 */
	public Comptage find(String version, String medro, String rubriqueSolde) {
		Comptage Comptage = new Comptage();
		int nombreLignes = 0;
		try {

			// Création de la requête sur la PK de DX.COMPTAGE_RS
			String query = "SELECT * FROM DX.COMPTEURS_RS ";
			query += " WHERE version = ?";
			query += " and medro = ?";
			query += " and RS = ?";

			/*
			 * Création de l'objet preparedStatement TYPE_SCROLL_INSENSITIVE
			 * scroll de la table avant et arriere par contre pas de
			 * modification si BDD mise à jour, CONCUR_READ_ONLY : données
			 * consultables uniquement en lecture
			 */
			PreparedStatement prepare = connect.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// Attibution des valeurs de la requête
			prepare.setString(1, version);
			prepare.setString(2, medro);
			prepare.setString(3, rubriqueSolde);

			// Check requête prepare
			System.out.println(prepare.toString());

			// Execution de la requete SQL prepare
			ResultSet result = prepare.executeQuery();

			// On se positionne sur la dernière ligne ramenée
			result.last();

			// récupération du n° de la ligne
			nombreLignes = result.getRow();

			// On récupère l'objet et ses valeurs
			if (result.first() && nombreLignes == 1)
				Comptage = new Comptage(version, medro, rubriqueSolde, result.getInt("CPT_TOT"),
						result.getInt("CPT_BAR"), result.getInt("CPT_EVO"), result.getInt("INDIC"));
			// Fermeture de la connexion
			result.close();
		} catch (SQLException e) {
			if (nombreLignes != 1) {
				System.out.println("Le nombre de ligne ramenée n'est pas égale à 1 mais à " + nombreLignes);
			}
			e.printStackTrace();
		}
		return Comptage;
	}

	@Override
	public List<Comptage> selectAll() {
		List<Comptage> listComptage = new ArrayList<Comptage>();

		try {

			// Création de la requête sur la PK de DX.COMPTAGE_RS
			String query = "SELECT * FROM DX.COMPTEURS_RS";

			/*
			 * Création de l'objet preparedStatement TYPE_SCROLL_INSENSITIVE
			 * scroll de la table avant et arriere par contre pas de
			 * modification si BDD mise à jour, CONCUR_READ_ONLY : données
			 * consultables uniquement en lecture
			 */
			PreparedStatement prepare = connect.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// Check requête prepare
			System.out.println(prepare.toString());

			// Execution de la requete SQL prepare
			ResultSet result = prepare.executeQuery();
			result.first();
			// Création d'un objet VersionLvs puis ajout dans la liste
			while (result.next()) {
				Comptage comptage = new Comptage();

				comptage.setVersion(result.getString("VERSION"));
				comptage.setMedro(result.getString("MEDRO"));
				comptage.setRubriqueSolde(result.getString("RS"));
				comptage.setCompteurTot(result.getInt("CPT_TOT"));
				comptage.setCompteurBar(result.getInt("CPT_BAR"));
				comptage.setCompteurEvo(result.getInt("CPT_EVO"));
				comptage.setIndic(result.getInt("INDIC"));
				
				listComptage.add(comptage);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listComptage;
	}

}
