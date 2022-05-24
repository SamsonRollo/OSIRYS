package coc.game;

public class Level {
    private int level;
    private int ySpeed;
    private int xSpeed;
    private int bulletSpeed;
    private int bulletLag;
    private int bulletLevel;
    private int bugLag;
    private int targetScore;

    public Level(){
        this.level = 1;
        this.ySpeed = 7;
        this.xSpeed = 1;
        this.bulletSpeed = 4;
        this.bulletLag = 12;
        this.bulletLevel = 1;
        this.bugLag = 30;
        this.targetScore = this.level*91+13;
    }

    public int getTargetScore(){
        return this.targetScore;
    }

    public int getLevel(){
        return this.level;
    }

    public void setLevel(int level){
        this.level = level;
        this.targetScore = this.level*91+13;
        this.bulletSpeed = this.bulletSpeed+(int)Math.floor(level/3) >10 ? 10 : this.bulletSpeed+(int)Math.floor(level/3);
        this.bulletLag = this.bulletLag-(level*2) < 5 ? 5 : this.bulletLag-(level*2);
        this.ySpeed = this.ySpeed+(int)Math.floor(level/2) > 20 ? 20 : this.ySpeed+(int)Math.floor(level/2);
        this.bugLag = this.bugLag-(level*5) < 10 ? 10 : this.bugLag-(level*5);
    }

    public void incrementLevel(){
        this.level++;
        this.targetScore = this.level*91+13;
        if(this.level%3==0 && bulletSpeed<11)
            this.bulletSpeed++;
        if(bulletLag>5)
            this.bulletLag-=2;
        if(this.level%2==0 && ySpeed<20)
            this.ySpeed++;
        if(bugLag>10)
            this.bugLag-=5;
        else if(bugLag-5<10)
            this.bugLag = 10;
    }

    public int getYSpeed(){
        return this.ySpeed;
    }

    public void setYSpeed(int ySpeed){
        this.ySpeed = ySpeed;
    }

    public void incrementYSpeed(){
        this.ySpeed++;
    }

    public void incrementYSpeed(int increment){
        this.ySpeed += increment;
    }

    public int getXSpeed(){
        return this.xSpeed;
    }

    public void setXSpeed(int xSpeed){
        this.xSpeed = xSpeed;
    }

    public void incrementXSpeed(){
        this.xSpeed++;
    }

    public void incrementXSpeed(int increment){
        this.xSpeed += increment;
    }

    public int getBulletSpeed(){
        return this.bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed){
        this.bulletSpeed = bulletSpeed;
    }

    public void incrementBulletSpeed(){
        this.bulletSpeed++;
    }

    public void incrementBulletSpeed(int increment){
        this.bulletSpeed += increment;
    }

    public int getBulletLag(){
        return this.bulletLag;
    }

    public void setBulletLag(int bulletLag){
        this.bulletLag = bulletLag;
    }

    public void decrementBulletLag(){
        this.bulletLag--;
    }

    public void decrementBulletLag(int decrement){
        this.bulletLag -= decrement;
    }

    public int getBulletLevel(){
        return this.bulletLevel;
    }

    public void setBulletLevel(int bulletLevel){
        this.bulletLevel = bulletLevel;
    }

    public int getBugLag(){
        return this.bugLag;
    }

    public void setBugLag(int lag){
        this.bugLag = lag;
    }

    public void decrementBugLag(){
        this.bugLag--;
    }

    public void decrementBugLag(int decrement){
        this.bugLag -= decrement;
    }
}
