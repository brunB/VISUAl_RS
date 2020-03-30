package rs.smsif.compteur.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import rs.smsif.compteur.model.Comptage;

public class AppControleur {

	@FXML
	private ComboBox <String> selectionRS;
	
	@FXML
	private ComboBox <String> selectionVersion;
	
	private ObservableList <Comptage> comptages;
	
	/**
	 * Constructeur.
	 */
	public AppControleur()
	{
		comptages = FXCollections.observableArrayList();
		
		comptages.add(new Comptage("v1", "m1", "rs1", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs2", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs3", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs4", 0, 0, 0, 1));
		comptages.add(new Comptage("v2", "m1", "rs2", 0, 0, 0, 1));
		comptages.add(new Comptage("v2", "m1", "rs3", 0, 0, 0, 1));
		comptages.add(new Comptage("v3", "m1", "rs3", 0, 0, 0, 1));
		comptages.add(new Comptage("v4", "m1", "rs1", 0, 0, 0, 1));
	}
	
	@FXML
	private void initialize()
	{
		ObservableList <String> rubriquesSolde = FXCollections.observableArrayList("rs1");
		selectionRS.setItems(rubriquesSolde);
		
		ObservableList <String> version = FXCollections.observableArrayList("v1", "v2", "v3", "v4");
		selectionVersion.setItems(version);
		
		// Chargement du graphique si le numéro de version est renseigné.
		selectionRS.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {
				
			if (!selectionVersion.getSelectionModel().isEmpty())
			{
				recupererRS();
			}
		});
		
		// Chargement du graphique si la rubrique de solde est renseignée.
		selectionVersion.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {
			
			if (!selectionRS.getSelectionModel().isEmpty())
			{
				recupererRS();
			}
		});
	}
	
	/**
	 * Récupère les rubriques de solde associées à celle demandée pour la version souhaitée.
	 * Dans le cas où la rubrique de solde n'existe pas pour la version,
	 * la dernière plus récente version sera prise en compte.
	 */
	public void recupererRS()
	{	
		Comptage comptageRS = null;
		
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
		
		System.out.println(comptagesSelonVersion);
	}
}
