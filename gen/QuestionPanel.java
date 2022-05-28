package gen;

import javax.swing.JLabel;

import aop.game.AOP;
import coc.game.COC;

import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.event.MouseEvent;

public class QuestionPanel extends GameMenuPanel implements MouseListener{
    private Question qObj = null;
    private boolean isPlay;
    private COC coc;
    private AOP aop;
    
    public QuestionPanel(COC coc, AOP aop, boolean playBol){
       this.coc= coc;
       this.aop = aop;
        if(coc!=null)
            game = coc;
        else
            game = aop;

        this.isPlay = playBol;
        this.path = "src/img/powerupquestion.png";
        this.srcPath = "";
        loadElements(game.getCode()+"question");
        setBounds(game.getWidth()/2-BG.getWidth()/2,
            game.getHeight()/2-BG.getHeight()/2,
            BG.getWidth(),
            BG.getHeight()
        );
        qObj = game.getGenerator().generate();

        JLabel question = new JLabel("<html>"+qObj.getQuestion()+"</html>");
        question.setBounds(55,85, 580, 180 );
        question.setHorizontalTextPosition(JLabel.CENTER);
        question.setVerticalTextPosition(JLabel.CENTER);
        question.setFont(new Font("sans_serif", Font.PLAIN, 18));

        int y = 290, yMul = 48;
        for(int i=0; i<qObj.getChoices().size(); i++){
            GameButton btn = new GameButton(getWidth()/2-276, y+yMul*i, 553, 41);
            autoSetIcons(btn, "mchoice");
            btn.setText("<html>"+qObj.getChoiceAt(i)+"</html>");
            btn.setHorizontalTextPosition(GameButton.CENTER);
            btn.setVerticalTextPosition(GameButton.CENTER);
            btn.addMouseListener(this);
            add(btn);
        }

        add(question);
    }

    private boolean checkAnswer(String answer, Question questionObj){
        if(answer.equalsIgnoreCase(questionObj.getAnswer()))
            return true;
        return false;
    }

    
    @Override
    protected void autoSetIcons(GameButton button, String name){
        button.setIcons(
            "src/img/normal/"+name+".png",
            "src/img/hilite/h_"+name+".png",
            name.toUpperCase()
        );
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if(event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1){
            String text = ((GameButton) event.getSource()).getText();
            text = text.substring(6, text.length()-7);

            CheckPanel cp;
            game.remove(getPanel());
            if(coc!=null)
                coc.revertChanges(isPlay); ///watch this
            else
                aop.revertChanges(isPlay);

            if(checkAnswer(text, qObj)){
                cp = new CheckPanel(game, true);
                game.getScore().incrementBonusScore(200);
            }else{
                cp = new CheckPanel(game, false);
            }
            game.add(cp);
            game.setComponentZOrder(cp, 0);
            game.updateUI();
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent arg0) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}
    
}
