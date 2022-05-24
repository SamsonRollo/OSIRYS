package coc.game;

import javax.swing.JPanel;

import gen.GameButton;
import gen.ImageLoader;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class MenuPanel extends JPanel{
    protected COC coc;
    protected BufferedImage BG;
    protected String path;

    protected void loadElements(String alt){
        setLayout(null);
        setOpaque(false);
        ImageLoader il = new ImageLoader(path, alt);
        BG = il.getBuffImage();
        
        setBoundsAux();
    }

    protected void autoSetIcons(GameButton button, String name){
        button.setIcons(
            "coc/src/normal/"+name+".png",
            "coc/src/hilite/h_"+name+".png",
            name.toUpperCase()
        );
    }

    public void setBoundsAux(){
        setBounds((int)Math.floor(Double.valueOf(coc.getWidth())/2-Double.valueOf(BG.getWidth())/2),
            120,
            BG.getWidth(), 
            BG.getHeight()+40    
        );
    }

    protected JPanel getPanel(){
        return this;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG, 0,0,null);
    }
    
}
