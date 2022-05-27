package coc.game;

import gen.GameMenuPanel;

public class LevelUpPanel extends GameMenuPanel implements Runnable{
    
    public LevelUpPanel(COC coc){
        this.game = coc;
        this.path = "coc/src/gamelevelup.png";
        loadElements("over");

        coc.getLevel().incrementLevel();
        coc.removeFloater();
        coc.getScore().resetCurrentLevelScore();
        coc.updateScoreIMG();
        coc.getShip().decreaseThreadTimer();
        coc.getDen().decreaseThreadTimer();
        coc.setLevelNotif(false);
    }


    @Override
    public void run() {
        game.add(this);
        game.setComponentZOrder(getPanel(), 0);
        try{
            Thread.sleep(2000);
        }catch(Exception e){}
        
        game.remove(this);
    }

    public void runLevelUp(){
        Thread levelThread = new Thread(this);
        levelThread.start();
    }
}
