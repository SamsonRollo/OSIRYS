package ayaog.game;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gen.GameButton;
import gen.GameMenuPanel;
import gen.ImageLoader;

public class HelpPanel extends GameMenuPanel {

    public HelpPanel(AYAOG ayaog){
        this.game = ayaog;
        this.path = "ayaog/src/helppanel.png";
        loadElements("content");
        setBounds(
            0,
            0,
            BG.getWidth(),
            BG.getHeight() 
        );
        GameButton back = new GameButton(getWidth()/2-45, getHeight()-50, 90, 28);

        autoSetIcons(back, "back");
    
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ayaog.setAllBtnEnable(true);
                ayaog.remove(getPanel());
                ayaog.updateUI();
            }
        });
        
        ImageLoader il = new ImageLoader("ayaog/src/helpcontent.png", "content");
        BufferedImage CONTENT = il.getBuffImage();

        JPanel contentPanel = new JPanel(null){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(CONTENT, 0, 0, null);
            }
        };

        contentPanel.setSize(CONTENT.getWidth(), CONTENT.getHeight());
        contentPanel.setPreferredSize(new Dimension(CONTENT.getWidth(), CONTENT.getHeight()));

        JScrollPane jsp = new JScrollPane(contentPanel);
        jsp.setBounds(160,60, 381, 376);
        jsp.setBorder(BorderFactory.createEmptyBorder());

        add(back);
        add(jsp);
    }
}
