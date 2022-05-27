package gen;

import main.OsirysGame;

public abstract class GameMenuPanel extends MenuPanel{
    protected OsirysGame game;

    @Override
    protected void loadElements(String alt){
        setLayout(null);
        setOpaque(false);
        ImageLoader il = new ImageLoader(path, alt);
        BG = il.getBuffImage();
        
        setBoundsAux();
    }

    @Override
    protected void autoSetIcons(GameButton button, String name){
        button.setIcons(
            game.getCode()+"/src/normal/"+name+".png",
            game.getCode()+"/src/hilite/h_"+name+".png",
            name.toUpperCase()
        );
    }

    public void setBoundsAux(){
        setBounds((int)Math.floor(Double.valueOf(game.getWidth())/2-Double.valueOf(BG.getWidth())/2),
            120,
            BG.getWidth(), 
            BG.getHeight()+40    
        );
    }
}
