package rs.smsif.compteur.view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BoutonSwitch extends HBox {

	private final Label texte;
	private final Button bouton;
	
	private String texteDroite;
	private String texteGauche;
	
	private static final int LARGEUR = 200;
	private static final int HAUTEUR = 39;
	
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	
	/**
	 * Constructeur.
	 * 
	 * @param x la position x du bouton.
	 * @param y la position y du bouton.
	 * @param texteDroite le texte affiché quand le bouton est à gauche.
	 * @param texteGauche le texte affiché quand le bouton est à droite.
	 */
	public BoutonSwitch(int x, int y, String texteDroite, String texteGauche) {
		
		texte = new Label(texteGauche);
		texte.setAlignment(Pos.CENTER);
		texte.setFocusTraversable(false);
		
		bouton = new Button();
		bouton.setFocusTraversable(false);
		
		switchedOn.set(false);
		
		this.texteDroite = texteDroite;
		this.texteGauche = texteGauche;
		
		setLayoutX(x);
		setLayoutY(y);
		setWidth(LARGEUR);
		setHeight(HAUTEUR);
				
		bouton.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
			changerEtat();
		});
		
		texte.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
			changerEtat();
		});
				
		lierProprietes();
		
		getChildren().addAll(texte, bouton);
	}
	
	/**
	 * Lie les propriétés de taille des composants à celle du composant mère.
	 */
	private void lierProprietes()
	{
		texte.prefHeightProperty().bind(heightProperty());
		texte.prefWidthProperty().bind(widthProperty().divide(2));
		
		bouton.prefHeightProperty().bind(heightProperty());
		bouton.prefWidthProperty().bind(widthProperty().divide(2));
	}
	
	/**
	 * Change l'état du bouton (on/off).
	 */
	private void changerEtat()
	{		
		if (switchedOn.get())
		{
    		texte.setText(texteDroite);
    		texte.toFront();
        }
		
        else
        {
			texte.setText(texteGauche);
    		bouton.toFront();
		}
	}
	
	public SimpleBooleanProperty getSwitchedOnProperty()
	{
		return switchedOn;
	}
}
