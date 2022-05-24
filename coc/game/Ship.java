
package coc.game;

import java.awt.event.MouseMotionAdapter;
import java.util.Vector;
import java.util.Iterator;
import java.util.Random;

import gen.Score;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Ship extends GameObject implements Runnable{
    private COC coc;
    private int LEFT_BOUND;
    private int RIGHT_BOUND;
    private int speed = 21;
    private Vector<Bullet> bullets;
    private Score score;
    private int threadTimer = 20;

    public Ship(COC coc, Score score, int x, int y){
        this.coc = coc;
        this.LEFT_BOUND = coc.getLeftBound();
        this.RIGHT_BOUND = coc.getRightBound();
        this.score = score;
        IMG_PATH = "coc/src/weapon.png";
        setGameObject("bug", x, y);
        setInitPoint(x, y);

        bullets = new Vector<Bullet>();

        addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                if(coc.isPlay() && coc.isEnter()){
                    if(e.getX()+getInitX()>LEFT_BOUND && e.getX()+getInitX()<RIGHT_BOUND){
                        setCurrentPoint(getInitX()+e.getX()-1, getInitY());
                    }
                }       
            }
        });

        addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                if(coc.isPlay() && coc.isEnter())
                    updateShipPosition();
            }
        });
    }

    @Override
    public void run(){
        int accumLag = coc.getLevel().getBulletLag();
        int bulletUpgradeTimer = 0;
        int powerupctr = 0;
        int powerupLag = (new Random().nextInt((int)Math.floor(30000/threadTimer) + 1 - (int)Math.floor(5000/threadTimer))+(int)Math.floor(5000/threadTimer));
        while(coc.isPlay()){

            if(powerupctr>=powerupLag){
                PowerUp pu = new PowerUp(coc);
                pu.runPowerUp();
                powerupctr = 0;
                powerupLag = (new Random().nextInt((int)Math.floor(30000/threadTimer) + 1 - (int)Math.floor(5000/threadTimer))+(int)Math.floor(5000/threadTimer));
        
            }

            updateBullets(coc.getLevel().getBulletSpeed());

            if(accumLag>=coc.getLevel().getBulletLag()){
                createBullet();
                accumLag = 0;
            }
            if(coc.getLevel().getBulletLevel()==2){
                bulletUpgradeTimer+=getThreadTimer();
                if(bulletUpgradeTimer>=5000){
                    coc.getLevel().setBulletLevel(1);
                    bulletUpgradeTimer = 0;
                }
            }

            removeBullets();
            accumLag++;
            powerupctr++;

            coc.updateUI();

            try{
                Thread.sleep(getThreadTimer());
            }catch(Exception e){};
        }
    }

    public void decreaseThreadTimer(){
        if(this.threadTimer>10)
            this.threadTimer-=2;
    }

    public int getThreadTimer(){
        return this.threadTimer;
    }

    public void updateBullets(int bulletSpeed){
        for(Bullet b : bullets){
            b.updateBullet(bulletSpeed);
            bugHit(b);
        }
    }

    public void createBullet(){
        Bullet b = new Bullet(coc.getLevel().getBulletLevel(), getX()+13, 450);
        bullets.add(b);
        coc.add(b);
    }

    public void removeBullets(){
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if(!bullet.isAlive()){
                iterator.remove();
                coc.remove(bullet);
                bullet = null;
            }
        }
    }

    public void bugHit(Bullet bullet){
        try{
            for(Bug bug : coc.getDen().getBugs()){
                if(bullet.getRectangle().intersects(bug.getRectangle())){
                    bullet.decrementLife();
                    bug.setAlive(false);
                    score.incrementLevelScore(2);
                    score.incrementGameScore(2);
                    coc.updateScoreIMG();
                    return;
                }
            }
        }catch(Exception e){};
    }

    public void killAllBullets(){
        for(Bullet b : bullets)
            b.setAlive(false);
        removeBullets();
    }

    void updateShipPosition(){;
        setInitPoint(getX(), getY());
        setLocation(getX(), getY());
    }

    public void moveLeft(){
        if(getX()-speed>LEFT_BOUND)
            keyBoardMove(getX()-speed);
        else
            keyBoardMove(LEFT_BOUND);
    }

    public void moveRight(){
        if(getX()+speed<RIGHT_BOUND-5)
            keyBoardMove(getX()+speed);
        else
            keyBoardMove(RIGHT_BOUND-5);
    }

    private void keyBoardMove(int delX){
        setInitPoint(delX, getY());
        setCurrentPoint(delX, getY());
        setLocation(delX, getY());
    }

    @Override
    protected boolean calculateAlive() {
        return true;
    }
    
}
