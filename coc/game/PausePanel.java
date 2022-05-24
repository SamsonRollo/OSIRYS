package coc.game;

public class PausePanel extends MenuPanel {
    
    public PausePanel(COC coc){
        this.coc = coc;
        this.path = "coc/src/gamepause.png";
        loadElements("pause");
    }
}
