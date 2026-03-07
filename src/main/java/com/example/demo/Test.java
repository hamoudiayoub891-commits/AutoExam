package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Test extends Application {
    public void start(Stage fenetre){
        Text text1 = new Text("Bonjour");
        Text text2 = new Text("Bonjour");

        VBox pane1 = new VBox();

        pane1.getChildren().add(text1);
        pane1.getChildren().add(text2);

        fenetre.setScene(new Scene(pane1));

        fenetre.show();


    }
}
