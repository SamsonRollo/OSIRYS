package main;

import javax.swing.JPanel;

import aop.game.AOP;
import ayaog.game.AYAOG;
import coc.game.COC;
import exception.ErrorReport;
import gen.ExcelLoader;
import gen.GameButton;
import gen.ImageLoader;
import gen.InternalStateSave;
import gen.MenuPanel;
import gen.Score;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Osirys extends JPanel implements ActionListener {
    private final String DEFAULT_EXCEL_PATH = "src/file/questions.xlsx";
    private String srcPath = "src/img/";
    private MainClass mainClass;
    private BufferedImage BG_IMG, DESC_IMG;
    private Score score;
    private InternalStateSave iss;
    private boolean soundOn = true;
    private boolean onFloater = false;
    private Font font;
    private ExcelLoader loader = null;

    public Osirys(MainClass mainClass) {
        this.mainClass = mainClass;
        setPreferredSize(new Dimension(mainClass.suggestedW(), mainClass.suggestedH()));
        setLayout(null);
        setBounds(0, 0, mainClass.suggestedW(), mainClass.suggestedH());
        loadElements();
        score = new Score();
        iss = new InternalStateSave();
    }

    public void loadElements() {
        ImageLoader il = new ImageLoader("src/img/panel.png", "bg");
        BG_IMG = il.getBuffImage();
        DESC_IMG = null;

        setLoader(null);

        int butX = getWidth() - 102, mulX = 31, y = 10, w = 28, h = 28;

        GameButton settingBtn = new GameButton(butX, y, w, h);
        GameButton aboutBtn = new GameButton(butX + mulX, y, w, h);
        GameButton exitBtn = new GameButton(butX + mulX * 2, y, w, h);

        font = new Font("sans_serif", Font.BOLD, 18);
        butX = (int) Math.floor(getWidth() / 3 - 122);
        mulX = 185;
        y = 170;
        w = 125;
        h = 85;

        GameButton ayaogBtn = new GameButton(butX, y, w, h);
        GameButton aopBtn = new GameButton(butX + mulX, y, w, h);
        GameButton cocBtn = new GameButton(butX + mulX * 2, y, w, h);

        autoSetButtonIcons(settingBtn, "settingBtn");
        autoSetButtonIcons(aboutBtn, "aboutBtn");
        autoSetButtonIcons(exitBtn, "exitBtn");
        autoSetButtonIcons(ayaogBtn, "ayaogBtn");
        autoSetButtonIcons(aopBtn, "aopBtn");
        autoSetButtonIcons(cocBtn, "cocBtn");

        settingBtn.addActionListener(this);
        aboutBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        ayaogBtn.addActionListener(this);
        aopBtn.addActionListener(this);
        cocBtn.addActionListener(this);

        ayaogBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!onFloater)
                    addDESC("ayaog");
            }

            public void mouseExited(MouseEvent e) {
                removeDESC();
            }
        });

        aopBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!onFloater)
                    addDESC("aop");
            }

            public void mouseExited(MouseEvent e) {
                removeDESC();
            }
        });

        cocBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!onFloater)
                    addDESC("coc");
            }

            public void mouseExited(MouseEvent e) {
                removeDESC();
            }
        });

        add(settingBtn);
        add(aboutBtn);
        add(exitBtn);
        add(ayaogBtn);
        add(aopBtn);
        add(cocBtn);
    }

    public void createGameThread(OsirysGame game) {
        Thread gameLoadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getMainClass().getGame().loadGame(getLoader());
                try {
                    Thread.sleep(400);
                } catch (Exception e) {
                }

                getMainClass().showScreen(Screen.GAME.name());
            }
        });
        gameLoadThread.start();
        initLoading();
    }

    private void initLoading() {
        getMainClass().showScreen(Screen.LOADING.name());
    }

    public void addDESC(String game) {
        ImageLoader il = new ImageLoader(srcPath + game + "desc.png", "description");
        DESC_IMG = il.getBuffImage();
        repaint();
    }

    public void removeDESC() {
        DESC_IMG = null;
        repaint();
    }

    public Osirys getOsirys() {
        return this;
    }

    public MainClass getMainClass() {
        return this.mainClass;
    }

    private void autoSetButtonIcons(GameButton button, String name) {
        button.setIcons(
                srcPath + "normal/" + name + ".png",
                srcPath + "hilite/h_" + name + ".png",
                name.toUpperCase());
    }

    public void setLoader(File file){
        String mes = "";
        ExcelLoader tempLoader = null;
        if(file==null){
            mes = "Cannot load default file. Please import new excel.";
            tempLoader = new ExcelLoader(DEFAULT_EXCEL_PATH);    
        }else{
            mes = "Selected file cannot be imported.";
            tempLoader = new ExcelLoader(file);
        }

        try{
            tempLoader.loadExcel();
            this.loader = tempLoader;
        }catch(Exception e){
           new ErrorReport(getMainClass(), mes, "Import Error");
        }
    }

    public ExcelLoader getLoader(){
        return this.loader;
    }

    public void setSoundOn(boolean on) {
        this.soundOn = on;
    }

    public boolean getSound() {
        return this.soundOn;
    }

    public void setOnFloater(boolean status) {
        this.onFloater = status;
    }

    public void setAllBtnEnable(boolean enable) {
        for (Component c : getOsirys().getComponents())
            if (c instanceof GameButton)
                ((GameButton) c).setEnabled(enable);
    }

    public void addFloater(MenuPanel panel) {
        onFloater = true;
        setAllBtnEnable(false);
        getOsirys().add(panel);
        getOsirys().setComponentZOrder(panel, 0);
        getOsirys().updateUI();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(BG_IMG, 0, 0, null);
        g.drawImage(DESC_IMG, getWidth() / 2 - 227, 285, null);
        g.setColor(Color.WHITE);
        g.fillRect(10, 10, 150, 30);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(score.getTotalScore()), 15, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("AYAOGBTN")) { // Are You An OS Geek?
            OsirysGame ayaog = new AYAOG(getMainClass(), score, iss);
            getMainClass().setGame(ayaog);
            createGameThread(ayaog);
        } else if (command.equalsIgnoreCase("AOPBTN")) { // Attack on Process
            OsirysGame aop = new AOP(getMainClass(), score);
            getMainClass().setGame(aop);
            createGameThread(aop);
        } else if (command.equalsIgnoreCase("COCBTN")) { // Crush 'em or Crash me
            OsirysGame coc = new COC(getMainClass(), score);
            getMainClass().setGame(coc);
            createGameThread(coc);
        } else if (command.equalsIgnoreCase("SETTINGBTN")) {
            SettingPanel sp = new SettingPanel(getOsirys());
            addFloater(sp);
        } else if (command.equalsIgnoreCase("ABOUTBTN")) {
            AboutPanel ap = new AboutPanel(getOsirys());
            addFloater(ap);
        } else if (command.equalsIgnoreCase("EXITBTN")) {
            QuitPanel qp = new QuitPanel(getOsirys());
            addFloater(qp);
        }
    }
}
