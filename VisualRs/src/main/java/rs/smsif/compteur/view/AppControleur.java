package rs.smsif.compteur.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import rs.smsif.compteur.model.Comptage;
import rs.smsif.compteur.model.Main;

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
		
		comptages.add(new Comptage("v1", "m1", "rs1", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs2", 0, 0, 1, 1));
		comptages.add(new Comptage("v1", "m1", "rs3", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs4", 0, 1, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs5", 0, 1, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs6", 0, 0, 1, 1));
		comptages.add(new Comptage("v1", "m1", "rs7", 0, 1, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs8", 0, 0, 0, 1));
		comptages.add(new Comptage("v1", "m1", "rs9", 0, 0, 0, 1));
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
		
		// La rubrique de solde centrale.
		String rsCentrale = comptageRS.getRubriqueSolde();
		
		Graph graph = new SingleGraph("Graphe");
		graph.addAttribute("ui.stylesheet", "url('" + Main.class.getResource("/graphe.css") +"')");
		
		for(int i = 0; i < comptages.size(); i++)
		{
			String rs = comptages.get(i).getRubriqueSolde();
			
			graph.addNode(rs);
			graph.addEdge(rsCentrale + rs, rsCentrale, rs);
			graph.getNode(i).addAttribute("ui.label", rs);
			
			if (comptages.get(i).getCompteurEvo() > 0)
			{
				graph.getNode(i).addAttribute("ui.class", "evo");
			}
			
			else if (comptages.get(i).getCompteurBar() > 0)
			{
				graph.getNode(i).addAttribute("ui.class", "bar");
			}
		}
		
		graph.display();
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
