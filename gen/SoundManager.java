package gen;

import main.OsirysGame;

public class SoundManager {
    private MusicPlayer bg;
    private MusicPlayer bullet;
    private MusicPlayer levelup;
    private MusicPlayer gameover;
    private MusicPlayer powerup;
    private MusicPlayer any;
    private OsirysGame game;

    public SoundManager(OsirysGame game){
        this.game= game;
        bg = null;
        bullet = null;
        levelup = null;
        gameover = null;
        powerup = null;
        any = null;
    }

    public void addMusic(String code, MusicType type){
        if(game.getMainClass().getOsirys().getSound()){
            if(type==MusicType.BG)
                bg = new MusicPlayer("src/sound/"+code+"bg.mp3");
            else if(type==MusicType.BULLET)
                bullet = new MusicPlayer("src/sound/bullet.mp3");
            else if(type==MusicType.LEVEL)
                levelup = new MusicPlayer("src/sound/level.mp3");
            else if(type==MusicType.GAMEOVER)
                gameover = new MusicPlayer("src/sound/gameover.mp3");
            else if(type==MusicType.POWERUP)
                powerup = new MusicPlayer("src/sound/powerup.mp3");
            else if(type==MusicType.ANY)
                any = new MusicPlayer(code);
        }
    }

    public void play(MusicType type){
        if(game.getMainClass().getOsirys().getSound()){
            if(type==MusicType.BULLET && bg!=null)
                bg.stop();
            else if(type==MusicType.BG && bg!=null)
                bullet.stop();
            else if(type==MusicType.LEVEL && bg!=null)
                levelup.play();
            else if(type==MusicType.GAMEOVER && bg!=null)
                gameover.play();
            else if(type==MusicType.POWERUP && bg!=null)
                powerup.play();
            else if(type==MusicType.ANY && bg!=null)
                any.play();
        }
    }

    public void stop(MusicType type){
        if(game.getMainClass().getOsirys().getSound()){
            if(type==MusicType.BG)
                bg.stop();
            else if(type==MusicType.BULLET)
                bullet.stop();
            else if(type==MusicType.LEVEL)
                levelup.stop();
            else if(type==MusicType.GAMEOVER)
                gameover.stop();
            else if(type==MusicType.POWERUP)
                powerup.stop();
            else if(type==MusicType.ANY)
                any.stop();
        }
    }

    public void loopBG(){
        Thread loopThread = new Thread(new Runnable() {
            public void run(){
                if(bg!=null)
                    bg.loop();
            }
        });
        loopThread.start();
    }

    public void stopAll(){
        if(game.getMainClass().getOsirys().getSound()){
            if(bg!=null)
                bg.stop();
            if(bullet!=null)
                bullet.stop();
            if(levelup!=null)
                levelup.stop();
            if(gameover!=null)
                gameover.stop();
            if(powerup!=null)
                powerup.stop();
            if(any!=null)
                any.stop();
        }
    }
}