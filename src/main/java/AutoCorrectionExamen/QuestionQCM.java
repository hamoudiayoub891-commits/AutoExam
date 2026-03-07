package AutoCorrectionExamen;


import java.util.ArrayList;
import java.util.List;

public class QuestionQCM extends Question {

    private final int indexBonneReponse;

    private List<String> options = new ArrayList<>();

    public QuestionQCM(int questionId, String enonce, double score, int indexBonneReponse) {

        super(questionId, enonce, score);

        this.indexBonneReponse = indexBonneReponse;
    }

    public void ajouterOption(String option) {

        this.options.add(option);
    }

    @Override
    public double corriger(Object reponseEtudiant) {

        try {

            int reponseChiffre = Integer.parseInt(reponseEtudiant.toString());

            if ((reponseChiffre - 1) == indexBonneReponse) {

                return getScore();
            }
            return 0.0;

        } catch (NumberFormatException e) {

            System.out.println("Erreur technique : La réponse n'est pas un nombre.");

            return 0.0;
        }
    }

    public List<String> getOptions() {

        return this.options;
    }

}
