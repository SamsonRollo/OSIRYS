package gen;

import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer{
    private MediaPlayer sound;

    public MusicPlayer(String path){
        Media song=null;
        try {
            song = new Media(getClass().getClassLoader().getResource(path).toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        sound = new MediaPlayer(song); 
    }

    public void play(){
        sound.play();
    }

    public void loop(){
        sound.play();
        sound.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                sound.seek(Duration.ZERO);
                sound.play();
            }
        }); 
    }

    public void stop(){
        sound.stop();
    }

}