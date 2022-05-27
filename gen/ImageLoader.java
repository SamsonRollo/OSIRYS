package gen;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageLoader {
    String path = "";
    URL url = null;
    BufferedImage img = null;
    String alt = null;

	public ImageLoader(){}

    public ImageLoader(String path, String alt){
        this.path = path;
        this.alt = alt;
        reloadImage(path, alt);
    }

    public boolean reloadImage(String path, String alt){
        this.path = path;
        this.alt = alt;
        try{
            img = ImageIO.read(this.getClass().getClassLoader().getResource(path));
            return true;
        }catch(Exception ioe){
            img = null;
            this.alt = alt;
            return false; 
        }
    }

    public boolean loadAsURL(String path){
        this.path = path;
        try{
            url = this.getClass().getClassLoader().getResource(path);
            return true;
        }catch(Exception ioe){
            url = null;
            return false; 
        }
    }

    public URL getUrl(){
        return this.url;
    }

    public BufferedImage getBuffImage(){
        return img;
    }

    public String getAlt(){
        return alt;
    }
    
    public boolean isNull(){
        return img == null;
    }
}

