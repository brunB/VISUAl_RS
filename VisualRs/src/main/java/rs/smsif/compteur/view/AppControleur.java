package rs.smsif.compteur.view;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import rs.smsif.compteur.dao.ComptageDAO;
import rs.smsif.compteur.dao.DAO;
import rs.smsif.compteur.dao.VersionLvsDAO;
import rs.smsif.compteur.dao.VisualrsConnexion;
import rs.smsif.compteur.model.Comptage;
import rs.smsif.compteur.model.VersionLvs;
import javafx.scene.layout.BorderPane;
import rs.smsif.compteur.model.Graphe;
import rs.smsif.compteur.utils.OutilsComptage;

public class AppControleur {

	@FXML
	private ComboBox <String> selectionRS;

	@FXML
	private ComboBox <String> selectionVersionReference;

	@FXML
	private ComboBox <String> selectionVersionAnalysee;

	private BoutonSwitch boutonSelectionVersion;

	private Graphe grapheVersionReference;

	private Graphe grapheVersionAnalysee;

	private ObservableList <Comptage> comptagesBDD;

	/**
	 * Constructeur.
	 */
	public AppControleur()
	{
		comptagesBDD = FXCollections.observableArrayList();

		comptagesBDD.add(new Comptage("07.14.01.a.r01","TAOPC","FORMAT",2,0,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TAOPC","FORMAT",2,0,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TAOPC","YYYY",2,0,0,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TAOPC","AAAA",2,0,0,1));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TAOPC","FORMAT",4,0,0,0));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TAOPC","YYYY",4,0,0,1));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TAOPC","AAAA",4,0,0,1));
		comptagesBDD.add(new Comptage("07.14.01.a.r01","TAOPC","TAOPCO",13,3,3,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TAOPC","TAOPCO",14,0,0,1));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TAOPC","TAOPCO",38,0,3,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","FORM","FORMAT",30,2,10,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","FORM","RECRUT",37,2,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TEST","RECRUT",37,2,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TEST","AZER",37,2,11,1));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","FORM","FORMAT",30,2,10,1));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","FORM","RECRUT",37,2,0,0));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TEST","RECRUT",37,2,0,0));
	}

	@FXML
	private void initialize()
	{
		VersionLvsDAO vld = new VersionLvsDAO(VisualrsConnexion.getInstance());
		List<String> listVersionLvs = vld.selectAllString();
		for (String string : listVersionLvs) {
			System.out.println(string);
		}
		/* LISTE DES RS FIGEES OBTENUE A PARTIR D'UNE TABLE */
		ObservableList <String> rubriquesSolde = FXCollections.observableArrayList("TAOPCO");
		selectionRS.setItems(rubriquesSolde);

		/* LISTE DES VERSIONS FIGEES OBTENUE A PARTIR D'UNE TABLE */
		ObservableList <String> versions = FXCollections.observableArrayList(listVersionLvs);
		selectionVersionReference.setItems(versions);
		selectionVersionAnalysee.setItems(versions);


		// Chargement du graphique si la version de r�f�rence et analys�e sont renseign�es.
		selectionRS.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionVersionReference.getSelectionModel().isEmpty()
				&& !selectionVersionAnalysee.getSelectionModel().isEmpty())
			{
				genererInterfaceSelonVersion();
			}
		});

		// Chargement du graphique si la rubrique de solde et la version analys�e sont renseign�es.
		selectionVersionReference.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionRS.getSelectionModel().isEmpty()
				&& !selectionVersionAnalysee.getSelectionModel().isEmpty())
			{
				genererInterfaceSelonVersion();
			}
		});

		// Chargement du graphique si la rubrique de solde et la version de r�f�rence sont renseign�es.
		selectionVersionAnalysee.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionRS.getSelectionModel().isEmpty()
				&& !selectionVersionReference.getSelectionModel().isEmpty())
			{
				genererInterfaceSelonVersion();
			}
		});
	}

	/**
	 * G�n�re le bouton de switch entre la version de r�f�rence et la version analys�e.
	 * Le graphe affich� d�pend de la version choisie.
	 */
	public void genererInterfaceSelonVersion()
	{
		// R�cup�ration des versions.
		String versionReference = selectionVersionReference.getSelectionModel().getSelectedItem();
		String versionAnalysee = selectionVersionAnalysee.getSelectionModel().getSelectedItem();

		BorderPane root = (BorderPane) (selectionRS.getScene().getRoot());

		// Cr�ation d'un nouveau bouton switch � chaque changement de version.
		if (root.getChildren().contains(boutonSelectionVersion))
		{
			root.getChildren().remove(boutonSelectionVersion);
		}

		boutonSelectionVersion = new BoutonSwitch(800, 28, versionAnalysee, versionReference);
		root.getChildren().add(boutonSelectionVersion);

		// G�n�ration du graphe de la version de r�f�rence.
		grapheVersionReference = new Graphe();
		Comptage rsCentraleReference = recupererComptageCentral(selectionVersionReference);
		construireGraphique(rsCentraleReference, grapheVersionReference);

		// Affichage du graphe de r�f�rence par d�faut.
		grapheVersionReference.afficher(selectionRS.getScene());

		// G�n�ration du graphe de la version analys�e.
		// Le graphe de la version analys�e s'appuie sur celui de r�f�rence.
		// Les noeuds en commun sont conserv�s � la m�me position, autant que possible.
		// La comparaison est ainsi plus facile � faire.
		grapheVersionAnalysee = new Graphe(grapheVersionReference.recupererCoordonneesNoeuds());
		Comptage rsCentraleAnalysee = recupererComptageCentral(selectionVersionAnalysee);
		construireGraphique(rsCentraleAnalysee, grapheVersionAnalysee);

		// Cr�ation du graphe selon la version choisie.
		boutonSelectionVersion.getSwitchedOnProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (nouvelleValeur)
			{
				grapheVersionAnalysee.afficher(selectionRS.getScene());
			}

			else
			{
				grapheVersionReference.afficher(selectionRS.getScene());
			}
		});
	}

	/**
	 * Construit le graphe des rubriques de solde par rapport � une rubrique centrale.
	 *
	 * @param rsCentrale la rubrique de solde centrale.
	 */
	public void construireGraphique(Comptage rsCentrale, Graphe graphe)
	{
		// La liste des rubriques de solde associ�e � la rubrique centrale.
		List <Comptage> comptagesSelonVersion = OutilsComptage.recupererRubriqueSolde(rsCentrale, comptagesBDD);

		int totalCompteurTotal = 0;

		for (Comptage comptage : comptagesSelonVersion)
		{
			totalCompteurTotal += comptage.getCompteurTot();
		}

		graphe.construire(rsCentrale, comptagesSelonVersion, totalCompteurTotal);

		// R�cup�ration des rubriques de soldes associ�es aux rubriques filles.
		for (Comptage comptage : comptagesSelonVersion)
		{
			if (comptage.getIndic() == 0)
			{
				List <Comptage> medros = OutilsComptage.recupererMedros(comptage, comptagesBDD);

				for (Comptage medro : medros)
				{
					construireGraphique(medro, graphe);
				}
			}
		}
	}

	/**
	 * Retourne la rubrique de solde centrale.
	 * Si la rubrique n'existe pas pour les versions demand�es, les plus r�centes derni�res versions
	 * qui contiennent la rubrique sont s�lectionn�es.
	 *
	 * @return la rubrique de solde centrale.
	 */
	public Comptage recupererComptageCentral(ComboBox <String> comboBoxVersion)
	{
		Comptage rsCentrale = null;

		// R�cup�ration des informations choisies par l'utilsiateur.
		String rubriqueSolde = selectionRS.getSelectionModel().getSelectedItem();
		int indiceVersion = comboBoxVersion.getSelectionModel().getSelectedIndex();

		// R�cup�ration de la derni�re plus r�cente version
		// dans laquelle la rubrique de solde existe.
		for (int i = indiceVersion; i >= 0; i--)
		{
			String version = selectionVersionReference.getItems().get(i);

			Optional <Comptage> comptage = comptagesBDD.stream()
			  										.filter(item -> item.getVersion().equals(version))
			  										.filter(item -> item.getRubriqueSolde().equals(rubriqueSolde))
			  										.findFirst();

			if (comptage.isPresent())
			{
				rsCentrale = comptage.get();

				break;
			}
		}

		return rsCentrale;
	}
}
