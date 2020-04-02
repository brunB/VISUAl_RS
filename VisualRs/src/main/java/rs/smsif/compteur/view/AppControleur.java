package rs.smsif.compteur.view;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import rs.smsif.compteur.model.Comptage;
import rs.smsif.compteur.model.Graphe;
import rs.smsif.compteur.utils.OutilsComptage;

public class AppControleur {

	@FXML
	private ComboBox <String> selectionRS;

	@FXML
	private ComboBox <String> selectionVersion;
	
	private Graphe graphe;
	
	private ObservableList <Comptage> comptagesBDD;

	/**
	 * Constructeur.
	 */
	public AppControleur()
	{
		graphe = new Graphe();

		comptagesBDD = FXCollections.observableArrayList();
		
		comptagesBDD.add(new Comptage("07.14.01.a.r01","TAOPC","FORMAT",2,0,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TAOPC","FORMAT",2,0,0,0));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TAOPC","FORMAT",4,0,0,0));
		comptagesBDD.add(new Comptage("07.14.01.a.r01","TAOPC","TAOPCO",13,3,3,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TAOPC","TAOPCO",14,0,0,1));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TAOPC","TAOPCO",38,0,3,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","FORM","FORMAT",30,2,10,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","FORM","RECRUT",37,2,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TEST","RECRUT",37,2,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TEST","AZER",37,2,11,1));
	}

	@FXML
	private void initialize()
	{
		/* LISTE DES RS FIGEES OBTENUE A PARTIR D'UNE TABLE */
		ObservableList <String> rubriquesSolde = FXCollections.observableArrayList("TAOPCO");
		selectionRS.setItems(rubriquesSolde);

		/* LISTE DES VERSIONS FIGEES OBTENUE A PARTIR D'UNE TABLE */
		ObservableList <String> version = FXCollections.observableArrayList("07.14.01.a.r01","07.15.00.d.r01", "07.19.00.c.r01","07.19.00.d.r01", "07.20.01.a.r01");
		selectionVersion.setItems(version);

		// Chargement du graphique si le numéro de version est renseigné.
		selectionRS.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionVersion.getSelectionModel().isEmpty())
			{
				// Récupération de la rubrique de solde centrale au graphique.
				Comptage rsCentrale = recupererComptageCentral();

				construireGraphique(rsCentrale);
				
				graphe.afficher(selectionRS.getScene());
			}
		});

		// Chargement du graphique si la rubrique de solde est renseignée.
		selectionVersion.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionRS.getSelectionModel().isEmpty())
			{
				// Récupération de la rubrique de solde centrale au graphique.
				Comptage rsCentrale = recupererComptageCentral();

				construireGraphique(rsCentrale);
				
				graphe.afficher(selectionRS.getScene());
			}
		});
	}
	
	public void construireGraphique(Comptage rsCentrale)
	{		
		// La liste des rubriques de solde associée à la rubrique centrale.
		List <Comptage> comptagesSelonVersion = OutilsComptage.recupererRubriqueSolde(rsCentrale, comptagesBDD);

		graphe.construire(rsCentrale, comptagesSelonVersion);
		
		// Récupération des rubriques de soldes associées aux rubriques filles.
		for (Comptage comptage : comptagesSelonVersion)
		{
			if (comptage.getIndic() == 0)
			{
				List <Comptage> medros = OutilsComptage.recupererMedros(comptage, comptagesBDD);

				for (Comptage medro : medros)
				{
					construireGraphique(medro);
				}
			}
		}
	}
	
	/**
	 * Retourne la rubrique de solde centrale.
	 * Si la rubrique n'existe pas pour les versions demandées, les plus récentes dernières versions
	 * qui contiennent la rubrique sont sélectionnées.
	 * 
	 * @return la rubrique de solde centrale.
	 */
	public Comptage recupererComptageCentral()
	{
		Comptage rsCentrale = null;
		
		// Récupération des informations choisies par l'utilsiateur.
		String rubriqueSolde = selectionRS.getSelectionModel().getSelectedItem();
		int indiceVersion = selectionVersion.getSelectionModel().getSelectedIndex();
		
		// Récupération de la dernière plus récente version
		// dans laquelle la rubrique de solde existe.
		for (int i = indiceVersion; i >= 0; i--)
		{
			String version = selectionVersion.getItems().get(i);

			Optional <Comptage> comptage = comptagesBDD.stream()
			  										.filter(item -> item.getVersion().equals(version))
			  										.filter(item -> item.getRubriqueSolde().equals(rubriqueSolde))
			  										.findFirst();

			if (comptage.isPresent())
			{
				rsCentrale = comptage.get();

				break;
			}
		}
		
		return rsCentrale;
	}
}
