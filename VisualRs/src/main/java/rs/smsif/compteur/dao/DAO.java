/**
 *
 */
package rs.smsif.compteur.dao;

import java.sql.Connection;
import java.util.List;

/**
 * @author neo
 *
 */
public abstract class DAO<T> {
	  protected Connection connect = null;

	  public DAO(Connection conn){
	    this.connect = conn;
	  }

	  /**
	  * Méthode de création
	  * @param obj
	  * @return boolean
	  */
	  public abstract int create(T obj);

	  /**
	  * Méthode pour effacer
	  * @param obj
	  * @return boolean
	  */
	  public abstract int delete(T obj);

	  /**
	  * Méthode de mise à jour
	  * @param obj
	  * @return boolean
	  */
	  public abstract boolean update(T obj);

	  /**
	  * Méthode de recherche des informations
	  * @param id
	  * @return T
	  */
	  public abstract T find(String a1, String a2, String a3);

	  /**
		  * Méthode de sélection de toutes les occurrences
		  * @param id
		  * @return T
		  */
	  public abstract List<T> selectAll();


}
