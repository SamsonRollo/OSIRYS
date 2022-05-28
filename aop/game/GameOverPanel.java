package aop.game;

import gen.GameButton;
import gen.GameMenuPanel;
import gen.MusicType;
import gen.Score;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameOverPanel extends GameMenuPanel {
    private Score score;
    private Font font;

    public GameOverPanel(AOP aop, Score score, Font font){
        aop.getSoundManager().play(MusicType.GAMEOVER);
        this.game = aop;
        this.score = score;
        this.font = font;
        this.path = "aop/src/gameoverPanel.png";
        loadElements("over");
        setBounds(0,0,700,500);

        GameButton back = new GameButton(308, 320, 84, 28);

        autoSetIcons(back, "back");

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                aop.remove(getPanel());
                aop.resetGame();
                aop.setAllBtnEnabled(true);
                aop.updateUI();
            }
        });

        add(back);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(String.valueOf(score.getGameScore()+score.getBonusScore()), 275, 271);

    }
}
