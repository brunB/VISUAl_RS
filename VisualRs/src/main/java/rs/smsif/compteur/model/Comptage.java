package rs.smsif.compteur.model;

/**
 * Cette classe recense l'apparition des rubriques de solde dans les DSE.
 *
 * @author gmarco
 *
 */
public class Comptage {

	private String version;
	private String medro;
	private String rubriqueSolde;
	private int compteurTot;
	private int compteurBar;
	private int compteurEvo;
	private int indic;


	/**
	 * Constructeur.
	 *
	 * @param version le numéro de version Louvois.
	 * @param medro le nom de la MEDRO.
	 * @param rubriqueSolde la rubrique de solde.
	 * @param compteurTot
	 * @param compteurBar
	 * @param compteurEvo le nombre d'évolution.
	 * @param indic un indicateur permettant de déterminer quelle RS est développée dans une MEDRO donnée.
	 */
	public Comptage(String version, String medro, String rubriqueSolde, int compteurTot, int compteurBar, int compteurEvo, int indic) {

		this.version = version;
		this.medro = medro;
		this.rubriqueSolde = rubriqueSolde;
		this.compteurTot = compteurTot;
		this.compteurBar = compteurBar;
		this.compteurEvo = compteurEvo;
		this.indic = indic;
	}

	public Comptage(){};

	public String getVersion() {
		return version;
	}

	public String getMedro() {
		return medro;
	}

	public String getRubriqueSolde() {
		return rubriqueSolde;
	}

	public int getCompteurTot() {
		return compteurTot;
	}

	public int getCompteurBar() {
		return compteurBar;
	}

	public int getCompteurEvo() {
		return compteurEvo;
	}

	public int getIndic() {
		return indic;
	}
<<<<<<< HEAD

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param medro the medro to set
	 */
	public void setMedro(String medro) {
		this.medro = medro;
	}

	/**
	 * @param rubriqueSolde the rubriqueSolde to set
	 */
	public void setRubriqueSolde(String rubriqueSolde) {
		this.rubriqueSolde = rubriqueSolde;
	}

	/**
	 * @param compteurTot the compteurTot to set
	 */
	public void setCompteurTot(int compteurTot) {
		this.compteurTot = compteurTot;
	}

	/**
	 * @param compteurBar the compteurBar to set
	 */
	public void setCompteurBar(int compteurBar) {
		this.compteurBar = compteurBar;
	}

	/**
	 * @param compteurEvo the compteurEvo to set
	 */
	public void setCompteurEvo(int compteurEvo) {
		this.compteurEvo = compteurEvo;
	}

	/**
	 * @param indic the indic to set
	 */
	public void setIndic(int indic) {
		this.indic = indic;
	}



	@Override
	public String toString()
	{
		return "La version est "+version + " pour la RS " + rubriqueSolde + " de la medro " + medro;

	public String getId()
	{
		return version + rubriqueSolde;
	}


	}
}
