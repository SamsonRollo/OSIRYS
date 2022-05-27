package main;

import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

import gen.GameButton;
import gen.MenuPanel;

public class QuitPanel extends MenuPanel {
 
    public QuitPanel(Osirys osirys){
        this.path = "src/img/quitpanel.png";
        this.mid = "img/";
        loadElements("quit");
        setBounds(
            osirys.getWidth()/2-BG.getWidth()/2,
            osirys.getHeight()/2-BG.getHeight()/2,
            BG.getWidth(),
            BG.getHeight()
        );

        GameButton yes = new GameButton(getWidth()/3-36, getHeight()-35, 73, 25);
        GameButton no = new GameButton(getWidth()/3*2-36, getHeight()-35, 73, 25);

        autoSetIcons(yes, "yes");
        autoSetIcons(no, "no");

        yes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

        no.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                osirys.setOnFloater(false);
                osirys.setAllBtnEnable(true);
                osirys.remove(getPanel());
                osirys.updateUI();
            }
        });

        add(yes);
        add(no);
    }
}
