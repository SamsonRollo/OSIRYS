package ayaog.game;

import java.util.ArrayList;
import java.util.Random;

import exception.CategoryException;
import exception.ErrorReport;

import java.awt.image.BufferedImage;

import gen.Question;
import gen.QuestionCategory;
import gen.QuestionGenerator;
import main.OsirysGame;

public class QuestionManager {
    private OsirysGame game;
    private QuestionGenerator generator;
    private Question question;
    private QuestionType type;
    private String formulatedQuestion;
    private boolean trueFalseAnswer;

    public QuestionManager(OsirysGame game, QuestionGenerator generator){
        this.game = game;
        this.generator = generator;
    }

    public boolean formulateQuestion(QuestionCategory categ, QuestionType type){
        this.type = type;
        
        try{
            question = generator.generate(categ);
        }catch(CategoryException e){
            new ErrorReport(game.getMainClass(), e.getMessage(), "Cannot create question");
            return false;
        }

        if(type==QuestionType.MULTIPLE)
            formulatedQuestion = question.getQuestion();
        else
            formulatedQuestion = formulateTrueFalseQuestion(question);
        return true;
    }

    private String formulateTrueFalseQuestion(Question question){
        String q = question.getQuestion();
        String assumption = question.getChoiceAt(new Random().nextInt(question.getChoices().size()));

        trueFalseAnswer = assumption.equalsIgnoreCase(question.getAnswer()) ? true : false;
        q = q+" The answer is "+assumption+".";

        return q;
    }

    public String getQuestion(){
        return this.formulatedQuestion;
    }

    public ArrayList<String> getChoices(){
        return question.getChoices();
    }

    public BufferedImage getImage(){
        if(question.hasImage())
            return question.getImage();
        return null;
    }

    public QuestionType getQuestionType(){
        return this.type;
    }

    public boolean questionHasImage(){
        return question.hasImage();
    }

    //tabbooo
    public boolean getBooleanAnswer(){
        return trueFalseAnswer;
    }

    public String getStringAnswer(){
        return question.getAnswer();
    }

    public String getId(){
        return question.getId();
    }

    public void setState(int id){
        this.question = generator.getQuestionAt(id-1);
    }

    public void setBolAnswer(boolean ans){
        this.trueFalseAnswer = ans;
    }

    public void setQuestionType(QuestionType type){
        this.type = type;
    }

    public void setManualQuestion(String formQ){
        this.formulatedQuestion = formQ;
    }

    public boolean checkAnswer(String string){
        if(type==QuestionType.TRUEFALSE){
            if(Boolean.parseBoolean(string)==trueFalseAnswer)
                return true;
        }else{
            if(string.equalsIgnoreCase(question.getAnswer()))
                return true;
        }
        return false;
    }

    public QuestionGenerator getQuestionGenerator(){
        return this.generator;
    }
    
}
