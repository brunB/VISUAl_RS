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
	
	@Override
	public String toString()
	{
		return version + " " + rubriqueSolde;
	}
}
