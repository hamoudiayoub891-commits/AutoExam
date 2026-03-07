package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;

import static javafx.application.Application.launch;

public class ListeCouleurs extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage fenetre){
    FlowPane root = new FlowPane();
        root.setVgap(10);
        root.setHgap(10);
        root.setPadding(new Insets(10,10,10,10));
        root.setStyle("-fx-background-color: black;");

    Random random = new Random();

    Circle grandCircle = new Circle(100);
        grandCircle.setFill(Color.WHITE);
        grandCircle.setStroke(Color.BLACK);

    StackPane zoneBas = new StackPane(grandCircle);
        zoneBas.setPadding(new Insets(20));

        for(int i=0; i<112; i++){

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        Color couleuraleatoire = Color.rgb(r,g,b);

        Circle petitCircle = new Circle(15);

        petitCircle.setFill(couleuraleatoire);

        petitCircle.setOnMouseClicked(event -> {
            grandCircle.setFill(petitCircle.getFill());
        });

        root.getChildren().add(petitCircle);
    }
    BorderPane rot = new BorderPane();
        rot.setCenter(root);
        rot.setBottom(zoneBas);


    Scene scene = new Scene(rot, 600,600);
        fenetre.setTitle("Liste Couleurs");
        fenetre.setScene(scene);
        fenetre.show();
}
}
