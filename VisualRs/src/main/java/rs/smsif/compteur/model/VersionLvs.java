/**
 *
 */
package rs.smsif.compteur.model;

/**
 *
 * Classe qui recense l'ensemble des versions LOUVOIS
 *
 * @author neo
 *
 */
public class VersionLvs {

	private int id;
	private String version;

	/**
	 * Constructeur.
	 *
	 * @param ID identifiant d'unicité de la version louvois
	 * @param version le numéro de version Louvois.
	 */
	public VersionLvs(int id, String version) {

		this.id = id;
		this.version = version;
	}

	public VersionLvs(){}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionLvs other = (VersionLvs) obj;
		if (id != other.id)
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	};

	@Override
	public String toString()
	{
		return "La version LVS est  "+version + " dont l'ID est "+id;
	}


}
