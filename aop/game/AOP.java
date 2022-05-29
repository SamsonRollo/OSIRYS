package aop.game;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import exception.ErrorReport;
import gen.ExcelLoader;
import gen.GameButton;
import gen.GameMenuPanel;
import gen.ImageLoader;
import gen.MusicType;
import gen.Score;
import gen.SpriteSheet;

import main.MainClass;
import main.OsirysGame;

public class AOP extends OsirysGame{
    private Upgrade upgrade;
    private final int MENU_LOC_X = 16;
    private final int MENU_LOC_Y = 85;
    private final int MENU_LOC_MUL = 32;
    private BufferedImage ANGER_IMG, CURR_ANGER, INIT_IMG;
    private ArrayList<Process> processes;
    private ArrayList<Processor> processors;
    private Rectangle[] dropPoints;
    private boolean[] hasProcessor;
    private int[] processLane;
    private boolean playBoolean = false;
    private SpriteSheet procBody;
    private SpriteSheet procTail;
    private SpriteSheet procStarve;
    private GameButton playBut, pauseBut;
    private Font font;
    private PowerUp powUp = null;

    public AOP(MainClass mainClass, Score score){
        this.mainClass = mainClass;
        this.score = score;
        setCode("aop");
        setProperties();
    }

    public void loadGame(ExcelLoader loader){
        loadElements();
        loadGenerator(loader);
        if(generator.isQuestionsNull())
            new ErrorReport(getMainClass(), 
                "Excel was not imported. No bonus for you.",
                "No Bonus");
        loadSoundManager();
        loadSounds();
        sManager.loopBG();
    }

    private void loadSounds(){
        sManager.addMusic("aop", MusicType.BG);
        sManager.addMusic("aop", MusicType.GAMEOVER);
        sManager.addMusic("aop", MusicType.POWERUP);
    }

    public void loadElements(){
        loadBackground();
        ImageLoader il = new ImageLoader("aop/src/anger.png", "anger");
        ANGER_IMG = il.getBuffImage();
        il = null;

        INIT_IMG = null;

        font = new Font("sans_serif", Font.BOLD, 18);
        upgrade = new Upgrade(1, 5, 1000, 1);
        procBody = new SpriteSheet("aop/src/pbodysprite.png", 55, 44);
        procTail = new SpriteSheet("aop/src/ptailsprite.png", 55, 44);
        procStarve = new SpriteSheet("aop/src/starvesprite.png", 44, 44);

        updateAngerIMG();

        processes = new ArrayList<Process>();
        processors = new ArrayList<Processor>();
        hasProcessor = new boolean[7];
        dropPoints = new Rectangle[7];
        processLane = new int[7];

        processors.add(new Processor(this, upgrade, 135, 88));
        hasProcessor[0] = true;

        for(int i=0, mult=56; i<7; i++)
            dropPoints[i] = new Rectangle(117, 85+mult*i, 81, 53);

        playBut = new GameButton(MENU_LOC_X, MENU_LOC_Y, 84, 28);
        pauseBut = new GameButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL, 84, 28);
        GameButton upgradeBut = new GameButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*2, 84, 28);
        GameButton helpBut = new GameButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*3, 84, 28);
        GameButton quitBut = new GameButton(MENU_LOC_X, MENU_LOC_Y+MENU_LOC_MUL*4, 84, 28);

        autoSetIcons(playBut, "play");
        autoSetIcons(pauseBut, "pause");
        autoSetIcons(upgradeBut, "upgrade");
        autoSetIcons(helpBut, "help");
        autoSetIcons(quitBut, "quit");
        
        playBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                loadInitImage();
                if(!isNewGame()){
                    setNewGame(false);
                    sManager.play(MusicType.ANY);
                }
                playingStatus(true);
            }
        });

        pauseBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playingStatus(false);
                PausePanel ppanel = new PausePanel(getAOP());
                addFloater(ppanel);
            }
        });

        upgradeBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean playBol = isPlay();
                setPlay(false);
                UpgradePanel ugPanel = new UpgradePanel(getAOP(), upgrade, font, playBol);
                addFloater(ugPanel);
            }
        });

        helpBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean playBol = isPlay();
                setPlay(false);
                HelpPanel hePanel = new HelpPanel(getAOP(), playBol);
                addFloater(hePanel);
            }
        });

        quitBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boolean playBol = isPlay();
                setPlay(false);
                QuitPanel qPanel = new QuitPanel(getAOP(), playBol);
                addFloater(qPanel);
            }
        });

        pauseBut.setEnabled(false);

        add(processors.get(0));
        add(playBut);
        add(pauseBut);
        add(upgradeBut);
        add(helpBut);
        add(quitBut);       
    }

    public void playingStatus(boolean status){
        setPlay(status);
        playBut.setEnabled(!status);
        pauseBut.setEnabled(status);
        if(status){
            producePowerUps();
            for(Processor p : processors)
                p.produceBullets();
            produceProcesses();
        }
    }

    public void setAllBtnEnabled(boolean status){
        for(Component c : getAOP().getComponents())
            if(c instanceof GameButton)
                ((GameButton)c).setEnabled(status);
        if(status && isPlay())
            playBut.setEnabled(false);
        else if(status && !isPlay())
            pauseBut.setEnabled(false);
    }

    public boolean isPlay(){
        return this.playBoolean;
    }

    public void setPlay(boolean playBoolean){
        this.playBoolean = playBoolean;
    }

    private void produceProcesses(){
        Thread processThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int accumLagProc = upgrade.getProcessLag()-100;
                while(isPlay()){
                    updateProcess();
                    if(accumLagProc >= upgrade.getProcessLag()){
                        createProcesses();
                        accumLagProc=0;
                        if(INIT_IMG!=null)
                            INIT_IMG = null;
                    }
                    removeProcess();
                    updateUI();
                    accumLagProc += upgrade.getSpeed();

                    try{
                        Thread.sleep(80);
                    } catch (InterruptedException e) {}
                }
            }
        });  
        processThread.start(); 
    }

    public void updateProcess(){
        for(Process p : processes)
            p.update();
    }

    public void createProcesses(){
        int spawnX = 640;
        int idx = new Random().nextInt(7);
        Rectangle lane = dropPoints[idx];
        
        if(!intersectProcess((int)lane.getY()+3)){
            Process p = new Process(getAOP(), upgrade.getLevel(), spawnX, (int)lane.getY()+3, 44);
            processLane[idx]++;
            processes.add(p);
            add(p);
        }
    }

    public boolean intersectProcess(int spawnY){
        for(Process p : processes)
            if(p.getRectangle().contains(640, spawnY))
                return true;
        return false;
    }

    public void removeProcess(){
        for (Iterator<Process> iterator = processes.iterator(); iterator.hasNext();) {
            Process process = iterator.next();
            if(!process.isAlive()){
                iterator.remove();
                processLane[process.getLane()]--;
                remove(process);
                process = null;
            }
        }
    }

    public void setAllProcessDead(){
        for(Process p : processes)
            p.setAlive(false);
    }

    public void removeProcessor(){
        for (Iterator<Processor> iterator = processors.iterator(); iterator.hasNext();) {
            Processor processor = iterator.next();
            iterator.remove();
            remove(processor);
            processor = null;
        }
    }

    public void setHasProcessorAt(int oldY, int newY){
        for(int i=0; i<dropPoints.length; i++){
            if((int)dropPoints[i].getY()+3 == oldY){
                hasProcessor[i] = false;
            }
            if((int)dropPoints[i].getY()+3 == newY){
                hasProcessor[i] = true;
            }
        }
    }

    public void processHit(Bullet b){
        for(Process p : processes){
            if(b.getRectangle().intersects(p.getRectangle())){
                b.setAlive(false);
                p.decrementBurstTime(upgrade.getBulletLevel()*3);
                if(!p.isAlive()){
                    score.incrementGameScore(p.getProcessScore());
                    upgrade.incrementToken(p.getProcessScore());
                    updateLevel();
                }
                return;
            }
        }
    }

    public void producePowerUps(){
        Thread powerupThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int lagctr = 0;
                int powerupLag = (new Random().nextInt(749 + 1 - 92)+92);
                while(isPlay()){
                    if(powUp!=null)
                        powUp.updatePowerUp();
                    if(lagctr >= powerupLag && powUp==null){
                        powUp = new PowerUp(getAOP());
                        getAOP().add(powUp);
                        getAOP().setComponentZOrder(powUp, 0);
                        lagctr=0;
                        powerupLag = (new Random().nextInt(749 + 1 - 102)+102);
                    }
                    if(powUp!=null && !powUp.isAlive()){
                        getAOP().remove(powUp);
                        powUp = null;
                        getAOP().updateUI();
                    }
                    lagctr++;
                    try{
                        Thread.sleep(90);
                    } catch (InterruptedException e) {}
                }
            }
        });  
        powerupThread.start(); 
    }
    
    public void updateLevel(){
        this.upgrade.updateLevel(this.score.getGameScore());
    }

    public void updateAngerIMG(){
        if(upgrade.getStarvationCount()<ANGER_IMG.getHeight())
            CURR_ANGER = ANGER_IMG.getSubimage(0, ANGER_IMG.getHeight()-upgrade.getStarvationCount()-1, ANGER_IMG.getWidth(), upgrade.getStarvationCount()+1);
        else
            setGameOver(); 
    }

    public void loadInitImage(){
        ImageLoader il = new ImageLoader("aop/src/proccoming.png", "proc");
        INIT_IMG = il.getBuffImage();
    }

    public void setGameOver(){
        if(!hasOverNotify()){
            setOverNotify(true);
            setPlay(false);
            GameOverPanel goPanel = new GameOverPanel(getAOP(), score, font);
            addFloater(goPanel);
        }
    }

    public void revertChanges(boolean status){
        if(status)
            playingStatus(true);
        setAllBtnEnabled(true);
    }

    public void resetProcessLane(){
        this.processLane = new int[7];
    }

    public int[] getProcessesLane(){
        return this.processLane;
    }

    public int getSpeed(){
        return this.upgrade.getSpeed();
    }

    public void incrementSpeed(int speed){
        this.upgrade.incrementSpeed(speed);
    }

    public SpriteSheet getBodySprite(){
        return this.procBody;
    }

    public SpriteSheet getTailSprite(){
        return this.procTail;
    }

    public SpriteSheet getStarveSprite(){
        return this.procStarve;
    }

    public Rectangle[] getDropPoints(){
        return this.dropPoints;
    }

    public boolean[] getHasProcessor(){
        return this.hasProcessor;
    }

    public ArrayList<Process> getProcesses(){
        return this.processes;
    }

    public void addProcessor(Processor cpu){
        processors.add(cpu);
        add(cpu);
    }

    public Upgrade getUpgrade(){
        return this.upgrade;
    }

    public AOP getAOP(){
        return this;
    }

    public void reportError(String message, String title){
        new ErrorReport(mainClass, message, title);
    }

    public void addFloater(GameMenuPanel panel){
        setAllBtnEnabled(false);
        getAOP().add(panel);
        getAOP().setComponentZOrder(panel, 0);
        getAOP().updateUI();
    }

    public void resetGame(){
        if(powUp!=null)
            remove(powUp);
        powUp = null;
        setAllProcessDead();
        removeProcess();
        for(Processor p : processors){
            p.setAllBulletsDead();
            p.removeBullet();
        }
        removeProcessor();
        score.incrementTotalScore(score.getGameScore()+score.getBonusScore());
        score.resetCurrentGameScore();
        score.resetCurrentBonusScore();
        upgrade = new Upgrade(1, 5, 1000, 1);
        processes = new ArrayList<Process>();
        processors = new ArrayList<Processor>();
        hasProcessor = new boolean[7];
        processLane =  new int[7];
        
        updateAngerIMG();    

        processors.add(new Processor(this, upgrade, 135, 88));
        hasProcessor[0] = true;
        add(processors.get(0));
        setPlay(false);
        playBut.setEnabled(true);
        pauseBut.setEnabled(false);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(CURR_ANGER, 31, 284+ANGER_IMG.getHeight()-CURR_ANGER.getHeight(), null); 

        if(INIT_IMG!=null)
            g.drawImage(INIT_IMG, 267, 151, null);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(String.valueOf(score.getGameScore()+score.getBonusScore()), 583, 53);
    }
}
