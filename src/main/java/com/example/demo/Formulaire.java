package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Formulaire extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public  void start(Stage fenetre){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Text titre = new Text("Bienvenue");
        titre.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(titre, 0,0,2,1);

        Label labelUser = new Label("Utilisateur :");
        grid.add(labelUser,0,1);

        TextField champUser = new TextField();
        grid.add(champUser, 1,1);

        Label labelPass = new Label("Mot de passe :");
        grid.add(labelPass, 0,2);

        PasswordField champPass = new PasswordField();
        grid.add(champPass,1,2);

        Button btn = new Button("Se connecter");
        grid.add(btn,1,3);

        btn.setOnAction(event -> {

            String nom = champUser.getText();
            String motDePasse = champPass.getText();

            if (nom.equals("Ayoub") && motDePasse.equals("2020")) {
                Alert alerteinfo = new Alert(Alert.AlertType.INFORMATION);
                alerteinfo.setTitle("succès");
                alerteinfo.setHeaderText("Bienvenu " + nom);
                alerteinfo.setContentText("Vous êtes bien connecé !");
                alerteinfo.showAndWait();
            } else {
                Alert alerteErreur = new Alert(Alert.AlertType.ERROR);
                alerteErreur.setTitle("Erreur");
                alerteErreur.setHeaderText("Echec !");
                alerteErreur.setContentText("Le nom d'utilisateur ou le mot de passe est incorrect");
                alerteErreur.showAndWait();
            }
        });

        Scene maScene = new Scene(grid,350,275);
        fenetre.setTitle(" de Connexion");
        fenetre.setScene(maScene);
        fenetre.show();
    }
}
