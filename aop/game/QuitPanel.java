package aop.game;

import gen.GameButton;
import gen.GameMenuPanel;
import main.Screen;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuitPanel extends GameMenuPanel {

    public QuitPanel(AOP aop, boolean isPlay){
        this.game = aop;
        this.path = "aop/src/quitPanel.png";
        loadElements("quit");
        setBounds(0,0,700,500);

        GameButton no = new GameButton(366, 305, 84, 28);
        GameButton yes = new GameButton(240, 305, 84, 28);

        autoSetIcons(no, "no");
        autoSetIcons(yes, "yes");

        no.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ 
                aop.remove(getPanel());
                if(isPlay)
                    aop.playingStatus(true);
                aop.setAllBtnEnabled(true);
                aop.updateUI();
            }
        });

        yes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.getScore().incrementTotalScore(aop.getScore().getGameScore());
                aop.getScore().resetCurrentGameScore();
                aop.getScore().resetCurrentLevelScore();
                aop.getMainClass().showScreen(Screen.MENU.name());
            }
        });

        add(no);
        add(yes);
    }
}
