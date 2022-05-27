package main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gen.GameButton;
import gen.MenuPanel;

public class SettingPanel extends MenuPanel {

    public SettingPanel(Osirys osirys){
        this.path = "src/img/settingpanel.png";
        this.mid = "img/";
        loadElements("setting");
        setBounds(
            osirys.getWidth()/2-170,
            osirys.getHeight()/2-85,
            340,
            170
        );

        GameButton sound = new GameButton(189, 72, 26, 27);
        GameButton back = new GameButton(getWidth()/2-36, getHeight()-35, 73, 25);


        if(osirys.getSound())
            autoSetNormalIcons(sound, "soundon");
        else
            autoSetNormalIcons(sound, "soundoff");

        autoSetIcons(back, "back");

        sound.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(osirys.getSound()){
                    autoSetNormalIcons(sound, "soundoff");
                    osirys.setSoundOn(false);
                }else{
                    autoSetNormalIcons(sound, "soundon");
                    osirys.setSoundOn(true);
                }
            }
        });

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                osirys.setOnFloater(false);
                osirys.setAllBtnEnable(true);
                osirys.remove(getPanel());
                osirys.updateUI();
            }
        });

        add(sound);
        add(back);
    }

    protected void autoSetNormalIcons(GameButton button, String name){
        button.setIcons(
            "src/img/"+name+".png",
            "src/img/h_"+name+".png",
            name.toUpperCase()
        );
    }
}
