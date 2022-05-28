package aop.game;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gen.GameButton;
import gen.GameMenuPanel;
import gen.ImageLoader;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HelpPanel extends GameMenuPanel {
    private BufferedImage CONTENT_IMG;

    public HelpPanel(AOP aop, boolean isPlay){
        this.game = aop;
        this.path = "aop/src/helpPanelOuter.png";
        loadElements("bgHelp");
        setBounds(0,0,700,500);

        ImageLoader il = new ImageLoader("aop/src/helpContent.png", "content");
        CONTENT_IMG = il.getBuffImage();

        GameButton back = new GameButton(308, 413, 84, 28);

        autoSetIcons(back, "back");

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.remove(getPanel());
                if(isPlay)
                    aop.playingStatus(true);
                aop.setAllBtnEnabled(true);
                aop.updateUI();    
            }
        });

        JPanel contentPanel = new JPanel(null){
            @Override
            public void paintComponent(Graphics g){
                g.drawImage(CONTENT_IMG, 0, 0, null);
            }
        };
        contentPanel.setSize(CONTENT_IMG.getWidth(), CONTENT_IMG.getHeight());
        contentPanel.setPreferredSize(new Dimension(CONTENT_IMG.getWidth(), CONTENT_IMG.getHeight()));

        JScrollPane jsp = new JScrollPane(contentPanel);
        jsp.setBounds(131, 25, 439, 375);
        jsp.setBorder(BorderFactory.createEmptyBorder());
        
        add(jsp);
        add(back);
    }
}
