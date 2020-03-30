package rs.smsif.compteur.view;

import java.util.List;
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
	
	public AppControleur()
	{
		comptages = FXCollections.observableArrayList();
		
		comptages.add(new Comptage("v1", "m1", "rs1", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs2", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs3", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs4", 0, 0, 0, 1));
		comptages.add(new Comptage("v2", "m1", "rs1", 0, 0, 0, 1));
		comptages.add(new Comptage("v2", "m1", "rs2", 0, 0, 0, 1));
		comptages.add(new Comptage("v3", "m1", "rs1", 0, 0, 0, 1));
	}
	
	@FXML
	private void initialize()
	{
		ObservableList <String> rubriquesSolde = FXCollections.observableArrayList("rs1");
		selectionRS.setItems(rubriquesSolde);
		
		ObservableList <String> version = FXCollections.observableArrayList("v1", "v2", "v3");
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
	
	public void recupererRS()
	{
		String version = selectionVersion.getSelectionModel().getSelectedItem();
		
		// Récupération des rubriques de solde de la version demandée.
		List<Comptage> comptagesSelonRS = comptages.stream()
			     						   		   .filter(item -> item.getVersion().equals(version))
			     						   		   .collect(Collectors.toList());
		
		System.out.println(comptagesSelonRS);
	}
}
