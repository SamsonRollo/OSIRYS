package gen;

public class InternalStateSave {
    private int ayaogLevel = 1;
    private int ayaogScore = 0;
    private boolean peekAlive = true;
    private boolean copyAlive = true;

    public InternalStateSave(){}

    public void setAyaogLevel(int level){
        this.ayaogLevel = level;
    }

    public int getAYAOGLevel(){
        return this.ayaogLevel;
    }

    public void setAyaogScore(int score){
        this.ayaogScore = score;
    }

    public int getAYAOGScore(){
        return this.ayaogScore;
    }

    public void setAyaogpeekAlive(boolean peek){
        this.peekAlive = peek;
    }

    public boolean getAYAOGpeekAlive(){
        return this.peekAlive;
    }

    public void setAyaogcopyAlive(boolean copy){
        this.copyAlive = copy;
    }

    public boolean getAYAOGcopyAlive(){
        return this.copyAlive;
    }
}
