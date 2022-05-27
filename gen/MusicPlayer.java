package gen;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class MusicPlayer{
    private AudioClip sound;

    public MusicPlayer(String path){
        URL url = this.getClass().getClassLoader().getResource("file:"+path);
        sound = Applet.newAudioClip(url);
    }

    public void play(){
        sound.play();
    }

    public void loop(){
        sound.play();
    }

    public void stop(){
        sound.stop();
    }

}