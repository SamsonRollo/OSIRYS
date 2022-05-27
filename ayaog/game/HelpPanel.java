package ayaog.game;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Graphics;

import gen.GameButton;
import gen.GameMenuPanel;

public class HelpPanel extends GameMenuPanel {

    public HelpPanel(AYAOG ayaog){
        this.game = ayaog;
        this.path = "ayaog/src/helpContent.png";
        loadElements("content");
        setBounds(
            ayaog.getWidth()/2-BG.getWidth()/2,
            ayaog.getHeight()/2-BG.getHeight()/2,
            BG.getWidth(), //chnage based on the size on the screen
            BG.getHeight() //change
        );
        GameButton okBtn = new GameButton(getWidth()/2-45, getHeight()-45, 90, 28);

        autoSetIcons(okBtn, "save"); //change to ok icon
    
        okBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ayaog.remove(getPanel());
                ayaog.updateUI();
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

        add(okBtn);
        add(jsp);
    }

    @Override
    public void paintComponent(Graphics g){}
}
