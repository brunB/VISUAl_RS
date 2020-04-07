package rs.smsif.compteur.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import rs.smsif.compteur.dao.ComptageDAO;
import rs.smsif.compteur.dao.DAO;
import rs.smsif.compteur.dao.VersionLvsDAO;
import rs.smsif.compteur.dao.VisualrsConnexion;
import rs.smsif.compteur.utils.Loader;

public class Main extends Application {

	public static void main(String[] args)
	{
		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		launch(args);
		System.exit(0);
	}

	@Override
	public void start(Stage stage)
	{
		/*
		DAO<Comptage> comptageDao = new ComptageDAO(VisualrsConnexion.getInstance());
		//07.00.00.a.r00	ARP	REPOPX
	     Comptage cpt = comptageDao.find("07.00.00.a.r00","ARP","REPOPX");
	     System.out.println(cpt.toString());

	     // DELETE
	     int del = comptageDao.delete(cpt);
	     if (del == 1) {
	    	 System.out.println("REPOPX de ARP de la 07.20.00.a.r00 a bien été supprimée");
	     }

	     // INSERT
	     Comptage REPOPX = new Comptage("07.20.00.a.r00", "ARP", "REPOPX", 1, 0, 0, 0);
	     del = comptageDao.create(REPOPX);
	     if (del == 1) {
	    	 System.out.println("REPOPX de ARP de la 07.20.00.a.r00 a bien été ajouté");
	     }
		*/

/*
		VersionLvsDAO vld = new VersionLvsDAO(VisualrsConnexion.getInstance());
		List<String> listVersionLvs = vld.selectAllString();
		for (String string : listVersionLvs) {
			System.out.println(string);
		}
*/
		/*
		DAO<VersionLvs> vdao = new VersionLvsDAO(VisualrsConnexion.getInstance());
		List<VersionLvs> listvDao = vdao.selectAll();
		System.out.println("####################");
		for (VersionLvs versionLvs : listvDao) {

			System.out.println(versionLvs.toString());
		}
		*/


		try
		{
			Loader.charger("/rs/smsif/compteur/view/App.fxml", "VisualRS");

		} catch (IOException e)
		{
			e.printStackTrace();
		}



	}
}
