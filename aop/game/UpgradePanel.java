package aop.game;

import gen.GameButton;
import gen.GameMenuPanel;
import gen.ImageLoader;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpgradePanel extends GameMenuPanel {
    private Upgrade upgrade;
    private BufferedImage coreImage, bulletImage;
    private Font font;
    private AOP aop;
    private boolean isPlay;

    public UpgradePanel(AOP aop, Upgrade upgrade, Font font, boolean isPlay){
        this.aop = aop;
        this.game = aop;
        this.upgrade = upgrade;
        this.font = font;
        this.isPlay = isPlay;
        this.path = "aop/src/upgradePanel.png";
        loadElements("upgrade");
        setBounds(0,0,700,500);

        loadAdditionalElements();
    }

    public void loadAdditionalElements(){
        ImageLoader il = new ImageLoader("aop/src/cpu.png", "cpu");
        coreImage = il.getBuffImage();
        il.reloadImage("aop/src/bullet"+upgrade.getBulletLevel()+".png", "bullet");
        bulletImage = il.getBuffImage();

        GameButton buyCPU = new GameButton(340, 172, 84, 28);
        GameButton buyBullet = new GameButton(389, 298, 84, 28);
        GameButton back = new GameButton(308, 392, 84, 28);

        autoSetIcons(buyCPU, "buy");
        autoSetIcons(buyBullet, "buy");
        autoSetIcons(back, "back");

        buyCPU.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int initToken = upgrade.getToken();
                try{
                    upgrade.decrementToken(upgrade.getCoreCost());
                    upgrade.incrementNumCore(aop);
                    updateUI();
                }catch(Exception ex){
                    if(upgrade.getToken()!=initToken)
                        upgrade.setToken(initToken);;
                    aop.reportError(ex.getMessage(), "Cannot purchase!");
                }
            }
        });

        buyBullet.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int initToken = upgrade.getToken();
                try{
                    upgrade.decrementToken(upgrade.getBulletCost());
                    upgrade.incrementBulletLevel();
                    updateBulletImage();
                    updateUI();
                }catch(Exception ex){
                    if(upgrade.getToken()!=initToken)
                        upgrade.setToken(initToken);
                    aop.reportError(ex.getMessage(), "Cannot purchase!");
                }
            }
        });

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.remove(getPanel());
                if(isPlay)
                    aop.playingStatus(true);
                aop.updateUI();
            }
        });

        add(buyCPU);
        add(buyBullet);
        add(back);
    }

    public void updateBulletImage(){
        ImageLoader il = new ImageLoader("aop/src/bullet"+upgrade.getBulletLevel()+".png", "bullet");
        bulletImage = il.getBuffImage();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG,0,0, null);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(String.valueOf(upgrade.getToken()), 265, 127);
        g.drawString(upgrade.getCoreCostStr(), 247, 219);
        g.drawString(upgrade.getBulletCostStr(), 247, 345);

        for(int i=0; i<upgrade.getNumCore(); i++)
            g.drawImage(coreImage, 197+(55*i), 235, null);

        g.drawImage(bulletImage, 197, 365, null);
    }
}
