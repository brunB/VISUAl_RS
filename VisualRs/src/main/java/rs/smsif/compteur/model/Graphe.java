package rs.smsif.compteur.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Graphe extends SingleGraph {
		
	public Graphe()
	{
		super("Graphe");
				
		addAttribute("ui.stylesheet", "url('" + Main.class.getResource("/graphe.css") +"')");
	}
	
	public void construire(Comptage comptageCentral, List <Comptage> comptages)
	{
		comptages.add(0, comptageCentral);
		
		// Récupération de la rubrique de solde centrale.
		String idRsCentrale = comptages.get(0).getId();

		for(int i = 0; i < comptages.size(); i++)
		{
			String id = comptages.get(i).getId();

			// Vérification de la présence du noeud dans le graphe.
			// En effet, le noeud peut être le noeud feuille d'un noeud précédent
			// mais le noeud parent des nouveaux.
			if (getNode(id) == null)
			{
				addNode(id);
				getNode(id).addAttribute("ui.label", comptages.get(i).getMedro());
			}
			
			// Pour la rs centrale, il n'y a aucun lien avec elle-même.
			if (i > 0)
			{
				addEdge(idRsCentrale + id, id, idRsCentrale, true);//.addAttribute("layout.weight", 2);
			}
			
			if (comptages.get(i).getCompteurEvo() > 0)
			{
				getNode(id).addAttribute("ui.class", "evo");
			}
			
			else if (comptages.get(i).getCompteurBar() > 0)
			{
				getNode(id).addAttribute("ui.class", "bar");
			}
		}
		
		comptages.remove(comptageCentral);
	}
	
	public void afficher(Scene sceneParent)
	{				
		// Sauvegarde du graphe sous forme d'image.
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.HD1080);
	    pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
	    pic.setStyleSheet("node {text-mode: normal;text-size: 20px;text-alignment: center;}");

	     try
	     {
	        pic.setAutofit(true);
	        pic.writeAll(this, "graphe.png");
	        
	        File file = new File("graphe.png");
	        
	        // Affichage de l'image dans la fenêtre JavaFX.
	        Image image = new Image(file.toURI().toString());
	        
	        ImageView imageView = new ImageView();
	        imageView.setImage(image);
	        imageView.setLayoutX(150);
	        imageView.setLayoutY(100);
	        imageView.setFitHeight(570);
	        imageView.setPreserveRatio(true);
	        
	        BorderPane root = (BorderPane) sceneParent.getRoot();
			root.getChildren().add(imageView);
			
	    } catch (IOException e)
	    {
	    	
	    }
	}
}
