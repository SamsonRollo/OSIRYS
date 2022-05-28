package main;

import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Dimension;
import java.awt.Graphics;

import gen.ExcelLoader;
import gen.GameButton;
import gen.ImageLoader;
import gen.QuestionGenerator;
import gen.Score;

public abstract class OsirysGame extends JPanel{
    private String excelPath = "src/file/questions.xlsx";
    protected String code;
    protected Score score;
    protected MainClass mainClass;
    protected BufferedImage BG_IMG;
    protected QuestionGenerator generator;
    protected boolean hasFloater = false;
    protected File excelFile = null;

    public String getCode(){
        return this.code;
    }

    protected void loadGenerator(){
        ExcelLoader el;
        if(excelFile!=null)
            el = new ExcelLoader(excelFile);    

        el = new ExcelLoader(getExcelPath());

        try{
            el.loadExcel();
        }catch(Exception e){
            new exception.ErrorReport(getMainClass(), e.getMessage(), "Import Error");
        }
        generator = new QuestionGenerator(el.getQuestions());
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

    protected String getExcelPath(){
        return this.excelPath;
    }

    public Score getScore(){
        return this.score;
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

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG,0,0,null);
    }

    abstract protected void loadGame();
    public abstract void revertChanges(boolean status);
}