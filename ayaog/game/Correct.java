package ayaog.game;

import gen.GameMenuPanel;

public class Correct extends GameMenuPanel{
    
    public Correct(AYAOG ayaog){
        this.game = ayaog;
        this.path = "ayaog/src/correct.png";
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
                    Thread.sleep(1150);
                }catch(Exception e){};

                ayaog.remove(getPanel());
                CategoryPanel cp = new CategoryPanel(ayaog);
                ayaog.setFloater(cp);
            }
        });
        pause.start();
    }
}
