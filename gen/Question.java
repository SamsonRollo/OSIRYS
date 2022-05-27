package gen;

import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Question {
    private String id;
    private QuestionCategory category;
    private String question;
    private ArrayList<String> choices;
    private String answer;
    private boolean taken;
    private String imgPath;
    private BufferedImage image;

    public Question(){
        taken = false;
        choices = new ArrayList<String>();
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setCategory(String category){
        category = category.replaceAll(" ", "_");
        if(category.equalsIgnoreCase(QuestionCategory.OVERVIEW.name()))
            this.category = QuestionCategory.OVERVIEW;
        else if(category.equalsIgnoreCase(QuestionCategory.PROCESS_MANAGEMENT.name()))
            this.category = QuestionCategory.PROCESS_MANAGEMENT;
        else if(category.equalsIgnoreCase(QuestionCategory.MEMORY_MANAGEMENT.name()))
            this.category = QuestionCategory.MEMORY_MANAGEMENT;
        else if(category.equalsIgnoreCase(QuestionCategory.STORAGE_MANAGEMENT.name()))
            this.category = QuestionCategory.STORAGE_MANAGEMENT;
        else if(category.equalsIgnoreCase(QuestionCategory.SECURITY_AND_PROTECTION.name()))
            this.category = QuestionCategory.SECURITY_AND_PROTECTION;
    }

    public QuestionCategory getCategory(){
        return this.category;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion(){
        return this.question;
    }

    public void addChoice(String choice){
        this.choices.add(choice);
    }

    public ArrayList<String> getChoices(){
        return this.choices;
    }

    public String getChoiceAt(int index){
        try{
            return this.choices.get(index);
        }catch(Exception e){
            return null;
        }
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return this.answer;
    }

    public void setImagePath(String path){
        this.imgPath = path;
    }

    public String getImgPath(){
        return this.imgPath;
    }

    public BufferedImage getImage(){
        if(image==null){
            ImageLoader il = new ImageLoader(imgPath, "image");
            this.image = il.getBuffImage();
        }
        return this.image;
    }

    public boolean hasImage(){
        if(image==null)
            return false;
        return true;
    }

    public void setTaken(boolean taken){
        this.taken = taken;
    }

    public boolean isTaken(){
        return this.taken;
    }  
}
