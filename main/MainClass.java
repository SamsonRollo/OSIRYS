package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

//import aop.game.AOP;

public class MainClass extends JFrame{
    //private BufferedImage ICON_PATH = "src/tray_icon.png"
    private final int SUGGESTED_W = 700;
    private final int SUGGESTED_H = 500;

    public MainClass(){
    	//setTrayIcon();
        getContentPane().setMinimumSize(new Dimension(SUGGESTED_W, SUGGESTED_H));
        getContentPane().setPreferredSize(new Dimension(SUGGESTED_W, SUGGESTED_H));
        setResizable(false);
		getContentPane().setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadPanels();
        pack();
        setLocationRelativeTo(null);
    }

    public void loadPanels(){
        // AOP aop = new AOP(this);
        Osirys osirys = new Osirys(this);
        getContentPane().add(osirys);
    }

    // private void setTrayIcon(){
	// 	ImageLoader il = new ImageLoader(ICON_PATH, "icon");
    //     setIconImage(il.getBuffImage());
	// }

    public int suggestedW(){
        return this.SUGGESTED_W;
    }

    public int suggestedH(){
        return this.SUGGESTED_H;
    }

    public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new MainClass().setVisible(true);
            }
        });
    }
}