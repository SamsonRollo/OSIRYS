package coc.game;

import gen.GameMenuPanel;

public class Middler extends GameMenuPanel {
    
    public Middler(COC coc){
        this.game = coc;
        this.path = "coc/src/middler.png";
        loadElements("middler");
    }

}
