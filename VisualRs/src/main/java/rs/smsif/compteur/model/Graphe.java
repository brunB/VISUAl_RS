package rs.smsif.compteur.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Cette classe permet de construire le graphe des rubriques de solde.
 * 
 * @author gmarco
 *
 */
public class Graphe extends SingleGraph {
	
	/**
	 * Constructeur.
	 */
	public Graphe()
	{
		super("Graphe");
		//System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		setAttribute("ui.stylesheet", "url('" + Main.class.getResource("/graphe.css") +"')");
	}
	
	/**
	 * Construit le graphe des rubriques de solde associées à une rubrique centrale.
	 * 
	 * @param comptageCentral la rubrique de solde centrale.
	 * @param comptages les rubriques de solde associées à la rubrique centrale.
	 * @param totalCompteurTotal 
	 */
	public void construire(Comptage comptageCentral, List <Comptage> comptages, int totalCompteurTotal)
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
				getNode(id).setAttribute("ui.label", comptages.get(i).getRubriqueSolde());
			}
			
			// Pour la rs centrale, il n'y a aucun lien avec elle-même.
			if (i > 0 )
			{
				// Ajout de la proportion de présence de la rubrique de solde.
				String pourcentage = String.format("%.1f", 100.0f * comptages.get(i).getCompteurTot() / totalCompteurTotal);
				
				addEdge(idRsCentrale + "->" + id, id, idRsCentrale, true);
				getEdge(idRsCentrale + "->" + id).setAttribute("ui.label", pourcentage + "%");
				
				// Les rubriques de solde s'impacte l'une et l'autre.
				// Création d'une flèche bidirectionnelle.
				if (comptages.get(i).getIndic() == 1)
				{
					addEdge(id + "->" + idRsCentrale, idRsCentrale, id, true);
				}
			}
			
			if (comptages.get(i).getCompteurEvo() > 0)
			{
				getNode(id).setAttribute("ui.class", "evo");
			}
			
			else if (comptages.get(i).getCompteurBar() > 0)
			{
				getNode(id).setAttribute("ui.class", "bar");
			}
		}
		
		comptages.remove(comptageCentral);
	}
	
	/**
	 * Affiche le graphe.
	 * 
	 * @param sceneParent la scène sur laquelle le graphe est affiché.
	 */
	public void afficher(Scene sceneParent)
	{	
		Viewer viewer = new FxViewer(this, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();
		
		FxDefaultView view = (FxDefaultView) viewer.addDefaultView(true);
		view.setLayoutX(30);
		view.setLayoutY(100);
		view.resize(1200, 550);
		
		((BorderPane) sceneParent.getRoot()).getChildren().add(view);
	}
}
