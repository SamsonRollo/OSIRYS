package coc.game;

public class Bullet extends GameObject{
    private int life = 1;

    public Bullet(int bulletLevel, int x, int y){
        this.life = bulletLevel;
        IMG_PATH = "coc/src/bullet"+bulletLevel+".png";
        setGameObject("bullet", x, y);
    }

    public void updateBullet(int bulletSpeed){
        moveCurrentPoint(getX(), getY()-bulletSpeed);
    }

    public void decrementLife(){
        if(this.life>0)
            this.life--;
        if(this.life==0)
            setAlive(false);
    }

    public int getLife(){
        return this.life;
    }

    @Override
    protected boolean calculateAlive() {
        if(getY() <= 85)
            return false;
        return true;
    }
}
