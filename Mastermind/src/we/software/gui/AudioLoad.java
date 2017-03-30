package we.software.gui;

import com.intellij.database.view.generators.Clipboard;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by bill on 30-Mar-17.
 */
public class AudioLoad implements LineListener{

    private URL audioFile;
    private AudioInputStream audioStream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip audioClip;
    private boolean playCompleted;

    public AudioLoad(String path){

        try {
            audioFile = LoadAssets.load(path);
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            format = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);


        } catch (IOException e) {
            System.out.println("Error playing the audio file.");
            e.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        }
    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
    }

    public synchronized void closeClip(){
        audioClip.close();
    }

    public synchronized void playClip(){
        audioClip.start();

        while (!playCompleted) {
            // wait for the playback completes
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        audioClip.stop();
    }

    public void playMenuClip(){
        audioClip.start();
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        while (!playCompleted) {
            // wait for the playback completes
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        audioClip.stop();
    }
}
