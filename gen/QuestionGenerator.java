package gen;

import java.util.ArrayList;
import java.util.Random;

import exception.CategoryException;

public class QuestionGenerator {
    private ArrayList<Question> questions;
    private boolean secondChance = true;
    private int numTaken = 0;
    private int retrieveCtr = 0;

    public QuestionGenerator(ArrayList<Question> questions){
        this.questions = questions;
    }

    public Question generate() throws CategoryException{
        Question q= new Question();
        retrieveCtr = 0;

        if(numTaken==questions.size()){
            for(Question ques : questions)
                ques.setTaken(false);
            numTaken = 0;
        }
        do{
            if(retrieveCtr>=questions.size())
                break;
            q =  questions.get(new Random().nextInt(questions.size()));
            retrieveCtr++;
        }while(q.isTaken() || q.getAnswer()=="" || q.getQuestion()=="");
        
        if(retrieveCtr>=questions.size())
            throw new CategoryException("No questions available at the moment.");
            
        q.updateChoices(rearrangeChoices(q.getChoices()));
        q.setTaken(true);
        numTaken++;
        return q;
    }

    public Question generate(QuestionCategory categ) throws CategoryException{
        ArrayList<Question> categQuestions = new ArrayList<Question>();

        for(Question q : questions){
            if(q.getCategory()==categ && !q.isTaken() && q.getAnswer()!=null)
                categQuestions.add(q);
        }
        if(categQuestions.size()==0 && secondChance){
            for(Question q : questions){
                if(q.getCategory()==categ && q.getAnswer()!=null){
                    categQuestions.add(q);
                    q.setTaken(false);
                }
            }
            if(categQuestions.size()==0)
                secondChance=false;
        }

        if(categQuestions.size()==0 && !secondChance){
            secondChance = true;
            throw new CategoryException("No questions found in that category!");
        }

        Question selectedQ = categQuestions.get(new Random().nextInt(categQuestions.size()));
        selectedQ.updateChoices(rearrangeChoices(selectedQ.getChoices()));
        selectedQ.setTaken(true);

        return selectedQ;
    }

    private ArrayList<String> rearrangeChoices(ArrayList<String> choices){
        ArrayList<String> shuffled = new ArrayList<>();
        while(choices.size()>0){
            int randIdx = new Random().nextInt(choices.size());
            shuffled.add(choices.remove(randIdx));
        }
        return shuffled;
    }

    public void updateQuestions(ArrayList<Question> questions){
        this.questions = questions;
    }

    public boolean isQuestionsNull(){
        if(questions==null)
            return true;
        if(questions.size()==0)
            return true;
        return false;
    }

    public Question getQuestionAt(int id){
        return questions.get(id);
    }

}
