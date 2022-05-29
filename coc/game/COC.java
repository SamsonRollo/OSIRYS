package coc.game;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import exception.ErrorReport;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;

import gen.ExcelLoader;
import gen.GameButton;
import gen.ImageLoader;
import gen.MusicType;
import gen.Score;
import gen.GameMenuPanel;
import main.MainClass;
import main.OsirysGame;

public class COC extends OsirysGame{
    public final String LEFT = "LEFT";
    public final String RIGHT = "RIGHT"; 
    public final String ENTER = "ENTER";
    private int LEFT_BOUND = 70;
    private int RIGHT_BOUND = 590;
    private BufferedImage TOT_SC_IMG, CUR_SC_IMG=null;
    private Level level;
    private Font font;
    private Ship ship;
    private BugDen den;
    private GameButton playBut, helpBut, quitBut;
    private boolean play = false, newGame = true, isEnter=false;
    private boolean overNotif = false, levelNotif = false;
    public Middler middler = null;
    public PausePanel pausePanel;

    public COC(MainClass mainClass, Score score){
        this.mainClass = mainClass;
        this.score = score;
        setCode("coc");
        setProperties();
    }

    @Override
    protected void loadGame(ExcelLoader loader){
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
        sManager.addMusic("coc", MusicType.BG);
        sManager.addMusic("coc", MusicType.LEVEL);
        sManager.addMusic("coc", MusicType.GAMEOVER);
        sManager.addMusic("coc", MusicType.POWERUP);
    }

    public void loadElements(){
        loadBackground();
        ImageLoader il = new ImageLoader("coc/src/progressbar.png", "progress");
        TOT_SC_IMG = il.getBuffImage();

        font = new Font("sans_serif", Font.BOLD, 21);
        middler = new Middler(getCOC());
        pausePanel = new PausePanel(getCOC());

        int butX= 394;
        int xMult = 102;
        playBut = new GameButton(butX, 11, 87, 30);
        helpBut = new GameButton(butX+xMult, 11, 87, 30);
        quitBut = new GameButton(butX+xMult*2, 11, 87, 30);

        autoSetIcons(playBut, "play");
        autoSetIcons(helpBut, "help");
        autoSetIcons(quitBut, "quit");

        playBut.setName("play");
        playBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playingStatus(!isPlay(), false);
            }
        });

        helpBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setButtonsEnabled(false);
                boolean curPlay = isPlay();
                setPlay(false);
                removeFloater();
                HelpPanel hp = new HelpPanel(getCOC(), curPlay);
                addFloater(hp);
            }
        });

        quitBut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setButtonsEnabled(false);
                boolean curPlay = isPlay();
                setPlay(false);
                removeFloater();
                QuitPanel qp = new QuitPanel(getCOC(), curPlay);
                addFloater(qp);
            }
        });

        setKeyBindings();

        add(playBut);
        add(helpBut);
        add(quitBut);
    }

    public void playingStatus(boolean status, boolean fromMenu){
        interactPlayBut(status, fromMenu);

        if(newGame){
            level = new Level();
            ship = new Ship(getCOC(), score, 330, 450);
            den = new BugDen(getCOC(), level, 4);
            getCOC().add(ship);
            updateScoreIMG();
            updateUI();
            newGame = false;
        }
    }

    public void gameThreads(){
        Thread shipThread = new Thread(ship);
        shipThread.start();
        createDenThread();
    }

    public void createDenThread(){
        Thread denThread = new Thread(den);
        denThread.start();
    }

    public void updateScoreIMG(){
        double percent = Double.valueOf(score.getLevelScore())/level.getTargetScore();
        int curLine = (int)Math.floor(percent*TOT_SC_IMG.getHeight());

        if(curLine >= TOT_SC_IMG.getHeight()){
            CUR_SC_IMG = TOT_SC_IMG;
            levelUp();
        }else
            CUR_SC_IMG = TOT_SC_IMG.getSubimage(0, TOT_SC_IMG.getHeight()-curLine-1, TOT_SC_IMG.getWidth(), curLine+1);
    }

    public void levelUp(){
        LevelUpPanel lp = new LevelUpPanel(getCOC());
        lp.runLevelUp();
    }

    public void notifyGameOver(){
        setOverNotif(true);
        setButtonsEnabled(false);
        setPlay(false);
        GameOverPanel gop = new GameOverPanel(getCOC());
        addFloater(gop);
    }

    public void showMiddler(){
        getCOC().remove(getPause());
        addFloater(getMiddler());
        bindENTER(ENTER);
    }

    public void removeMiddler(){
        getCOC().remove(middler);
        getCOC().updateUI();
    }

    public void showPause(){
        getCOC().remove(getMiddler());
        addFloater(getPause());
    }

    public void removePause(){
        getCOC().remove(pausePanel);
        getCOC().updateUI();
    }

    public void addFloater(GameMenuPanel panel){
        getCOC().add(panel);
        getCOC().setComponentZOrder(panel, 0);
        getCOC().updateUI();
    }

    public void removeFloater(){
        getCOC().remove(getMiddler());
        getCOC().remove(getPause());
        getCOC().updateUI();
    }

    public void cleanGame(){
        ship.killAllBullets();
        getCOC().remove(ship);
        ship = null;
        den.killAllBugs();
        score.incrementTotalScore(score.getGameScore()+score.getBonusScore());
        score.resetCurrentBonusScore();
        score.resetCurrentGameScore();
        score.resetCurrentLevelScore();
        updateScoreIMG();
        playBut.setName("play");
        autoSetIcons(playBut, "play");
        removeFloater();
        setOverNotif(false);
        newGame = true;
    }

    public void interactPlayBut(boolean status, boolean fromMenu){
        if(playBut.getName().equals("play")){
            if(!fromMenu){ 
                playBut.setName("pause");
                showMiddler();
            }else
                showPause();
        }else{
            if(!fromMenu){
                playBut.setName("play");
                showPause();
            }else
                showMiddler();
        }
        if(!fromMenu)
            autoSetIcons(playBut, playBut.getName());
        setPlay(status);
    }

    public void setButtonsEnabled(boolean enable){
        this.playBut.setEnabled(enable);
        this.helpBut.setEnabled(enable);
        this.quitBut.setEnabled(enable);
    }

    public void setKeyBindings(){
        ActionMap am = getActionMap();
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), LEFT);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), LEFT);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), RIGHT);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), RIGHT);
        
        am.put(LEFT, new KeyAction(LEFT));
        am.put(RIGHT, new KeyAction(RIGHT));
    }

    public void bindENTER(String command){
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), command);
        if(!command.equals("none")){
            ActionMap am = getActionMap();
            am.put(command, new KeyAction(command));
        }
    }

    private class KeyAction extends AbstractAction{
        private KeyAction(String action){
            putValue(ACTION_COMMAND_KEY, action);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(ship!=null && isPlay()){
                if(String.valueOf(e.getActionCommand()).equals(LEFT))
                    ship.moveLeft();
                if(String.valueOf(e.getActionCommand()).equals(RIGHT))
                    ship.moveRight();
                if((String.valueOf(e.getActionCommand()).equals(ENTER)) && den!=null){
                    gameThreads();
                    bindENTER("none");
                    removeMiddler();
                    setEnter(true);
                }
            }
        }

    }

    public void revertChanges(boolean status){
        setButtonsEnabled(true);
        if(!isNewGame()){
            interactPlayBut(status, true);
            if(!status)
                showPause();
        }
    }

    public Middler getMiddler(){
        return this.middler;
    }

    public PausePanel getPause(){
        return this.pausePanel;
    }

    public boolean isLevelNotif(){
        return this.levelNotif;
    }

    public void setLevelNotif(boolean notif){
        this.levelNotif = notif;
    }

    public boolean isOverNotif(){
        return this.overNotif;
    }

    public void setOverNotif(boolean notif){
        this.overNotif = notif;
    }

    public boolean isEnter(){
        return this.isEnter;
    }

    public void setEnter(boolean enter){
        this.isEnter = enter;
    }

    public boolean isPlay(){
        return this.play;
    }

    public void setPlay(boolean play){
        this.play = play;
        if(!play)
            setEnter(false);
    }

    public boolean isNewGame(){
        return this.newGame;
    }

    public Level getLevel(){
        return this.level;
    }

    public int getRightBound(){
        return this.RIGHT_BOUND;
    }

    public int getLeftBound(){
        return this.LEFT_BOUND;
    }

    public COC getCOC(){
        return this;
    }

    public BugDen getDen(){
        return this.den;
    }

    public Ship getShip(){
        return this.ship;
    }

    public Score getScore(){
        return this.score;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG, 0, 0, null);
        if(CUR_SC_IMG!=null)
            g.drawImage(CUR_SC_IMG, 656, 98+TOT_SC_IMG.getHeight()-CUR_SC_IMG.getHeight(), null); 

        g.setColor(Color.black);
        g.setFont(font);
        g.drawString(String.valueOf(score.getGameScore()+score.getBonusScore()), 44, 50);
    }
    
}
