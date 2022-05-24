package coc.game;

public class LevelUpPanel extends MenuPanel implements Runnable{
    
    public LevelUpPanel(COC coc){
        this.coc = coc;
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
        coc.add(this);
        coc.setComponentZOrder(getPanel(), 0);
        try{
            Thread.sleep(2000);
        }catch(Exception e){}
        
        coc.remove(this);
    }

    public void runLevelUp(){
        Thread levelThread = new Thread(this);
        levelThread.start();
    }
}
