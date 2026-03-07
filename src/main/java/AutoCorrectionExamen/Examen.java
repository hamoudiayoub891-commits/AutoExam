package AutoCorrectionExamen;

import java.util.ArrayList;
import java.util.List;

public class Examen {

    private List<Question> questions = new ArrayList<>();


    public Question getQuestion(int index) {

        if (index >= 0 && index < questions.size()) {

            return questions.get(index);
        }

        return null;
    }

    public int getNombreQuestions() {

        return questions.size();
    }

    public void melangerQuestions() {

        OutilsGeneriques.melangerListe(this.questions);
    }

    public void ajouterQuestion(Question q) {

        this.questions.add(q);
    }

}
