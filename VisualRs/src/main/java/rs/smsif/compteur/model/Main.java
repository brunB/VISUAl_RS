package rs.smsif.compteur.model;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import rs.smsif.compteur.dao.ComptageDAO;
import rs.smsif.compteur.dao.DAO;
import rs.smsif.compteur.dao.VisualrsConnexion;
import rs.smsif.compteur.utils.Loader;

public class Main extends Application {

	public static void main(String[] args) {
		/**
		 * TESTS DAO ParamDAO
		 */
		/*Connection connexion = VisualrsConnexion.getInstance();
		DAO<ParamRs> paramdao = new ParamDAO(connexion);
		List<ParamRs> listParams = paramdao.selectAll();
		for (ParamRs paramRs : listParams) {
			System.out.println(paramRs.toString());
		}
		// Objet ParamRs
		ParamRs test = new ParamRs();
		ParamRs param = new ParamRs("01.01.01.a.r01", "TEST", "TestParam", 10, 5, 0, 0);
		int val = paramdao.create(param);
		System.out.println("valeur retour create = "+val);

		test = paramdao.find(param.getVersion(), param.getMedro(), param.getParamRs());
		System.out.println("Test injection de param : "+test.toString());

		val=paramdao.delete(param);
		System.out.println("valeur retour create = "+val);
		test = paramdao.find("01.01.01.a.r01", "TEST", "TestParam");*/


		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		launch(args);
		System.exit(0);
	}

	@Override
	public void start(Stage stage) {
		try {
			Loader.charger("/rs/smsif/compteur/view/App.fxml", "VisualRS");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
