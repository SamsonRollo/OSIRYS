package main;

import gen.ImageLoader;
import gen.MenuPanel;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Graphics;

public class LoadingPanel extends MenuPanel{
    private BufferedImage loadingText;
    private int ltXpos;
    
    public LoadingPanel(){
        this.path = "src/img/loadingscreen.png";
        this.srcPath = "";
        loadElements("loading");
        setBounds(0,0,700,500);

        ImageLoader il = new ImageLoader("src/img/loadingtext.png", "text");
        loadingText = il.getBuffImage();

        ltXpos = getWidth()/2-loadingText.getWidth()/2;

        String gifPath = "src/img/loading.gif";
          
        if(il.loadAsURL(gifPath)){
            try{
                ImageIcon imageIcon = new ImageIcon(il.getUrl());
                JLabel animation = new JLabel(imageIcon);
                animation.setBounds(ltXpos-80, 240, 76, 76);
                add(animation);
            }catch(Exception e){e.printStackTrace();};
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG, 0, 0, null);
        g.drawImage(loadingText, ltXpos, 245, null);
    }
}
