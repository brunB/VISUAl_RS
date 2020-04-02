package rs.smsif.compteur.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import rs.smsif.compteur.model.Comptage;
import rs.smsif.compteur.model.Graphe;

public class AppControleur {

	@FXML
	private ComboBox <String> selectionRS;

	@FXML
	private ComboBox <String> selectionVersion;
		
	private Comptage comptageRS;
	
	private ObservableList <Comptage> comptages;

	/**
	 * Constructeur.
	 */
	public AppControleur()
	{
		comptages = FXCollections.observableArrayList();
		
		comptages.add(new Comptage("07.14.01.a.r01","TAOPC","FORMAT",2,0,0,0));
		comptages.add(new Comptage("07.20.01.a.r01","TAOPC","FORMAT",2,0,0,0));
		comptages.add(new Comptage("07.19.00.c.r01","TAOPC","FORMAT",4,0,0,0));
		comptages.add(new Comptage("07.14.01.a.r01","TAOPC","TAOPCO",13,3,3,1));
		comptages.add(new Comptage("07.20.01.a.r01","TAOPC","TAOPCO",14,0,0,1));
		comptages.add(new Comptage("07.19.00.c.r01","TAOPC","TAOPCO",38,0,3,1));
		comptages.add(new Comptage("07.20.01.a.r02","FORM","FORMAT",30,2,10,1));
		comptages.add(new Comptage("07.20.01.a.r02","FORM","RECRUT",37,2,11,1));
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
				construireGraphique();
			}
		});

		// Chargement du graphique si la rubrique de solde est renseignée.
		selectionVersion.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionRS.getSelectionModel().isEmpty())
			{
				construireGraphique();
			}
		});
	}
	
	public void construireGraphique()
	{
		// La liste des rubriques de solde.
		List <Comptage> comptages = recupererRS();
				
		Graphe graphe = new Graphe(comptages);
		
		graphe.construire();
		graphe.afficher(selectionRS.getScene());
	}
	
	/**
	 * Retourne les rubriques de solde associées à celle demandée pour la version souhaitée.
	 * Dans le cas où la rubrique de solde n'existe pas pour la version,
	 * la dernière plus récente version sera prise en compte.
	 * 
	 * @return les rubriques de solde associées à celle demandée pour la version souhaitée.
	 */
	public List <Comptage> recupererRS()
	{			
		String rubriqueSolde = selectionRS.getSelectionModel().getSelectedItem();
		
		int indiceVersion = selectionVersion.getSelectionModel().getSelectedIndex();
		
		// Récupération de la dernière plus récente version
		// dans laquelle la rubrique de solde existe.
		for (int i = indiceVersion; i >= 0; i--)
		{
			String version = selectionVersion.getItems().get(i);

			Optional <Comptage> comptage = comptages.stream()
			  										.filter(item -> item.getVersion().equals(version))
			  										.filter(item -> item.getRubriqueSolde().equals(rubriqueSolde))
			  										.findFirst();

			if (comptage.isPresent())
			{
				comptageRS = comptage.get();

				break;
			}
		}

		String comptageRSVersion = comptageRS.getVersion();

		// Récupération des rubriques de soldes associées à celle choisie.
		// Celle choisie doit être exclue.
		List <Comptage> comptagesSelonVersion = comptages.stream()
	   		        									 .filter(item -> item.getVersion().equals(comptageRSVersion))
	   		        									 .filter(item -> !item.getRubriqueSolde().equals(rubriqueSolde))
	   		        									 .collect(Collectors.toList());
		
		
		comptagesSelonVersion.add(0, comptageRS);
		
		return comptagesSelonVersion;
	}
}
