package ayaog.game;

import gen.GameMenuPanel;
import java.awt.Graphics;

public class RewarPanel extends GameMenuPanel{
    
    public RewarPanel(AYAOG ayaog){
        this.game = ayaog;
        this.path = "ayaog/src/rewardpanel.png";
        this.srcPath = "ayaog/";
        loadElements("comeback");
        setBounds(ayaog.getWidth()/2-BG.getWidth()/2,
            ayaog.getHeight()/2-BG.getHeight()/2,
            389,
            150
        );
        Thread pause = new Thread(new Runnable() {
            public void run(){
                try{
                    Thread.sleep(1750);
                }catch(Exception e){};

                ayaog.remove(getPanel());
            }
        });
        pause.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
    }
}
