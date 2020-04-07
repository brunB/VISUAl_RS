/**
 *
 */
package rs.smsif.compteur.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * Classe SINGLETON permettant l'instanciation d'une unique connexion à la BDD
 * ORACLE
 *
 * @author neo
 *
 */
public class VisualrsConnexion {
	// URL de connexion
	private String url = "jdbc:oracle:thin:@localhost:1522:VISUALRS";
	// Nom du user
	private String user = "system";
	// Mot de passe de l'utilisateur
	private String passwd = "VisualRS250";
	// Objet Connection
	private static Connection connect;

	// Constructeur privé
	private VisualrsConnexion() {
		try {
			connect = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Méthode qui va nous retourner notre instance et la créer si elle n'existe
	// pas
	public static Connection getInstance() {
		if (connect == null) {
			new VisualrsConnexion();
		}
		return connect;
	}


}
