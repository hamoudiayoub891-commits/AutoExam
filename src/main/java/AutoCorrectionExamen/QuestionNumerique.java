package AutoCorrectionExamen;

public class QuestionNumerique extends Question {

    private double reponseAttendue;

    public QuestionNumerique(int id, String enonce, double score, double reponseAttendue) {

        super(id, enonce, score);

        this.reponseAttendue = reponseAttendue;
    }

    @Override
    public double corriger(Object reponseEtudiant) {

        try {

            double reponseDonnee = Double.parseDouble(reponseEtudiant.toString());

            if (reponseDonnee == this.reponseAttendue) {

                return getScore();

            } else {

                return 0.0;
            }

        } catch (NumberFormatException e) {

            System.out.println("Erreur : La valeur saisie n'est pas un nombre valide.");

            return 0.0;
        }
    }
}