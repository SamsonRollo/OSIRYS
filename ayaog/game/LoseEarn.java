package ayaog.game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import gen.GameMenuPanel;

public class LoseEarn extends GameMenuPanel{
    private Font font;
    
    public LoseEarn(AYAOG ayaog){
        this.game = ayaog;
        this.path = "ayaog/src/loseearn.png";
        this.srcPath = "ayaog/";
        font = new Font("sans_serif", Font.BOLD, 30);
        loadElements("loseearn");
        setBounds(ayaog.getWidth()/2-BG.getWidth()/2,
            ayaog.getHeight()/2-BG.getHeight()/2,
            389,
            150
        );
        Thread pause = new Thread(new Runnable() {
            public void run(){
                try{
                    Thread.sleep(1150);
                }catch(Exception e){};
                ayaog.getScore().setGameScore(ayaog.getLevel().getWinning(true));
                ayaog.resetGame();
                ayaog.remove(getPanel());
            }
        });
        pause.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setFont(font);
        g.setColor(Color.BLACK);
        String str = String.valueOf(game.getScore().getGameScore());
        g.drawString(str, 
            getWidth()/2-g.getFontMetrics().stringWidth(str)/2-5,
            getHeight()-30    
        );
    }
}
