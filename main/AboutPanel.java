package main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gen.GameButton;
import gen.MenuPanel;

public class AboutPanel extends MenuPanel {
 
    public AboutPanel(Osirys osirys){
        this.path = "src/img/aboutpanel.png";
        this.mid = "img/";

        loadElements("about");
        setBounds(
            osirys.getWidth()/2-BG.getWidth()/2-5,
            115,
            BG.getWidth()+20,
            365
        );

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
        add(jsp);
    }

    @Override
    public void paintComponent(Graphics g){}
}
