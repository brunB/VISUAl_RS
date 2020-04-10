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
 * Classe SINGLETON permettant l'instanciation d'une unique connexion � la BDD
 * ORACLE
 *
 * @author neo
 *
 */
public class VisualrsConnexion {
	// Objet de r�cup�ration des donn�es du fichier properties
	ResourceBundle bundle = ResourceBundle.getBundle("rs.smsif.compteur.properties.config");
	// r�cup�ration adresse IP
	String ip = bundle.getString("sgbdr.ip");
	// R�cup�ration du port d'�coute de la base de donn�es
	String port = bundle.getString("sgbdr.port");
	// R�cup�ration du SID de la base de donn�es
	String sid = bundle.getString("sgbdr.sid");
	// R�cup�ration du USER
	String userBdd = bundle.getString("sgbdr.user");
	// R�cup�ration du mot de passe
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
	 * Constructeur priv�
	 */
	private VisualrsConnexion() {
		try {
			connect = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// M�thode qui va nous retourner notre instance et la cr�er si elle n'existe
	// pas
	public static Connection getInstance() {
		if (connect == null) {
			new VisualrsConnexion();
		}
		return connect;
	}

}
