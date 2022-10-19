

public class Fitness {
    protected int score;
    protected int position;

    public Fitness(int score, int position){

        this.score=score;
        this.position=position;
    }


    public int getScore() {
        return score;
    }

    public int getPosition() {
        return position;
    }



    public void setScore(int score) {
        this.score = score;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
