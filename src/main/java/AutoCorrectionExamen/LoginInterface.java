package AutoCorrectionExamen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;



public class LoginInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Authentification - Système d'Examen");

        Label lblTitre = new Label("Connexion");
        lblTitre.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label lblUser = new Label("Nom d'utilisateur :");
        TextField txtUser = new TextField();
        txtUser.setPromptText("Ex: Ayoub walid");

        Label lblPass = new Label("Mot de passe :");
        PasswordField txtPass = new PasswordField();

        Button btnLogin = new Button("Se connecter");
        btnLogin.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white;");

        Label lblMessage = new Label();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(lblTitre, 0, 0, 2, 1);
        grid.add(lblUser, 0, 1);
        grid.add(txtUser, 1, 1);
        grid.add(lblPass, 0, 2);
        grid.add(txtPass, 1, 2);
        grid.add(btnLogin, 1, 3);
        grid.add(lblMessage, 1, 4);

        // --- Action du bouton ---
        btnLogin.setOnAction(e -> {
            String user = txtUser.getText().trim();
            String pass = txtPass.getText().trim();

            GestionBaseDonnees db = new GestionBaseDonnees();

            if (db.authentifier(user, pass)) {
                lblMessage.setTextFill(Color.GREEN);
                lblMessage.setText("Connexion réussie !");
                ExamenFX.nomEtudiantConnecte = user;

                primaryStage.close();

                ouvrirFenetreExamen();

            } else {
                lblMessage.setTextFill(Color.RED);
                lblMessage.setText("Identifiants incorrects.");
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void ouvrirFenetreExamen() {

        try {

            ExamenFX examen = new ExamenFX();

            Stage stageExamen = new Stage();

            examen.start(stageExamen);

        } catch (Exception e) {

            System.err.println("ERREUR CRITIQUE lors de l'ouverture de l'examen :");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
