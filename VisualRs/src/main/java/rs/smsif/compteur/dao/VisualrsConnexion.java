/**
 *
 */
package rs.smsif.compteur.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * Classe SINGLETON permettant l'instanciation d'une unique connexion à la BDD
 * ORACLE
 *
 * @author neo
 *
 */
public class VisualrsConnexion {
	// Objet de récupération des données du fichier properties
	ResourceBundle bundle = ResourceBundle.getBundle("rs.smsif.compteur.properties.config");
	// récupération adresse IP
	String ip = bundle.getString("sgbdr.ip");
	// Récupération du port d'écoute de la base de données
	String port = bundle.getString("sgbdr.port");
	// Récupération du SID de la base de données
	String sid = bundle.getString("sgbdr.sid");
	// Récupération du USER
	String userBdd = bundle.getString("sgbdr.user");
	// Récupération du mot de passe
	String pwd = bundle.getString("sgbdr.password");

	// URL de connexion
	private String url = "jdbc:oracle:thin:"+ip+":"+port+":"+sid;
	// Nom du user
	private String user = userBdd;
	// Mot de passe de l'utilisateur
	private String passwd = pwd;
	// Objet Connection
	private static Connection connect;

	/**
	 * Constructeur privé
	 */
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
