package coc.game;

import gen.GameMenuPanel;

public class PausePanel extends GameMenuPanel {
    
    public PausePanel(COC coc){
        this.game = coc;
        this.path = "coc/src/gamepause.png";
        loadElements("pause");
    }
}
