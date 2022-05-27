package ayaog.game;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gen.GameButton;
import gen.GameMenuPanel;
import main.Screen;

public class CertificatePanel extends GameMenuPanel{

    public CertificatePanel(AYAOG ayaog, boolean win, String command){
        this.game = ayaog;
        this.path = "ayaog/src/anti_certificate.png";
        if(win)
            this.path = "ayaog/src/certificate.png";
        // if(command.equalsIgnoreCase("save"))
        //  this.path = ayaog/src/savegame.png;
        this.srcPath = "ayaog/";
        loadElements("select");
        setBounds(
            ayaog.getWidth()/2-BG.getWidth()/2,
            ayaog.getHeight()/2-BG.getHeight()/2,
            BG.getWidth(),
            BG.getHeight()
        );

        GameButton okBtn = new GameButton(getWidth()/2-45, getHeight()-45, 90, 28);

        autoSetIcons(okBtn, "save");

        okBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(command.equals("drop") || command.equals("save")){  
                    ayaog.getMainClass().showScreen(Screen.MENU.name());
                }

                ayaog.remove(getPanel());
                ayaog.resetGame();

                CategoryPanel cp = new CategoryPanel(ayaog);
                ayaog.setFloater(cp);
            }
        });

        add(okBtn);
    }
    
}