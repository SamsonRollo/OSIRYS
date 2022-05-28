package ayaog.game;

import gen.GameMenuPanel;
import gen.MusicType;
import main.Screen;

public class ComeBack extends GameMenuPanel{
    
    public ComeBack(AYAOG ayaog){
        this.game = ayaog;
        this.path = "ayaog/src/comeback.png";
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
                ayaog.getSoundManager().stop(MusicType.BG);
                ayaog.getMainClass().showScreen(Screen.MENU.name());
            }
        });
        pause.start();
    }
}
