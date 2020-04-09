package rs.smsif.compteur.utils;

import java.util.List;
import java.util.stream.Collectors;

import rs.smsif.compteur.model.Comptage;

/**
 * Cette classe permet d'effectuer des actions sur les rubriques de solde.
 *
 * @author gmarco
 *
 */
public class OutilsComptage {

	/**
	 * Retourne les rubriques de solde associ�es � la rubrique centrale.
	 *
	 * @param comptage la rubrique de solde centrale.
	 * @param comptages la liste de toutes les rubriques de solde.
	 *
	 * @return les rubriques de solde associ�es � la rubrique centrale.
	 */
	public static List <Comptage> recupererRubriqueSolde(Comptage comptage, List <Comptage> comptages)
	{
		String version = comptage.getVersion();
		String medro = comptage.getMedro();
		String rubriqueSolde = comptage.getRubriqueSolde();

		// R�cup�ration des rubriques de soldes associ�es � celle choisie.
		// Celle choisie doit �tre exclue.
		List <Comptage> comptagesSelonVersion = comptages.stream()
	   		        									 .filter(item -> item.getVersion().equals(version))
	   		        									 .filter(item -> item.getMedro().equals(medro))
	   		        									 .filter(item -> !item.getRubriqueSolde().equals(rubriqueSolde))
	   		        									 .collect(Collectors.toList());

		return comptagesSelonVersion;
	}

	/**
	 * Retourne les comptages ayant la m�me rubrique de solde que la rubrique centrale.
	 *
	 * @param comptage la rubrique de solde centrale.
	 * @param comptages la liste de toutes les rubriques de solde.
	 *
	 * @return les comptages ayant la m�me rubrique de solde que la rubrique centrale.
	 */
	public static List <Comptage> recupererMedros(Comptage comptage, List <Comptage> comptages)
	{
		String version = comptage.getVersion();
		String rubriqueSolde = comptage.getRubriqueSolde();

		// R�cup�ration des comptages ayant la m�me rubrique de solde.
		List <Comptage> comptagesSelonMedro = comptages.stream()
					 								   .filter(item -> item.getVersion().substring(0, 10).equals(version.substring(0, 10)))
					 								   .filter(item -> item.getRubriqueSolde().equals(rubriqueSolde))
					 								   .filter(item -> item.getIndic() == 1)
					 								   .collect(Collectors.toList());

		comptagesSelonMedro.remove(comptage);

		return comptagesSelonMedro;
	}
}
