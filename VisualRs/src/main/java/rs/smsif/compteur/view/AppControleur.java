package rs.smsif.compteur.view;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import rs.smsif.compteur.model.Comptage;
import rs.smsif.compteur.model.Graphe;
import rs.smsif.compteur.utils.OutilsComptage;

public class AppControleur {

	@FXML
	private ComboBox <String> selectionRS;

	@FXML
	private ComboBox <String> selectionVersionReference;
	
	@FXML
	private ComboBox <String> selectionVersionAnalysee;
	
	private Graphe graphe;
	
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
		comptagesBDD.add(new Comptage("07.14.01.a.r01","TAOPC","TAOPCO",13,3,3,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TAOPC","TAOPCO",14,0,0,1));
		comptagesBDD.add(new Comptage("07.19.00.c.r01","TAOPC","TAOPCO",38,0,3,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","FORM","FORMAT",30,2,10,1));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","FORM","RECRUT",37,2,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TEST","RECRUT",37,2,0,0));
		comptagesBDD.add(new Comptage("07.20.01.a.r01","TEST","AZER",37,2,11,1));
	}

	@FXML
	private void initialize()
	{
		/* LISTE DES RS FIGEES OBTENUE A PARTIR D'UNE TABLE */
		ObservableList <String> rubriquesSolde = FXCollections.observableArrayList("TAOPCO");
		selectionRS.setItems(rubriquesSolde);

		/* LISTE DES VERSIONS FIGEES OBTENUE A PARTIR D'UNE TABLE */
		ObservableList <String> versions = FXCollections.observableArrayList("07.14.01.a.r01","07.15.00.d.r01", "07.19.00.c.r01","07.19.00.d.r01", "07.20.01.a.r01");
		selectionVersionReference.setItems(versions);
		selectionVersionAnalysee.setItems(versions);

		// Chargement du graphique si le num�ro de version est renseign�.
		selectionRS.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionVersionReference.getSelectionModel().isEmpty()
				&& !selectionVersionAnalysee.getSelectionModel().isEmpty())
			{
				genererInterfaceSelonVersion();
			}
		});

		// Chargement du graphique si la rubrique de solde est renseign�e.
		selectionVersionReference.valueProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {

			if (!selectionRS.getSelectionModel().isEmpty()
				&& !selectionVersionAnalysee.getSelectionModel().isEmpty())
			{
				genererInterfaceSelonVersion();
			}
		});
		
		// Chargement du graphique si la rubrique de solde est renseign�e.
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
		String versionReference = selectionVersionReference.getSelectionModel().getSelectedItem();
		String versionAnalysee = selectionVersionAnalysee.getSelectionModel().getSelectedItem();
		
		BoutonSwitch bouton = new BoutonSwitch(850, 28, versionAnalysee, versionReference);

		BorderPane root = (BorderPane) (selectionRS.getScene().getRoot());
		root.getChildren().add(bouton);
		
		// Cr�ation du graphe selon la version choisie.
		bouton.getSwitchedOnProperty().addListener((ov, ancienneValeur, nouvelleValeur) -> {
			
			Comptage rsCentrale = null;

			bouton.definirEtat(nouvelleValeur);
			
			if (nouvelleValeur)
			{
				// R�cup�ration de la rubrique de solde centrale au graphique.
				rsCentrale = recupererComptageCentral(selectionVersionAnalysee);
			}
			
			else
			{
				// R�cup�ration de la rubrique de solde centrale au graphique.
				rsCentrale = recupererComptageCentral(selectionVersionReference);
			}
			
			construireGraphique(rsCentrale);
			graphe.afficher(selectionRS.getScene());
		});
	}
	
	/**
	 * Construit le graphe des rubriques de solde par rapport � une rubrique centrale.
	 * 
	 * @param rsCentrale la rubrique de solde centrale.
	 */
	public void construireGraphique(Comptage rsCentrale)
	{		
		// La liste des rubriques de solde associ�e � la rubrique centrale.
		List <Comptage> comptagesSelonVersion = OutilsComptage.recupererRubriqueSolde(rsCentrale, comptagesBDD);

		graphe.construire(rsCentrale, comptagesSelonVersion);
		
		// R�cup�ration des rubriques de soldes associ�es aux rubriques filles.
		for (Comptage comptage : comptagesSelonVersion)
		{
			if (comptage.getIndic() == 0)
			{
				List <Comptage> medros = OutilsComptage.recupererMedros(comptage, comptagesBDD);

				for (Comptage medro : medros)
				{
					construireGraphique(medro);
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
		graphe = new Graphe();

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
