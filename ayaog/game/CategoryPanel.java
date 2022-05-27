package ayaog.game;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

import aop.exception.ErrorReport;


import gen.GameButton;
import gen.GameMenuPanel;
import gen.QuestionCategory;

public class CategoryPanel extends GameMenuPanel implements ActionListener{
    private AYAOG ayaog;

    public CategoryPanel(AYAOG ayaog){
        this.ayaog = ayaog;
        this.game = ayaog;
        this.path = "ayaog/src/selectionPanel.png";
        this.srcPath = "ayaog/";
        loadElements("select");
        setBounds(
            ayaog.getWidth()/2-277,
            ayaog.getHeight()/2-175,
            553,
            350
        );

        GameButton overview = new GameButton(this.getWidth()/2-111, 57, 222, 60);
        GameButton pManage = new GameButton(this.getWidth()/2-230, 143, 222, 60);
        GameButton mManage = new GameButton(this.getWidth()/2+8, 143, 222, 60);
        GameButton sManage = new GameButton(this.getWidth()/2-230, 231, 222, 60);
        GameButton secAndPro = new GameButton(this.getWidth()/2+8, 231, 222, 60);

        autoSetIcons(overview, "overview");
        autoSetIcons(pManage, "processman");
        autoSetIcons(mManage, "memoryman");
        autoSetIcons(sManage, "storageman");
        autoSetIcons(secAndPro, "secprotect");

        overview.addActionListener(this);
        pManage.addActionListener(this);
        mManage.addActionListener(this);
        sManage.addActionListener(this);
        secAndPro.addActionListener(this);

        add(overview);
        add(pManage);
        add(mManage);
        add(sManage);
        add(secAndPro);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        if(ayaog.getQuestionManager().getQuestionGenerator().isQuestionsNull()){
            new ErrorReport(ayaog.getMainClass(),
                "No questions imported!",
                 "Invalid Category"
            );
            return;
        }

        QuestionCategory categ;

        if(e.getActionCommand().equalsIgnoreCase("processman"))
            categ = QuestionCategory.PROCESS_MANAGEMENT;
        else if(e.getActionCommand().equalsIgnoreCase("memoryman"))
            categ = QuestionCategory.MEMORY_MANAGEMENT;
        else if(e.getActionCommand().equalsIgnoreCase("storageman"))
            categ = QuestionCategory.STORAGE_MANAGEMENT;
        else if(e.getActionCommand().equalsIgnoreCase("secprotect"))
            categ = QuestionCategory.SECURITY_AND_PROTECTION;
        else
            categ = QuestionCategory.OVERVIEW;

        Random rand = new Random();
        QuestionType type = QuestionType.MULTIPLE;

        if((rand.nextInt(100)+1)%5==0)
            type = QuestionType.TRUEFALSE;
        
        if(ayaog.getQuestionManager().formulateQuestion(categ, type)){
            ayaog.remove(getPanel());
            ayaog.setAllBtnEnable(true);
            ayaog.loadGameScreen(type);
            ayaog.revalidate();
            ayaog.updateUI();
        }
    }
    
}
