package main;

import javax.swing.JPanel;
import java.awt.Dimension;

public class Osirys extends JPanel{
    private MainClass mainClass;

    public Osirys(MainClass mainClass){
        this.mainClass = mainClass;
        setPreferredSize(new Dimension(mainClass.suggestedW(), mainClass.suggestedH()));
    }
}
