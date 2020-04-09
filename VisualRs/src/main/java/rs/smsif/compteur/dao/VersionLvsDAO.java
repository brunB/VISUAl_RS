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

import rs.smsif.compteur.model.VersionLvs;

/**
 * @author neo
 *
 */
public class VersionLvsDAO extends DAO<VersionLvs> {

	public VersionLvsDAO(Connection conn) {
		super(conn);
	}

	@Override
	public int create(VersionLvs obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(VersionLvs obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(VersionLvs obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VersionLvs find(String a1, String a2, String a3) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Méthode de sélection de l'ensemble des objets VersionLvs issus de la
	 * table DX.HISTO_VERSION_LVS
	 *
	 * @return List<VersionLvs> retourne une liste d'objet de type VersionLvs
	 */
	@Override
	public List<VersionLvs> selectAll() {
		List<VersionLvs> listVLVS = new ArrayList<VersionLvs>();

		try {

			// Création de la requête sur la PK de DX.COMPTAGE_RS
			String query = "SELECT * FROM DX.HISTO_VERSION_LVS";

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

			// Création d'un objet VersionLvs puis ajout dans la liste
			if (result.first()) {
				VersionLvs versionLvsInit = new VersionLvs();
				versionLvsInit.setId(result.getInt("ID"));
				versionLvsInit.setVersion(result.getString("VERSION_LVS"));
				listVLVS.add(versionLvsInit);

				while (result.next()) {
					VersionLvs versionLvs = new VersionLvs();
					versionLvs.setId(result.getInt("ID"));
					versionLvs.setVersion(result.getString("VERSION_LVS"));
					// System.out.println(versionLvs.toString());
					listVLVS.add(versionLvs);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listVLVS;
	}

	/**
	 * Méthode permettant d'extraire la liste des versions uniquement de l'objet
	 * VersionLvs
	 *
	 * @return List<String> liste de String
	 */
	public List<String> selectAllString() {
		DAO<VersionLvs> versionLvsDao = new VersionLvsDAO(VisualrsConnexion.getInstance());
		List<VersionLvs> listVersionLvs = versionLvsDao.selectAll();
		List<String> listVersionLvsString = new ArrayList<String>();
		for (VersionLvs vLvs : listVersionLvs) {
			listVersionLvsString.add(vLvs.getVersion());
		}

		return listVersionLvsString;
	}

}
