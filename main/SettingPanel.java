package main;

import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import exception.ErrorReport;

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
            BG.getWidth(),
            BG.getHeight()
        );

        GameButton sound = new GameButton(189, 72, 26, 27);
        GameButton importbtn = new GameButton(getWidth()/2-70, 109, 140, 27);
        GameButton back = new GameButton(getWidth()/2-36, getHeight()-35, 73, 25);


        if(osirys.getSound())
            autoSetNormalIcons(sound, "soundon");
        else
            autoSetNormalIcons(sound, "soundoff");

        autoSetIcons(back, "back");
        autoSetIcons(importbtn, "import");

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

        importbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
		        JFileChooser jf = new JFileChooser();
                jf.setAcceptAllFileFilterUsed(false);
                jf.addChoosableFileFilter(new FileNameExtensionFilter("Excel File", "xlsx"));

                if(jf.showOpenDialog(osirys.getMainClass())==JFileChooser.APPROVE_OPTION){
                    if(jf.getSelectedFile().exists())
                        osirys.setExcelFile(jf.getSelectedFile());
                    else
                        new ErrorReport(osirys.getMainClass(), "Cannot import file", "Import error");
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
        add(importbtn);
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
