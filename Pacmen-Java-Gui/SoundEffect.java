

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/***
 * 
 * @author Andrey Leskov, Shlomo Raday
 * @exception SoundEffect, this kind of class Responsible for expressing the necessary sounds for events happening in the game.
 * 
 *
 */
public class SoundEffect{
private Clip audioClip ; 

/**
 * 
 * @param soundFileName
 * @see SoundEffect(String soundFileName), 
 * 
 * A constructor method that accepts a variable type String, the method establishes the conditions 
 * necessary to get the sound from where it is used for the game
 * 
 */
 public SoundEffect(String soundFileName) {
   File wav = new File(soundFileName);
    try {
      
       AudioInputStream audioStream = AudioSystem.getAudioInputStream(wav);
       AudioFormat format = audioStream.getFormat();
       DataLine.Info info = new DataLine.Info(Clip.class, format);
       // Get a clip resource.
       audioClip = (Clip) AudioSystem.getLine(info);
       // Open audio clip and load samples from the audio input stream.
       audioClip.open(audioStream);
    } catch (UnsupportedAudioFileException e) {
    	  JOptionPane.showMessageDialog(null,e.getMessage());
    } catch (IOException e) {
    	  JOptionPane.showMessageDialog(null,e.getMessage());
    } catch (LineUnavailableException e) {
    	  JOptionPane.showMessageDialog(null,e.getMessage());
    }
 }
 
  /**
   * 
   * @param on
   * @exception  play( Boolean on), This method receives a boolean variable 
   * and by this variable which is an indication of whether to turn the sound on, or off.
   * 
   */
 // Play or Re-play the sound effect from the beginning, by rewinding.
 public void play( Boolean on) {
    if (on) {
       if(audioClip.isRunning())
       {
        audioClip.stop();   // Stop the player if it is still running
        audioClip.setFramePosition(0); // rewind to the beginning
       }else{
       audioClip.setFramePosition(0);
       audioClip.start();   
         // Start playing
    }
  }
 

 }
}