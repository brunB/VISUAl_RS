package rs.smsif.compteur.model;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import rs.smsif.compteur.utils.Loader;

public class Main extends Application {

	public static void main(String[] args) 
	{
		launch(args);
				
		System.exit(0);
	}

	@Override
	public void start(Stage stage)
	{
		try 
		{
			Loader.charger("/rs/smsif/compteur/view/App.fxml", "Application");

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
