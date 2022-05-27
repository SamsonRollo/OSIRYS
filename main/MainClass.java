package main;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gen.ImageLoader;

public class MainClass extends JFrame{
    private String ICON_PATH = "src/img/icon.ico";
    private final int SUGGESTED_W = 700;
    private final int SUGGESTED_H = 500;
    private JPanel mainPanel;
    private OsirysGame game;
    private CardLayout card;

    public MainClass(){
    	setTrayIcon();
        getContentPane().setMinimumSize(new Dimension(SUGGESTED_W, SUGGESTED_H));
        getContentPane().setPreferredSize(new Dimension(SUGGESTED_W, SUGGESTED_H));
        setResizable(false);
        setUndecorated(true);
		getContentPane().setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadPanels();
        pack();
        setLocationRelativeTo(null);
    }

    public void loadPanels(){
        Osirys osirys = new Osirys(this);
        LoadingPanel loadPanel = new LoadingPanel();
        game = new DummyGame();
        card = new CardLayout();
        mainPanel = new JPanel(card);
        mainPanel.add(osirys, Screen.MENU.name());
        mainPanel.add(loadPanel, Screen.LOADING.name());
        mainPanel.add(game, Screen.GAME.name());
        mainPanel.setBounds(0,0, SUGGESTED_W, SUGGESTED_H);
        getContentPane().add(mainPanel);
    }

    private void setTrayIcon(){
		ImageLoader il = new ImageLoader(ICON_PATH, "icon");
        setIconImage(il.getBuffImage());
	}

    public void showScreen(String screen){
        card.show(mainPanel, screen);
    }

    public void setGame(OsirysGame game){
        mainPanel.remove(this.game);
        this.game = game;
        mainPanel.add(this.game, Screen.GAME.name());
    }

    public OsirysGame getGame(){
        return this.game;
    }

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