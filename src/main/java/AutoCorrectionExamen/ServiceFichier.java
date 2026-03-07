package AutoCorrectionExamen;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServiceFichier {

    public void sauvegarderResultat(String nomEtudiant, double note) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bulletin_notes.txt", true))) {

            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            String ligne = String.format("Étudiant : %-20s | Note : %5.2f/20 | Date : %s",
                    nomEtudiant, note, date);

            writer.write(ligne);

            writer.newLine();

        } catch (IOException e) {
            System.err.println("Erreur critique lors de l'écriture du fichier : " + e.getMessage());
            e.printStackTrace();
        }
    }
}