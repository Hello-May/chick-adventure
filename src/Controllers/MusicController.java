package Controllers;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicController {

	// 只能播放短音樂

//	 private AudioClip sound;
//
//	 public void playEffect(){
//		 URL file = getClass().getResource(filename);
//		 sound = Applet.newAudioClip(file);
//		 sound.play();
//	 }
//
//	public void stopEffect() {
//		sound.stop();
//	}

	////////////////////////////////////////////////////////////////////////////

	AudioInputStream audioInputStream;
	private String filename;
	private Thread thread;

	public MusicController(String wavfile) {
		filename = wavfile;
	}

	public void play() {
		if (thread == null) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						do {
							audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(filename));
							AudioFormat format = audioInputStream.getFormat();
							DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
							SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
							line.open();
							line.start();
							int length = 0;
							byte[] buffer = new byte[512];
							while ((length = audioInputStream.read(buffer)) != -1) {
								NumberFormat numberFormat = NumberFormat.getInstance();
								numberFormat.setMaximumFractionDigits(2);
								line.write(buffer, 0, length);
							}
							line.drain();
							line.close();
							audioInputStream.close();
						} while (true);
					} catch (UnsupportedAudioFileException e) {
//						e.printStackTrace();
					} catch (IOException e) {
//						e.printStackTrace();
					} catch (LineUnavailableException e) {
//						e.printStackTrace();
					}
				}
			});
			thread.start();
		} else {
			try {
				thread.join();
				thread.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void stop() {
		if (audioInputStream != null) {
			try {
				audioInputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (thread != null) {
			thread.interrupt();
		}
		thread = null;

	}

	public void playOnce() {
		if (thread == null) {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(filename));
						AudioFormat format = audioInputStream.getFormat();
						DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
						SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
						line.open();
						line.start();
						int length = 0;
						byte[] buffer = new byte[512];
						while ((length = audioInputStream.read(buffer)) != -1) {
							NumberFormat numberFormat = NumberFormat.getInstance();
							numberFormat.setMaximumFractionDigits(2);
							line.write(buffer, 0, length);
						}
						line.drain();
						line.close();
						audioInputStream.close();
					} catch (UnsupportedAudioFileException e) {
					} catch (IOException e) {
					} catch (LineUnavailableException e) {
					}
				}
			});
			thread.start();
		} else {
			try {
				thread.join();
				thread.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
