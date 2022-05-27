package gen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;

import ayaog.game.AYAOG;
import ayaog.game.Level;
import ayaog.game.QuestionManager;
import ayaog.game.QuestionType;
import main.Osirys;

public class FileManager {
    private String ayaogPath = "src/file/ayaog.sav";
    private AYAOG ayaog;
    private Osirys osirys;

    public FileManager(AYAOG ayaog){
        this.ayaog = ayaog;
    }

    public FileManager(Osirys osirys){
        this.osirys = osirys;
    }

    public void saveAYAOG(QuestionManager qmManager, int gameScore, int level){
        URL url = this.getClass().getClassLoader().getResource(ayaogPath);
        try{
            File file = new File(url.toURI());
            if(!file.exists())
                file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(String.valueOf(level));
            bw.newLine();
            bw.write(String.valueOf(gameScore));
            bw.newLine();
            bw.write(qmManager.getId());
            bw.newLine();
            bw.write(qmManager.getQuestionType().name());
            bw.newLine();
            bw.write(qmManager.getQuestion());
            bw.newLine();
            bw.write(String.valueOf(qmManager.getBooleanAnswer()));
            bw.newLine();
            bw.write(String.valueOf(ayaog.getPeek()));
            bw.newLine();
            bw.write(String.valueOf(ayaog.getCopy()));
            bw.newLine();
            
            bw.close();
        }catch(Exception e){}
    }

    public void load(QuestionManager qManager, Score score, Level level){
        URL url = this.getClass().getClassLoader().getResource(ayaogPath);
        
        try{
            File file = new File(url.toURI());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int curCtr = 0;

            while((line = br.readLine())!=null){
                if(curCtr==0)
                    level.setLevel(Integer.valueOf(line));
                else if(curCtr==1)
                    score.setGameScore(Integer.valueOf(line));
                else if(curCtr==2)
                    qManager.setState(Integer.valueOf(line));
                else if(curCtr==3)
                    qManager.setQuestionType(getQType(line));
                else if(curCtr==4)
                    qManager.setManualQuestion(line);
                else if(curCtr==5)
                    qManager.setBolAnswer(Boolean.parseBoolean(line));
                else if(curCtr==6)
                    ayaog.setPeek(Boolean.parseBoolean(line));
                else if(curCtr==7)
                    ayaog.setCopy(Boolean.parseBoolean(line));
                curCtr++;
            }
            
            br.close();
        }catch(Exception e){}
    }

    public void destroySave(){
        URL url = this.getClass().getClassLoader().getResource(ayaogPath);
        
        try{
            File file = new File(url.toURI());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write("");
            bw.close();
        }catch(Exception e){}
    }

    public boolean isAyaogFileEmpty(){
        File file;
        try{
            URL url = this.getClass().getClassLoader().getResource(ayaogPath);
            file = new File(url.toURI());
        }catch(Exception e){return true;};
        return file.length()==0 ? true : false;
    }

    private QuestionType getQType(String str){
        if(str.equals("MULTIPLE"))
            return QuestionType.MULTIPLE;
        else
            return QuestionType.TRUEFALSE;
    }
}
