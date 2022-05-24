package gen;

public class Score {
    private int gameScore;
    private int totalScore;
    private int levelScore;

    public Score(){
        this.gameScore = 0;
        this.totalScore = 0;
        this.levelScore = 0;
    }

    public Score(int totalScore){
        this.totalScore = totalScore;
        this.gameScore = 0;
        this.levelScore = 0;
    }

    public void setTotalScore(int totalScore){
        this.totalScore = totalScore;
    }

    public void incrementTotalScore(int score){
        this.totalScore += score;
    }

    public int getTotalScore(){
        return this.totalScore;
    }

    public void setGameScore(int score){
        this.gameScore = score;
    }

    public void incrementGameScore(int score){
        this.gameScore+=score;
    }

    public int getGameScore(){
        return this.gameScore;
    }

    public void resetCurrentGameScore(){
        this.gameScore = 0;
    }

    public void setLevelScore(int levelScore){
        this.levelScore = levelScore;
    }

    public void incrementLevelScore(int levelScore){
        this.levelScore+=levelScore;
    }

    public int getLevelScore(){
        return this.levelScore;
    }

    public void resetCurrentLevelScore(){
        this.levelScore = 0;
    }
}
