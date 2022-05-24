package gen;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameButton extends JButton{
 
    public GameButton(){
        setBorders();
    }

    public GameButton(int x, int y, int w, int h){
        setBorders();
        setBounds(x,y,w,h); 
    }

    public void setBorders(){
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public void setIcons(String selectAddress, String unselectAddress, String alt){
        ImageLoader imgLoader = new ImageLoader(selectAddress, alt);
        ImageIcon icon;
        try{
            icon = new ImageIcon(imgLoader.getBuffImage());
            setIcon(icon);
        }catch(Exception e){setText(alt);}

        imgLoader.reloadImage(unselectAddress, alt);
        try{
            icon = new ImageIcon(imgLoader.getBuffImage());
            setRolloverIcon(icon);
        }catch(Exception e){};

        setActionCommand(alt);
    }

    public JButton getButton(){
        return this;
    }
}
