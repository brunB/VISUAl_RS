package rs.smsif.compteur.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import rs.smsif.compteur.model.Comptage;

/**
 * Cette classe représente les rubriques de solde sous forme graphique.
 * 
 * @author gmarco
 *
 */
public class Bulle extends StackPane {

	private static final int RAYON_REFERENCE = 60;
	private static final Color COULEUR_DEFAUT = Color.CORNFLOWERBLUE;
	
	private Text texte;
	private Circle cercle;
	
	public Bulle(int x, int y, Comptage comptage)
	{		
		cercle = new Circle();
		cercle.setRadius(RAYON_REFERENCE);
		
		texte = new Text(comptage.getRubriqueSolde());
		texte.setStyle("-fx-font: 24 arial;");
				
		if (comptage.getCompteurEvo() > 0)
		{
			cercle.setFill(Color.RED);
		}
		
		if (comptage.getCompteurBar() > 0)
		{
			cercle.setFill(Color.LAWNGREEN);
		}
		
		else
		{
			cercle.setFill(COULEUR_DEFAUT);
		}
		
		setLayoutX(x);
		setLayoutY(y);
		getChildren().addAll(cercle, texte);
	}
}
