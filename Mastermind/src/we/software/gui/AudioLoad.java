package we.software.gui;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by bill on 30-Mar-17.
 */
public class AudioLoad{

    private Clip audioClip;
    private boolean playCompleted;

    public AudioLoad(String path){

        try {
            URL audioFile = LoadAssets.load(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }



    public synchronized void closeClip(){

        audioClip.stop();
        audioClip.setFramePosition(0);
    }


    public void playMenuClip(){

        new Thread(){
            public void run(){
                audioClip.start();
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }.start();

    }
}
