package ayaog.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import gen.ExcelLoader;
import gen.FileManager;
import gen.GameButton;
import gen.MenuPanel;
import gen.QuestionGenerator;
import gen.Score;

import main.MainClass;
import main.OsirysGame;

public class AYAOG extends OsirysGame implements MouseListener{
    private Font font;
    private boolean start = true;
    private QuestionManager qManager;
    private FileManager fileManager;
    private Level level;

    public AYAOG(MainClass mainClass, Score score){
        this.mainClass = mainClass;
        this.score = score;
        setCode("ayaog");
        setProperties();
    }

    public void loadGame(){
       loadElements();
        fileManager.load(qManager, score, level);
       updateUI();
    }

    public void loadElements(){
        loadBackground();
        ExcelLoader el = new ExcelLoader(getExcepPath());
        el.loadExcel();
        qManager = new QuestionManager(getAYAOG(), new QuestionGenerator(el.getQuestions()));
        fileManager = new FileManager(getAYAOG());

        level = new Level();
        font = new Font("sans_serif", Font.BOLD, 20);

        questionArea = new JLabel("");
        questionArea.setBounds(174, 71, 495, 197);
        questionArea.setHorizontalTextPosition(JLabel.CENTER);
        questionArea.setVerticalTextPosition(JLabel.CENTER);
        questionArea.setFont(new Font("sans_serif", Font.PLAIN, 18));

        peekBtn = new GameButton(26, 99, 90, 28);
        copyBtn = new GameButton(26, 135, 90, 28);
        saveBtn = new GameButton(26, 205, 90, 28);
        dropBtn = new GameButton(26, 241, 90, 28);
        helpBtn = new GameButton(655, 5, 35, 35);
        lockBtn = new GameButton(596, 308, 84, 84);

        setLockEnabled(false);

        autoSetIcons(peekBtn, "peek");
        autoSetIcons(copyBtn, "copy");
        autoSetIcons(saveBtn, "save");
        autoSetIcons(dropBtn, "drop");
        autoSetIcons(helpBtn, "help");
        autoSetIcons(lockBtn, "lockin");

        peekBtn.addMouseListener(this);
        copyBtn.addMouseListener(this);
        saveBtn.addMouseListener(this);
        dropBtn.addMouseListener(this);
        lockBtn.addMouseListener(this);
        
        peekBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnClickCtr>=1){
                    Classmate cm = new Classmate(qManager);
                    changeAnswerFromClassmate(cm.getAnswer());
                    peekAlive = false;
                    peekBtn.setEnabled(false);
                }
            }
        });

        copyBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnClickCtr>=1){
                    copyAlive = false;
                    copyBtn.setEnabled(false);
                    Classmate cm = new Classmate(qManager);
                    changeAnswerFromClassmate(cm.getAnswer());
                    getAYAOG().paintImmediately(getAYAOG().getBounds());
                    lockAnswer();
                }
            }
        });

        saveBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnClickCtr>=1){
                    fileManager.saveAYAOG(qManager, score.getGameScore(), level.getLevel());
                    CertificatePanel cp = new CertificatePanel(getAYAOG(), false, "save");
                    setFloater(cp);
                }
            }
        });

        dropBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnClickCtr>=1){
                    score.incrementTotalScore(score.getGameScore());
                    fileManager.destroySave();
                    CertificatePanel cp = new CertificatePanel(getAYAOG(), false, "drop");
                    setFloater(cp);
                }
            }
        });

        helpBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               
            }
        });

        lockBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(btnClickCtr>=1){
                    lockAnswer();
                }
            }
        });

        add(peekBtn);
        add(copyBtn);
        add(saveBtn);
        add(dropBtn);
        add(helpBtn);  
        add(lockBtn);
        add(questionArea);
        setAllBtnEnable(false);

        CategoryPanel cp = new CategoryPanel(this);
        add(cp);
        setComponentZOrder(cp, 0);
    }

    public void lockAnswer(){
        setAllBtnEnable(false);
        Thread lockThread = new Thread(new Runnable() {
            public void run(){

                //play drum roll
                try{
                    Thread.sleep(2500);
                }catch(Exception e){}
            }
        });
        lockThread.start();
        
        try {
            lockThread.join();
        }catch(InterruptedException e1){}

        String playerAnswer = selectedChoice.getText();

        if(qManager.getQuestionType()==QuestionType.MULTIPLE){
            playerAnswer = playerAnswer.substring(6, playerAnswer.length()-7);
        }else{
            playerAnswer = selectedChoice.getActionCommand();
            if(playerAnswer.toLowerCase().contains("true"))
                playerAnswer = "true";
            else
                playerAnswer = "false";
        }

        if(qManager.checkAnswer(playerAnswer)){
            score.setGameScore(level.getPrize());
            if(level.getLevel()<11){
                level.incrementLevel();
                CategoryPanel cp = new CategoryPanel(getAYAOG());
                setFloater(cp);
            }else{ //win
                CertificatePanel cp = new CertificatePanel(getAYAOG(), true, "win");
                setFloater(cp);
            }
        }else{
            score.setGameScore(level.getWinning(true));
            CertificatePanel cp = new CertificatePanel(getAYAOG(), false, "lose");
            setFloater(cp);
        }
    }

    public void resetGame(){
        peekAlive = true;
        copyAlive = true;
        setAllBtnEnable(true);
        setLockEnabled(false);
        score.setTotalScore(score.getGameScore());
        score.resetCurrentGameScore();
        level = new Level();
        removeCurrentQuestionSetup();
    }

    public void loadGameScreen(QuestionType type){
        questionArea.setVerticalTextPosition(JLabel.CENTER);
        if(qManager.questionHasImage()){
            questionArea.setIcon(new ImageIcon(qManager.getImage()));
            questionArea.setVerticalTextPosition(JLabel.BOTTOM);
        }
        questionArea.setText("<html>"+qManager.getQuestion()+"</html>");
        questionArea.validate();
        loadChoicesBtn(type);

        if(level.getLevel()>=11){
            peekAlive=false;
            copyAlive=false;
        }
    }

    public void loadChoicesBtn(QuestionType type){
        if(!start)
            removeCurrentQuestionSetup();

        start = false;
        setLockEnabled(false);
        
        choicesGroup = new ButtonGroup();
        
        if(type == QuestionType.TRUEFALSE){
            trueBtn = new GameButton(58, 338, 219, 74);
            falseBtn = new GameButton(320, 338, 219, 74);

            autoSetIcons(trueBtn, "truechoice");
            autoSetIcons(falseBtn, "falsechoice");

            trueBtn.addMouseListener(this);
            falseBtn.addMouseListener(this);
        
            trueBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if(btnClickCtr>=1){
                       changeSelected(selectedChoice, trueBtn);
                    }
                }
            });

            falseBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if(btnClickCtr>=1){
                        changeSelected(selectedChoice, falseBtn);
                    }
                }
            });
            
            choicesGroup.add(trueBtn);
            choicesGroup.add(falseBtn);

            getAYAOG().add(trueBtn);
            getAYAOG().add(falseBtn);
        }else{
            ArrayList<String> choices = qManager.getChoices();
            int yMul = 49, yCtr=0;

            for(String choice : choices){
                GameButton button = new GameButton(17, 305+yMul*yCtr, 553, 41);
                autoSetIcons(button, "mchoice");
                addBtnChoiceText(button, choice);

                button.addMouseListener(this);
                button.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(btnClickCtr>=1)
                           changeSelected(selectedChoice, button);
                    }
                });
                choicesGroup.add(button);
                getAYAOG().add(button);
                yCtr++;
            }
        }
        getAYAOG().updateUI();
    }
    
    public void removeCurrentQuestionSetup(){
        questionArea.setIcon(null);

        for(Component c : getAYAOG().getComponents()){
            if(c instanceof GameButton){
                GameButton btn = (GameButton)c;
                if(btn.getActionCommand().toLowerCase().contains("mchoice")){
                    getAYAOG().remove(btn);
                    continue;
                }
            
                if(btn.getActionCommand().toLowerCase().contains("truechoice") 
                    || btn.getActionCommand().toLowerCase().contains("falsechoice"))
                    getAYAOG().remove(btn);
            }
        }
        selectedChoice = null;
    }

    public void changeSelected(GameButton currentBtn, GameButton newBtn){
        setLockEnabled(true);
        if(currentBtn==newBtn)
            return;
        String newStr = newBtn.getActionCommand().toLowerCase();
        if(currentBtn!=null){
            String curStr = currentBtn.getActionCommand().toLowerCase();
            curStr = curStr.substring(0, curStr.length()-4);
            autoSetIcons(currentBtn, curStr);
        }
        autoSetIcons(newBtn, newStr+"lock");
        this.selectedChoice = newBtn;
        getAYAOG().updateUI();
    }

    public void setLockEnabled(boolean enable){
        this.lockBtn.setEnabled(enable);
    }

    public void addBtnChoiceText(GameButton button, String text){
        button.setText("<html>"+text+"</html>");
        button.setHorizontalTextPosition(GameButton.CENTER);
        button.setVerticalTextPosition(GameButton.CENTER);
    }

    public void changeAnswerFromClassmate(String cmAnswer){
        for(Component c : getAYAOG().getComponents()){
            if(c instanceof GameButton){
                GameButton btn = (GameButton)c;

                if(qManager.getQuestionType()==QuestionType.MULTIPLE){
                    String btnText = btn.getText();
                    if(btnText.length()>13){
                        btnText = btnText.substring(6, btnText.length()-7);

                        if(btnText.equalsIgnoreCase(cmAnswer)){
                            changeSelected(selectedChoice, btn);
                            selectedChoice = btn;
                            break;
                        }
                    }
                }else{
                    String btnCommand = btn.getActionCommand();
                    if(btnCommand.toUpperCase().contains(cmAnswer.toUpperCase())){
                        changeSelected(selectedChoice, btn);
                        selectedChoice = btn;
                        break;
                    }

                }
            }
        }
        getAYAOG().updateUI();
    }

    public void setAllBtnEnable(boolean status){
        Component[] components = getAYAOG().getComponents();
        for(Component c : components)
            if(c instanceof GameButton)
                ((GameButton)c).setEnabled(status);
        if(!peekAlive)
            peekBtn.setEnabled(false);
        if(!copyAlive)
            copyBtn.setEnabled(false);
        helpBtn.setEnabled(true);
    }

    public void setFloater(MenuPanel panel){
        getAYAOG().add(panel);
        getAYAOG().setComponentZOrder(panel, 0);
        getAYAOG().updateUI();
    }

    public QuestionManager getQuestionManager(){
        return this.qManager;
    }

    public FileManager getFileManager(){
        return this.fileManager;
    }

    public AYAOG getAYAOG(){
        return this;
    }

    public boolean getPeek(){
        return this.peekAlive;
    }

    public void setPeek(boolean status){
        this.peekAlive = status;
    }
  
    public boolean getCopy(){
        return this.copyAlive;
    }

    public void setCopy(boolean status){
        this.copyAlive = status;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(BG_IMG, 0, 0, null);

        g.setColor(Color.black);
        g.setFont(font);
        g.drawString(String.valueOf(score.getGameScore()), 61, 43);
        g.drawString(String.valueOf(level.getPrize()), 348, 43);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        btnClickCtr++;
        if(btnClickCtr>1){
            GameButton gb = (GameButton) arg0.getSource();
            
            for(Enumeration<AbstractButton> buttons = choicesGroup.getElements(); buttons.hasMoreElements();){
                GameButton btn = (GameButton)buttons.nextElement();
                if(gb==btn){
                    selectedChoice = gb;
                    return;
                }
            }
        }
        
        Timer t = new Timer("btnDC", false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                btnClickCtr = 0;
            }
        }, 250);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {
        btnClickCtr = 0;
    }

    @Override
    public void mousePressed(MouseEvent arg0) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}

    private GameButton trueBtn, falseBtn, peekBtn, copyBtn, saveBtn, lockBtn, dropBtn, helpBtn;
    private GameButton selectedChoice = null;
    private ButtonGroup choicesGroup;
    private boolean peekAlive =true, copyAlive = true;
    private int btnClickCtr = 0;
    private JLabel questionArea;
}
