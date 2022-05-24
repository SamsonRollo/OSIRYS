package main;

import javax.swing.JPanel;

import aop.game.AOP;
import gen.GameButton;
import gen.ImageLoader;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Osirys extends JPanel implements ActionListener{
    private MainClass mainClass;
    private BufferedImage BG_IMG, DESC_IMG;
    private String srcPath = "src/";

    public Osirys(MainClass mainClass){
        this.mainClass = mainClass;
        setPreferredSize(new Dimension(mainClass.suggestedW(), mainClass.suggestedH()));
        setLayout(null);
        setBounds(0,0,mainClass.suggestedW(), mainClass.suggestedH());
        loadElements();
    }

    public void loadElements(){
        ImageLoader il = new ImageLoader("src/panel.png", "bg");
        BG_IMG = il.getBuffImage();
        DESC_IMG = null;

        int butX = getWidth()-130, mulX = 31, y=10, w=28, h=28;

        GameButton settingBtn = new GameButton(butX, y, w, h);
        GameButton aboutBtn = new GameButton(butX+mulX, y, w, h);
        GameButton rankBtn = new GameButton(butX+mulX*2, y, w, h);
        GameButton exitBtn = new GameButton(butX+mulX*3, y, w, h);

        butX = (int)Math.floor(getWidth()/3-122); //62 is half, 15 add
        mulX = 185;
        y = 170;
        w = 125;
        h = 85;

        GameButton ayaogBtn = new GameButton(butX, y, w, h);
        GameButton aopBtn = new GameButton(butX+mulX, y, w, h);
        GameButton cocBtn = new GameButton(butX+mulX*2, y, w, h);

        autoSetButtonIcons(settingBtn, "settingBtn");
        autoSetButtonIcons(aboutBtn, "aboutBtn");
        autoSetButtonIcons(rankBtn, "rankBtn");
        autoSetButtonIcons(exitBtn, "exitBtn");
        autoSetButtonIcons(ayaogBtn, "ayaogBtn");
        autoSetButtonIcons(aopBtn, "aopBtn");
        autoSetButtonIcons(cocBtn, "cocBtn");

        settingBtn.addActionListener(this);
        aboutBtn.addActionListener(this);
        rankBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        ayaogBtn.addActionListener(this);
        aopBtn.addActionListener(this);
        cocBtn.addActionListener(this);

        ayaogBtn.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                addDESC("ayaog");
            }
            public void mouseExited(MouseEvent e){
                removeDESC();
            }
        });

        aopBtn.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                addDESC("aop");
            }
            public void mouseExited(MouseEvent e){
                removeDESC();
            }
        });

        cocBtn.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){
                addDESC("coc");
            }
            public void mouseExited(MouseEvent e){
                removeDESC();
            }
        });

        add(settingBtn);
        add(aboutBtn);
        add(rankBtn);
        add(exitBtn);
        add(ayaogBtn);
        add(aopBtn);
        add(cocBtn);
    }

    private void autoSetButtonIcons(GameButton button, String name){
        button.setIcons(
            srcPath+"normal/"+name+".png",
            srcPath+"hilite/h_"+name+".png",
            name.toUpperCase()
        );
    }

    public void addDESC(String game){
        ImageLoader il = new ImageLoader(srcPath+game+"desc.png", "description");
        DESC_IMG = il.getBuffImage();
        repaint();
    }

    public void removeDESC(){
        DESC_IMG = null;
        repaint();
    }

    public MainClass getMainClass(){
        return this.mainClass;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG,0,0,null);

        //if(DESC_IMG!=null)
            g.drawImage(DESC_IMG, getWidth()/2-227, 285, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("AYAOGBTN")){

        }else if(command.equals("AOPBTN")){
            AOP aop = new AOP(getMainClass());
        }else if(command.equals("COCBTN")){

        }else if(command.equals("SETTINGBTN")){

        }else if(command.equals("ABOUTBTN")){
            
        }else if(command.equals("RANKBTN")){
            
        }else if(command.equals("EXITBTN")){
            
        }
    }
}
