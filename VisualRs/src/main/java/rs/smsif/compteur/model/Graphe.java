package rs.smsif.compteur.model;

import java.util.HashMap;
import java.util.List;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Cette classe permet de construire le graphe des rubriques de solde.
 * 
 * @author gmarco
 *
 */
public class Graphe extends SingleGraph {
	
	private FxDefaultView vue;
	private HashMap <String, double[]> coordonnees;
	
	/**
	 * Constructeur.
	 */
	public Graphe()
	{
		this(new HashMap <String, double[]> ());
	}
	
	/**
	 * Constructeur.
	 * 
	 * @param coordonnees les coordonn�es de certains noeuds. Elles proviennent du graphe de r�f�rence.
	 */
	public Graphe(HashMap <String, double[]> coordonnees)
	{
		super("Graphe");

		//System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		this.coordonnees = coordonnees;
		
		setAttribute("ui.stylesheet", "url('" + Main.class.getResource("/graphe.css") +"')");
	}
	
	/**
	 * Construit le graphe des rubriques de solde associ�es � une rubrique centrale.
	 * 
	 * @param comptageCentral la rubrique de solde centrale.
	 * @param comptages les rubriques de solde associ�es � la rubrique centrale.
	 * @param totalCompteurTotal 
	 */
	public void construire(Comptage comptageCentral, List <Comptage> comptages, int totalCompteurTotal)
	{
		String idCentral = creerNoeud(comptageCentral);
		
		for(int i = 0; i < comptages.size(); i++)
		{
			String idNoeud = creerNoeud(comptages.get(i));
			
			// Ajout de la proportion de pr�sence de la rubrique de solde.
			float pourcentage = 100.0f * comptages.get(i).getCompteurTot() / totalCompteurTotal;
			
			creerArc(idCentral, idNoeud, String.format("%.1f", pourcentage) + "%");
			
			// Les rubriques de solde s'impacte l'une et l'autre.
			// Cr�ation d'une fl�che bidirectionnelle.
			if (comptages.get(i).getIndic() == 1)
			{
				creerArc(idNoeud, idCentral, "");
			}
		}
	}
	
	/**
	 * Cr�e un noeud.
	 * 
	 * @param comptage l'�l�ment associ� au noeud.
	 * 
	 * @return l'identifiant du noeud cr��.
	 */
	private String creerNoeud(Comptage comptage)
	{
		String id = comptage.getId();
		String texte = comptage.getRubriqueSolde();
		
		Node noeud = getNode(id);
		
		// V�rification de la pr�sence du noeud dans le graphe.
		// En effet, le noeud peut �tre le noeud feuille d'un noeud pr�c�dent
		// mais le noeud parent des nouveaux.
		if (noeud == null)
		{
			noeud = addNode(id);				
			noeud.setAttribute("ui.label", texte);
		}
		
		// Si le noeud existe dans le graphe de r�f�rence,
		// la position du nouveau noeud doit �tre identique.
		if (coordonnees.containsKey(texte))
		{
			noeud.setAttribute("xy", coordonnees.get(texte)[0], coordonnees.get(texte)[1]);
			noeud.setAttribute("layout.frozen");
		}
		
		if (comptage.getCompteurEvo() > 0)
		{
			noeud.setAttribute("ui.class", "evo");
		}
		
		else if (comptage.getCompteurBar() > 0)
		{
			noeud.setAttribute("ui.class", "bar");
		}
		
		return id;
	}
	
	/**
	 * Cr�e un arc entre deux noeuds.
	 * 
	 * @param idNoeudSource l'identifiant du noeud source.
	 * @param idNoeudCible l'identifiant du noeud cible.
	 * @param texte le texte associ� � l'arc.
	 */
	private void creerArc(String idNoeudSource, String idNoeudCible, String texte)
	{
		Edge arc = addEdge(idNoeudSource + "->" + idNoeudCible, idNoeudCible, idNoeudSource, true);
		
		arc.setAttribute("ui.label", texte);
	}
	
	/**
	 * Affiche le graphe.
	 * 
	 * @param sceneParent la sc�ne sur laquelle le graphe est affich�.
	 */
	public void afficher(Scene sceneParent)
	{	
		// Cr�ation de la vue.
		if (vue == null)
		{
			Viewer viewer = new FxViewer(this, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			
			Layout layout = new SpringBox(false);
					
			vue = (FxDefaultView) viewer.addDefaultView(true);
			vue.setLayoutX(30);
			vue.setLayoutY(100);
			vue.resize(1200, 550);
			
			// Calcul des positions des noeuds (en Unit� Graphiques (GU)).
			Toolkit.computeLayout(this, layout, 0.9);
		}
		
		BorderPane root = (BorderPane) sceneParent.getRoot();
		
		if (root.getChildren().contains(vue))
		{
			root.getChildren().remove(vue);
		}
		
		((BorderPane) sceneParent.getRoot()).getChildren().add(vue);
		
		coordonnees.clear();

		nodes().forEach(node -> {
			
			coordonnees.put(node.getAttribute("ui.label").toString(), Toolkit.nodePosition(node));
		});
	}
	
	/**
	 * Retourne les coordonn�es des noeuds du graphe.
	 * 
	 * @return les coordonn�es des noeuds du graphe.
	 */
	public HashMap <String, double[]> recupererCoordonneesNoeuds()
	{
		return coordonnees;
	}
}
