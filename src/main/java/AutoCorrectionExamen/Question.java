package AutoCorrectionExamen;

public abstract class Question implements Corrigible {

    private int QuestionId;
    private String enonce;
    private double score;

    public Question(int QuestionId, String enonce, double score) {

        this.QuestionId = QuestionId;
        this.enonce = enonce;
        this.score = score;
    }
    public int getQuestionId(){

        return QuestionId;
    }
    public String getEnonce(){

        return enonce;
    }
    public double getScore() {

        return score;
    }
    public void setEnonce(String enonce){

        this.enonce=enonce;
    }
    public void setScore(double score){

        this.score=score;
    }

    @Override
    public abstract double corriger(Object reponseEtudiant);
}