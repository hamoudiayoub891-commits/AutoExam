package AutoCorrectionExamen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class ExamenFX extends Application {

    public static Examen examenEnCours;
    public static String nomEtudiantConnecte = "";

    private int indexQuestion = 0;

    private Map<Integer, String> memoireReponses = new HashMap<>();

    private Label labelEnonce = new Label();
    private VBox zoneReponse = new VBox(15);
    private Button btnPrecedent = new Button("<< Précédent");
    private Button btnSuivant = new Button("Suivant >>");

    private ToggleGroup groupeRadio;
    private TextField champTexte;

    @Override
    public void start(Stage primaryStage) {
        examenEnCours = GestionBaseDonnees.chargerExamenDepuisDB();

        // Conteneur principal avec style moderne
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-container");
        root.setPadding(new Insets(30, 40, 30, 40));

        // Titre de l'examen
        Label titreExamen = new Label("Examen POO");
        titreExamen.getStyleClass().add("exam-title");

        VBox topContainer = new VBox(titreExamen);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(topContainer);

        // Carte contenant la question (effet de carte moderne)
        VBox questionCard = new VBox(25);
        questionCard.getStyleClass().add("question-card");
        questionCard.setAlignment(Pos.TOP_LEFT);
        questionCard.setPadding(new Insets(30));
        VBox.setVgrow(questionCard, Priority.ALWAYS);

        // Configuration du label d'énoncé
        labelEnonce.setWrapText(true);
        labelEnonce.setTextAlignment(TextAlignment.LEFT);
        labelEnonce.getStyleClass().add("question-label");
        labelEnonce.setMaxWidth(Double.MAX_VALUE);

        // Zone de réponse avec espacement
        zoneReponse.setSpacing(18);
        zoneReponse.setPadding(new Insets(15, 0, 0, 0));

        questionCard.getChildren().addAll(labelEnonce, zoneReponse);

        // Wrapper pour centrer la carte
        StackPane centerWrapper = new StackPane(questionCard);
        centerWrapper.setPadding(new Insets(20, 0, 20, 0));
        root.setCenter(centerWrapper);

        // Zone de navigation avec styles
        HBox navigationBox = new HBox(20);
        navigationBox.getStyleClass().add("navigation-box");
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.setPadding(new Insets(25, 30, 25, 30));

        // Application des styles aux boutons
        btnPrecedent.getStyleClass().addAll("modern-button", "button-previous");
        btnSuivant.getStyleClass().add("modern-button");

        navigationBox.getChildren().addAll(btnPrecedent, btnSuivant);
        root.setBottom(navigationBox);

        // Gestionnaires d'événements (LOGIQUE INTACTE)
        btnSuivant.setOnAction(e -> {

            Question qActuelle = examenEnCours.getQuestion(indexQuestion);
            String reponseUser = null;

            if (qActuelle instanceof QuestionQCM) {
                if (groupeRadio.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) groupeRadio.getSelectedToggle();
                    int indexRep = groupeRadio.getToggles().indexOf(selected);
                    reponseUser = String.valueOf(indexRep + 1);
                }
            } else if (qActuelle instanceof QuestionNumerique) {
                reponseUser = champTexte.getText().trim();
            }

            if (reponseUser != null && !reponseUser.isEmpty()) {
                memoireReponses.put(indexQuestion, reponseUser);
            } else {
                memoireReponses.remove(indexQuestion);
            }

            if (indexQuestion == examenEnCours.getNombreQuestions() - 1) {

                try {

                    if (memoireReponses.size() < examenEnCours.getNombreQuestions()) {
                        int manque = examenEnCours.getNombreQuestions() - memoireReponses.size();
                        throw new ExamenNonTermineException("Il manque " + manque + " réponse(s).");
                    }

                    afficherResultatsFinaux();

                } catch (ExamenNonTermineException ex) {

                    Alert alerte = new Alert(Alert.AlertType.WARNING);
                    alerte.setTitle("Attention");
                    alerte.setHeaderText("Examen incomplet");
                    alerte.setContentText(ex.getMessage());
                    alerte.showAndWait();
                }

            } else {
                indexQuestion++;
                chargerQuestion();
            }
        });

        btnPrecedent.setOnAction(e -> questionPrecedente());

        chargerQuestion();

        // Création de la scène avec chargement du CSS
        Scene scene = new Scene(root, 750, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setTitle("Système d'Examen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void chargerQuestion() {
        Question q = examenEnCours.getQuestion(indexQuestion);

        labelEnonce.setText("Question " + (indexQuestion + 1) + " : " + q.getEnonce() + " (" + q.getScore() + " pts)");

        zoneReponse.getChildren().clear();

        if (q instanceof QuestionQCM) {
            setupQCM((QuestionQCM) q);
        } else if (q instanceof QuestionNumerique) {
            setupNumerique();
        }

        btnPrecedent.setDisable(indexQuestion == 0);

        if (indexQuestion == examenEnCours.getNombreQuestions() - 1) {
            btnSuivant.setText("Terminer l'examen");
            btnSuivant.getStyleClass().add("button-finish");
        } else {
            btnSuivant.setText("Suivant >>");
            btnSuivant.getStyleClass().remove("button-finish");
        }
    }

    private void setupQCM(QuestionQCM q) {
        groupeRadio = new ToggleGroup();
        String reponsePrecedente = memoireReponses.get(indexQuestion);

        int i = 1;
        for (String option : q.getOptions()) {
            RadioButton rb = new RadioButton(option);
            rb.getStyleClass().add("modern-radio");
            rb.setToggleGroup(groupeRadio);
            rb.setUserData(String.valueOf(i));

            if (reponsePrecedente != null && reponsePrecedente.equals(String.valueOf(i))) {
                rb.setSelected(true);
            }

            zoneReponse.getChildren().add(rb);
            i++;
        }
    }

    private void setupNumerique() {
        champTexte = new TextField();
        champTexte.getStyleClass().add("modern-textfield");
        champTexte.setPromptText("Votre réponse numérique...");

        String reponsePrecedente = memoireReponses.get(indexQuestion);
        if (reponsePrecedente != null) {
            champTexte.setText(reponsePrecedente);
        }

        zoneReponse.getChildren().add(champTexte);
    }

    private void sauvegarderReponseActuelle() {
        Question q = examenEnCours.getQuestion(indexQuestion);
        String reponse = null;

        if (q instanceof QuestionQCM) {
            if (groupeRadio.getSelectedToggle() != null) {

                reponse = groupeRadio.getSelectedToggle().getUserData().toString();
            }
        } else if (q instanceof QuestionNumerique) {
            reponse = champTexte.getText().trim();
        }

        if (reponse != null && !reponse.isEmpty()) {
            memoireReponses.put(indexQuestion, reponse);
        } else {
            memoireReponses.remove(indexQuestion);
        }
    }

    private void questionSuivante() {
        sauvegarderReponseActuelle();

        if (indexQuestion < examenEnCours.getNombreQuestions() - 1) {
            indexQuestion++;
            chargerQuestion();
        } else {

            afficherResultatsFinaux();
        }
    }

    private void questionPrecedente() {
        sauvegarderReponseActuelle();
        if (indexQuestion > 0) {
            indexQuestion--;
            chargerQuestion();
        }
    }

    private void afficherResultatsFinaux() {
        VBox resultatBox = new VBox(20);
        resultatBox.getStyleClass().add("results-container");
        resultatBox.setPadding(new Insets(40));
        resultatBox.setAlignment(Pos.TOP_CENTER);

        Label titreFin = new Label("Résultats de l'examen");
        titreFin.getStyleClass().add("results-title");

        double scoreTotal = 0;
        double scoreMax = 0;

        VBox detailsQuestions = new VBox(12);
        detailsQuestions.setPadding(new Insets(15));
        ScrollPane scroll = new ScrollPane(detailsQuestions);
        scroll.getStyleClass().add("scroll-pane");
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(320);

        for (int i = 0; i < examenEnCours.getNombreQuestions(); i++) {
            Question q = examenEnCours.getQuestion(i);
            String reponseEleve = memoireReponses.get(i);

            double points = 0;
            if (reponseEleve != null) {
                points = q.corriger(reponseEleve);
            }

            scoreTotal += points;
            scoreMax += q.getScore();

            Label ligneResultat = new Label();
            String status = (points > 0) ? "✅ Correct" : "❌ Incorrect";
            String repAffichee = (reponseEleve == null) ? "Aucune réponse" : reponseEleve;

            ligneResultat.setText("Q" + (i+1) + " (" + q.getScore() + "pts): " + status + " [Votre rep: " + repAffichee + "]");

            if (points > 0) {
                ligneResultat.getStyleClass().add("result-correct");
            } else {
                ligneResultat.getStyleClass().add("result-incorrect");
            }

            detailsQuestions.getChildren().add(ligneResultat);
        }

        Label scoreFinalLabel = new Label("Note Finale : " + scoreTotal + " / " + scoreMax);
        scoreFinalLabel.getStyleClass().add("final-score");

        ServiceFichier service = new ServiceFichier();

        service.sauvegarderResultat(nomEtudiantConnecte, scoreTotal);

        Label msgSauvegarde = new Label("(Résultat sauvegardé dans bulletin_notes.txt)");
        msgSauvegarde.getStyleClass().add("save-message");

        Separator sep = new Separator();
        sep.setPadding(new Insets(10, 0, 10, 0));

        resultatBox.getChildren().addAll(titreFin, scoreFinalLabel, msgSauvegarde, sep, scroll);

        Scene sceneFin = new Scene(resultatBox, 650, 650);
        sceneFin.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        Stage stage = (Stage) btnSuivant.getScene().getWindow();
        stage.setScene(sceneFin);
    }
}