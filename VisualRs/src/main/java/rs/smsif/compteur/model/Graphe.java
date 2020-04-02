package rs.smsif.compteur.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.FileSinkImages.Resolutions;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Graphe extends MultiGraph {
	
	private List <Comptage> comptages;
	
	public Graphe(List <Comptage> comptages)
	{
		super("Graphe");
		
		this.comptages = comptages;
		
		addAttribute("ui.stylesheet", "url('" + Main.class.getResource("/graphe.css") +"')");
	}
	
	public void construire()
	{
		// Récupération de la rubrique de solde centrale.
		String rsCentrale = comptages.get(0).getRubriqueSolde();
		
		for(int i = 0; i < comptages.size(); i++)
		{
			String rs = comptages.get(i).getRubriqueSolde();

			addNode(rs);
			
			// Pour la rs centrale, il n'y a aucun lien avec elle-même.
			if (i > 0)
			{
				addEdge(rsCentrale + rs, rsCentrale, rs, true).addAttribute("layout.weight", 2);
			}
			
			getNode(i).addAttribute("ui.label", rs);
			
			if (comptages.get(i).getCompteurEvo() > 0)
			{
				getNode(i).addAttribute("ui.class", "evo");
			}
			
			else if (comptages.get(i).getCompteurBar() > 0)
			{
				getNode(i).addAttribute("ui.class", "bar");
			}
		}
	}
	
	public void afficher(Scene sceneParent)
	{
		// Sauvegarde du graphe sous forme d'image.
		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.HD1080);
	    pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);

	     try
	     {
	        pic.setAutofit(true);
	        pic.writeAll(this, "graphe.png");
	        
	        File file = new File("graphe.png");
	        
	        // Affichage de l'image dans la fenêtre JavaFX.
	        Image image = new Image(file.toURI().toString());
	        
	        ImageView imageView = new ImageView();
	        imageView.setImage(image);
	        imageView.setLayoutX(100);
	        imageView.setFitHeight(500);
	        imageView.setPreserveRatio(true);
	        
	        BorderPane root = (BorderPane) sceneParent.getRoot();
			root.getChildren().add(imageView);
			
	    } catch (IOException e)
	    {
	    	
	    }
	}
}
