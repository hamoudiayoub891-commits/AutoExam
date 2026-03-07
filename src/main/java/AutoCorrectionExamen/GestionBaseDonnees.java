package AutoCorrectionExamen;

import java.sql.*;

public class GestionBaseDonnees {

    private static final String URL = "jdbc:mysql://localhost:3306/examen_db";

    private static final String USER = "root";

    private static final String PASSWORD = "2020";

    private Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/examen_db";

        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    public boolean authentifier(String nom, String motDePasse) {

        String sql = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";

        try (java.sql.Connection conn = this.getConnection()) {

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, motDePasse);

            java.sql.ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (java.sql.SQLException e) {
            System.out.println("ERREUR CRITIQUE : Problème de connexion ou SQL !");
            e.printStackTrace();
            return false;
        }
    }

    public static Examen chargerExamenDepuisDB() {

        Examen examen = new Examen();

        try

             (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

             Statement stmt = conn.createStatement();

             ResultSet rs = stmt.executeQuery("SELECT * FROM questions")) {


            while (rs.next()) {

                String type = rs.getString("type_question");

                String enonce = rs.getString("enonce");

                double score = rs.getDouble("score");

                String reponse = rs.getString("reponse");

                if ("NUM".equals(type)) {

                    double repAttendue = Double.parseDouble(reponse);

                    examen.ajouterQuestion(new QuestionNumerique(0, enonce, score, repAttendue));
                }
                else if ("QCM".equals(type)) {

                    int indexBonneRep = Integer.parseInt(reponse) - 1;

                    QuestionQCM qcm = new QuestionQCM(0, enonce, score, indexBonneRep);

                    String optionsBrutes = rs.getString("options");

                    if (optionsBrutes != null) {

                        String[] tabOptions = optionsBrutes.split(";");

                        for (String opt : tabOptions) {

                            qcm.ajouterOption(opt);
                        }
                    }
                    examen.ajouterQuestion(qcm);
                }
            }


        } catch (SQLException e) {

            System.err.println(" Erreur de connexion JDBC : " + e.getMessage());

            e.printStackTrace();

        }
        return examen;
    }
}