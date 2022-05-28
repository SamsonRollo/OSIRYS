package aop.game;

import gen.GameButton;
import gen.GameMenuPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PausePanel extends GameMenuPanel {

    public PausePanel(AOP aop){
        this.game = aop;
        this.path = "aop/src/pausePanel.png";
        loadElements("pause");
        setBounds(0,0,700,500);

        GameButton resume = new GameButton(308, 296, 84, 28);

        autoSetIcons(resume, "resume");;

        resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.remove(getPanel());
                aop.playingStatus(true);
                aop.setAllBtnEnabled(true);
                aop.updateUI();
            }
        });

        add(resume);
    }
}
