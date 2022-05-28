package coc.game;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gen.GameButton;
import gen.GameMenuPanel;
import main.Screen;

public class QuitPanel extends GameMenuPanel {
    
    public QuitPanel(COC coc, boolean curPlay){
        this.game = coc;
        this.path = "coc/src/gamequit.png";
        loadElements("quit");

        GameButton yes = new GameButton((int)Math.floor(Double.valueOf(getWidth())/4-43), getHeight()-36 , 87, 30);
        GameButton no = new GameButton((int)Math.floor(Double.valueOf(getWidth())/4*3-43), getHeight()-36 , 87, 30);

        autoSetIcons(yes, "yes");
        autoSetIcons(no, "no");
        
        yes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                coc.getScore().incrementTotalScore(coc.getScore().getGameScore());
                coc.getScore().resetCurrentGameScore();
                coc.getScore().resetCurrentLevelScore();
                coc.getSoundManager().stopAll();
                coc.getMainClass().showScreen(Screen.MENU.name());
            }
        });

        no.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                coc.remove(getPanel());
                coc.setButtonsEnabled(true);
                if(!coc.isNewGame()){
                    coc.interactPlayBut(curPlay, true);
                    if(!curPlay)
                        coc.showPause();
                }
                coc.updateUI();
            }
        });

        add(yes);
        add(no);
    }

}
