package comp102x.project.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEngine {

	private Map<Integer, String> soundPaths;
	private List<Clip> clips;

	public SoundEngine() {
		soundPaths = new HashMap<Integer, String>();
		clips = Collections.synchronizedList(new ArrayList<Clip>());
	}

	public void loadSound(String filepath, Integer soundID) {
		soundPaths.put(soundID, filepath);
	}

	public void playSound(Integer soundID) {


		loopSound(soundID, 1);
	}
	
	public void loopSound(Integer soundID, int numberOfTimes) {
		
		try {

			AudioInputStream audioIn = AudioSystem
					.getAudioInputStream(new File(soundPaths.get(soundID)));
			AudioFormat format = audioIn.getFormat();
			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					format.getSampleRate(), 16, format.getChannels(),
					format.getChannels() * 2, format.getSampleRate(), false);
			audioIn = AudioSystem.getAudioInputStream(format, audioIn);

			final Clip clip = AudioSystem.getClip();
			clip.addLineListener(new LineListener() {

				@Override
				public void update(LineEvent event) {

					LineEvent.Type type = event.getType();

					if (type.equals(LineEvent.Type.STOP)) {
						clip.close();
						clips.remove(clip);
					}
				}
			});

			clip.open(audioIn);
			clip.loop(numberOfTimes - 1);
			clips.add(clip);

		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {

			e.printStackTrace();
		} 
	}
	
	public void stopAll() {
		
		synchronized(clips) {
			for (Clip clip : clips) {
				clip.stop();
			}
		}
	}

}
