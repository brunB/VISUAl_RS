package rs.smsif.compteur.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * La classe Loader charge une nouvelle vue.
 * 
 * @author gmarco
 *
 */
public class Loader
{
	/**
	 * Charge une nouvelle vue.
	 * 
	 * @param chemin le chemin de la vue à charger.
	 * @param titre le titre de la vue.
	 * 
	 * @return le contrôleur associé à la vue créée.
	 * 
	 * @throws IOException lorsqu'il y a une erreur dans le chargement de la vue.
	 */
	public static Object charger(String chemin, String titre) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Loader.class.getResource(chemin));

        Stage stage = new Stage();
        
        Scene scene = new Scene((BorderPane) loader.load());
        
        stage.setScene(scene);
        stage.setTitle(titre);
        stage.setResizable(false);
        
        stage.show();
        
        return loader.getController();
	}
}

