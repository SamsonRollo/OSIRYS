package coc.game;

import java.util.ArrayList;
import java.util.Iterator;

public class BugDen implements Runnable{
    private COC coc;
    private Level level;
    private ArrayList<Bug> bugs;
    private int colorIdx = 0;
    private int lastY = 85;
    private boolean freeze = false;
    private int threadTimer = 20;

    public BugDen(COC coc, Level level, int initLayer){
        this.coc = coc;
        this.level = level;
        bugs = new ArrayList<Bug>();
        createBugs(initLayer);
    }

    @Override
    public void run() {
        int accumLag = 0;
        int freezeCtr = 0;
        while(coc.isPlay()){

            if(isFreeze()){
                freezeCtr+=20;
                if(freezeCtr>=4000){
                    setFreeze(false);
                    freezeCtr=0;
                }
            }

            if((accumLag>=level.getBugLag() || bugs.size()==0) && !isFreeze()){
                updateBugs(level);
                accumLag=0;
            }

            if(lastY>=133){
                createBugs(1);
                lastY = 85;
            }

            removeBugs();
            accumLag++;
            try{
                coc.updateUI();
            }catch (Exception e){}
            try{
                Thread.sleep(threadTimer);
            }catch(Exception e){};
        }
        
    }

    public void createBugs(int layer){
        int bugPerRow = 15;
        for(int j=0, mulY = 48; j<layer; j++){
            int mulX = (int)Math.floor((520 - bugPerRow*33)/bugPerRow)+33; //11 maust be randomize later
            int shift = colorIdx%2==0 ? 20 : -20; //if shift left or right to shoft row 
            for(int i=0; i<bugPerRow; i++){
                Bug b = new Bug(coc, coc.getLeftBound()+20+mulX*i+shift, 85+mulY*j, (colorIdx%4)+1, shift);
                bugs.add(b);
                coc.add(b);
            }
            incrementColorIndex();
        }
    }

    public void updateBugs(Level level){
        try{
            for(Bug b: bugs)
                b.update(level);
        }catch(Exception e){}
        lastY+=level.getYSpeed();
    }

    public void removeBugs(){
        try{
            for (Iterator<Bug> iterator = bugs.iterator(); iterator.hasNext();) {
                Bug bug = iterator.next();
                if(!bug.isAlive()){
                    iterator.remove();
                    coc.remove(bug);
                    bug = null;
                }
            }
        }catch(Exception e){}
    }

    public void killAllBugs(){
        try{
            for(Bug b : bugs)
                b.setAlive(false);
        }catch(Exception e){}
        removeBugs();
    }
    
    public void decreaseThreadTimer(){
        if(this.threadTimer>5)
            this.threadTimer-=2;
    }

    public int getThreadTimer(){
        return this.threadTimer;
    }

    public int getColorIndex(){
        return this.colorIdx;
    }

    public void incrementColorIndex(){
        this.colorIdx++;
    }

    public void setFreeze(boolean freeze){
        this.freeze = freeze;
    }

    public boolean isFreeze(){
        return this.freeze;
    }

    public ArrayList<Bug> getBugs(){
        return this.bugs;
    }
}
