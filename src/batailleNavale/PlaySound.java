package batailleNavale;

import javax.sound.sampled.*;
import java.io.*;

public class PlaySound implements LineListener {

    boolean isPlaybackCompleted;
    Clip audioClip;
    AudioInputStream audioStream;

    public PlaySound(String audioFilePath) {
        try {
            // file read
//            InputStream inputStream = getClass().getResourceAsStream(audioFilePath);
            InputStream inputStream = new ByteArrayInputStream(audioFilePath.getBytes());
            // from file stream, create an AudioInputStream
            File file = new File(audioFilePath);
            this.audioStream = AudioSystem.getAudioInputStream(file);
            // creation of a DataLine.Info object
            AudioFormat audioFormat = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            // create Clip object from DataLine.Info, open the stream, call start to start playing the audio:
//            Clip audioClip = (Clip) AudioSystem.getLine(info);
            this.audioClip = AudioSystem.getClip();
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
//            audioClip.start();
            // close open resource
//            audioClip.close();
//            audioStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
//            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
//            System.out.println("Playback completed.");
        }
    }

    public Clip getAudioClip() {
        return audioClip;
    }

    public void setAudioClip(Clip audioClip) {
        this.audioClip = audioClip;
    }

    public AudioInputStream getAudioStream() {
        return audioStream;
    }

    public void setAudioStream(AudioInputStream audioStream) {
        this.audioStream = audioStream;
    }

    public void start() {
        try {
            this.getAudioClip().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            this.getAudioClip().close();
            this.getAudioStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void plouf() {
        new PlaySound("resources/Plouf.wav").start();
    }
    
    public static void boom() {
        new PlaySound("resources/Boom.wav").start();
    }
    
    public static void coule() {
        new PlaySound("resources/Coule.wav").start();
    }

}