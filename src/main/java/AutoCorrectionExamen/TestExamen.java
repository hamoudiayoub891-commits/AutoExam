package AutoCorrectionExamen;

import javafx.application.Application;

public class TestExamen {

     static void main(String[] args) {

        Examen examen = GestionBaseDonnees.chargerExamenDepuisDB();

        examen.melangerQuestions();

        ExamenFX.examenEnCours = examen;

        Application.launch(ExamenFX.class, args);
    }
}