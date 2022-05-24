package coc.game;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Font;

import gen.GameButton;

public class GameOverPanel extends MenuPanel {
    private Font font;

    public GameOverPanel(COC coc){
        this.coc = coc;
        this.font = new Font("sans_serif", Font.BOLD, 25);;
        this.path = "coc/src/gameover.png";
        loadElements("over");

        GameButton ok = new GameButton((int)Math.floor(Double.valueOf(getWidth())/2-43), getHeight()-36 , 87, 30);

        autoSetIcons(ok, "ok");

        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                coc.remove(getPanel());
                coc.setButtonsEnabled(true);
                coc.cleanGame();
            }
        });

        add(ok);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(BG, 0, 0, null);
        g.setColor(java.awt.Color.white);
        g.setFont(font);
        String score = String.valueOf(coc.getScore().getGameScore());
        int scoreW = g.getFontMetrics().stringWidth(score);
        g.drawString(score, 
            (int)Math.floor(Double.valueOf(getWidth())/2-Double.valueOf(scoreW)/2),
            129
        );
    }
}
