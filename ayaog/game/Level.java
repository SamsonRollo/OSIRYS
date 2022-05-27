package ayaog.game;

public class Level {
    private int prize;
    private int level;

    public Level(){
        this.prize = 1000;
        this.level = 1;
    }

    public int getPrize(){
        return this.prize;
    }

    public void incrementLevel(){
        this.level++;
        updatePrize();
    }

    public void setLevel(int level){
        this.level = level;
        updatePrize();
    }

    public int getWinning(boolean lose){
        if(level<5)
            return 0;
        else if(level>5 && !lose)
            return prize;
        return 25000;
    }

    public void updatePrize(){
        switch(level){
            case 1:
                prize = 1000;
                break;
            case 2:
                prize = 2000;
                break;
            case 3:
                prize = 5000;
                break;
            case 4:
                prize = 10000;
                break;
            case 5:
                prize = 25000;
                break;
            case 6:
                prize = 50000;
                break;
            case 7:
                prize = 100000;
                break;
            case 8:
                prize = 200000;
                break;
            case 9:
                prize = 300000;
                break;
            case 10:
                prize = 500000;
                break;
            case 11:
                prize = 1000000;
                break;
        }
    }

    public int getLevel(){
        return this.level;
    }
    
}
