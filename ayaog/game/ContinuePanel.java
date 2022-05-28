package ayaog.game;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gen.GameButton;
import gen.GameMenuPanel;

public class ContinuePanel extends GameMenuPanel{
    
    public ContinuePanel(AYAOG ayaog){
        this.game = ayaog;
        this.path = "ayaog/src/continuequit.png";
        this.srcPath = "ayaog/";
        loadElements("continuequir");
        setBounds(ayaog.getWidth()/2-BG.getWidth()/2,
            ayaog.getHeight()/2-BG.getHeight()/2,
            BG.getWidth(),
            BG.getHeight()
        );
        GameButton yes = new GameButton(getWidth()/3-45, getHeight()-35, 90, 28);
        GameButton no = new GameButton(getWidth()/3*2-45, getHeight()-35, 90, 28);

        autoSetIcons(yes, "yes");
        autoSetIcons(no, "no");
    
        yes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ayaog.remove(getPanel());
                CategoryPanel cp = new CategoryPanel(ayaog);
                ayaog.setFloater(cp);
                ayaog.updateUI();
            }
        });

        no.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ayaog.getScore().incrementTotalScore(ayaog.getScore().getGameScore());
                ayaog.remove(getPanel());
                CertificatePanel cp = new CertificatePanel(ayaog, false, "noncontinue");
                ayaog.setFloater(cp);
                ayaog.updateUI();
            }
        });

        add(yes);
        add(no);
    }
}
