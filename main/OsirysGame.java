package main;

import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

import gen.ExcelLoader;
import gen.GameButton;
import gen.ImageLoader;
import gen.QuestionGenerator;
import gen.Score;
import gen.SoundManager;

public abstract class OsirysGame extends JPanel{
    protected String code;
    protected Score score;
    protected MainClass mainClass;
    protected BufferedImage BG_IMG;
    protected QuestionGenerator generator;
    protected boolean hasFloater = false;
    protected SoundManager sManager;
    protected boolean hasAlreadyWarn = false;
    protected boolean hasOverNotify = false;
    protected boolean newGame = true;

    public String getCode(){
        return this.code;
    }

    protected void loadGenerator(ExcelLoader loader){
        generator = new QuestionGenerator(loader.getQuestions());
    }

    protected void loadSoundManager(){
        sManager = new SoundManager(this);
    }

    public void setProperties(){
        setPreferredSize(new Dimension(700,500));
        setLayout(null);
        setBounds(0,0,700,500);
    }

    public void loadBackground(){
        ImageLoader il = new ImageLoader(getCode()+"/src/panel.png", "bg");
        BG_IMG = il.getBuffImage();
        il = null;
    }

    protected void setCode(String code){
        this.code = code;
    }

    public Score getScore(){
        return this.score;
    }

    public void setWarn(boolean warn){
        this.hasAlreadyWarn = warn;
    }

    public boolean hasAlreadyWarn(){
        return this.hasAlreadyWarn;
    }

    public boolean hasOverNotify(){
        return this.hasOverNotify;
    }

    public void setOverNotify(boolean stat){
        this.hasOverNotify = stat;
    }

    public boolean isNewGame(){
        return this.newGame;
    }

    public void setNewGame(boolean stat){
        this.newGame = stat;
    }

    public QuestionGenerator getGenerator(){
        return this.generator;
    }

    public void setHasFloater(boolean stat){
        this.hasFloater = stat;
    }

    public boolean getHasFloater(){
        return this.hasFloater;
    }

    protected void autoSetIcons(GameButton button, String name){
        button.setIcons(
            getCode()+"/src/normal/"+name+".png",
            getCode()+"/src/hilite/h_"+name+".png",
            name.toUpperCase()
        );
    }

    public MainClass getMainClass(){
        return this.mainClass;
    }

    public OsirysGame getGame(){
        return this;
    }

    public SoundManager getSoundManager(){
        return this.sManager;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG,0,0,null);
    }

    abstract protected void loadGame(ExcelLoader loader);
    public abstract void revertChanges(boolean status);
}