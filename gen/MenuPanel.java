package gen;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel{
    protected BufferedImage BG;
    protected String path;
    protected String srcPath;

    protected void loadElements(String alt){
        setLayout(null);
        setOpaque(false);
        ImageLoader il = new ImageLoader(path, alt);
        BG = il.getBuffImage();
    }

    protected void autoSetIcons(GameButton button, String name){
        button.setIcons(
            srcPath+"src/normal/"+name+".png",
            srcPath+"src/hilite/h_"+name+".png",
            name.toUpperCase()
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
