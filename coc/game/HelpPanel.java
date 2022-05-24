package coc.game;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Graphics;

import gen.GameButton;

public class HelpPanel extends MenuPanel {

    public HelpPanel(COC coc, boolean curPlay){
        this.coc = coc;
        this.path = "coc/src/helpContent.png";
        loadElements("content");
        setBounds(getX(), 85, 397, 400);

        GameButton back = new GameButton((int)Math.floor(Double.valueOf(getWidth())/2-43), getHeight()-35 , 87, 30);

        autoSetIcons(back, "back");

        back.addActionListener(new ActionListener(){
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

        JPanel contentPanel = new JPanel(null){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(BG, 0, 0, null);
            }
        };

        contentPanel.setSize(BG.getWidth(), BG.getHeight());
        contentPanel.setPreferredSize(new Dimension(BG.getWidth(), BG.getHeight()));

        JScrollPane jsp = new JScrollPane(contentPanel);
        jsp.setBounds(0,0, getWidth(), getHeight()-40);
        jsp.setBorder(BorderFactory.createEmptyBorder());

        add(back);
        add(jsp);
    }

    @Override
    public void paintComponent(Graphics g){}
}
