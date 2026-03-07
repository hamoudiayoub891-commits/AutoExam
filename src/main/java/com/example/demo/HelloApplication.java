package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class HelloApplication extends Application {

    public void start(Stage fenetre) throws Exception {

        ArrayList<Color> colors = new ArrayList<Color>();
        Class<?> myClass = Class.forName("javafx.scene.paint.Color");


        if (myClass != null) {
            Field[] field = myClass.getFields();
            for (int i = 0; i < field.length; i++) {
                Field f = field[i];
                Object obj = f.get(null);
                if (obj instanceof Color) {
                    colors.add((Color) obj);
                }
            }
        }

            FlowPane flow = new FlowPane();
            flow.setHgap(10);
            flow.setVgap(10);
            flow.setPadding(new Insets(20));
            flow.setAlignment(Pos.CENTER);

            flow.setStyle("-fx-background-color: black;");

            Circle grandCercle = new Circle(60);
            grandCercle.setFill(Color.WHITE);
            grandCercle.setStroke(Color.BLACK);

        StackPane stack = new StackPane(grandCercle);
        stack.setPadding(new Insets(10,10,10,10));

            for (Color q : colors) {
                Circle petitCercle = new Circle(15);

                petitCercle.setFill(q);

                petitCercle.setOnMouseClicked(event -> {
                    grandCercle.setFill(petitCercle.getFill());
                });
                flow.getChildren().add(petitCercle);

            }
            BorderPane root = new BorderPane();
            root.setBottom(stack);
            root.setCenter(flow);

            Scene scene = new Scene(root, 700, 700);
            fenetre.setTitle("Couleurs");
            fenetre.setScene(scene);
            fenetre.show();

    }

}