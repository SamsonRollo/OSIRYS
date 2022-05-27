package main;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;

import gen.GameButton;
import gen.MenuPanel;

public class RankPanel extends MenuPanel {
 
    public RankPanel(Osirys osirys){
        this.path = "src/img/rankpanel.png";
        this.mid = "img/";
        loadElements("rank");
        setBounds(
            osirys.getWidth()/2-180,
            osirys.getHeight()/2-150,
            360,
            300
        );

        //12,70
        //size 340, 190

        //load from file

        GameButton back = new GameButton(getWidth()/2-36, getHeight()-35, 73, 25);

        autoSetIcons(back, "back");

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                osirys.setOnFloater(false);
                osirys.setAllBtnEnable(true);
                osirys.remove(getPanel());
                osirys.updateUI();
            }
        });

        add(back);
    }
}
