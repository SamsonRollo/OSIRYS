package gen;

import main.OsirysGame;

public class CheckPanel extends GameMenuPanel{
    
    public CheckPanel(OsirysGame game, boolean correct){
        this.game = game;
        this.path = "src/img/right.png";

        if(!correct)
            this.path = "src/img/wrong.png";

        this.srcPath = "";
        loadElements("middler");

        setBounds(game.getWidth()/2-BG.getWidth()/2,
            game.getHeight()/2-BG.getHeight()/2,
            BG.getWidth(),
            BG.getHeight()
        );
        Thread pause = new Thread(new Runnable() {
            public void run(){
                try{
                    Thread.sleep(1950);
                }catch(Exception e){};

                game.remove(getPanel());
                game.updateUI();
            }
        });
        pause.start();
    }
}
