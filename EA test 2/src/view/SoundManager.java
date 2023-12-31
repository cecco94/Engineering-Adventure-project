package view;


import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	//il volume è un numero da 0 a 1
	private float musicVolume = 0.2f, seVolume = 0.2f;
	private Clip music, soundEffect;
	private URL soundURL[] = new URL[15];
	public static final int MENU_MUSIC = 0, AULA_STUDIO = 1, DORMITORIO = 2, BIBLIOTECA = 3,
							COLPO = 4, TENDA = 5, FUOCO = 6, LABORATORIO = 7, BOSS_SECOND_PHASE = 8, CAFFE = 9, 
							APPUNTI = 10, CFU = 11, DIALOGUE = 12, BOSS_FIRTST_PHASE = 13;
	
	// tutti i percorsi dei file dei suoni vengono inseriti in un array
	public SoundManager() {
		soundURL[0] = getClass().getResource("/sound/menuLeggera.wav");
		soundURL[1] = getClass().getResource("/sound/salaStudioLeggera.wav");
		soundURL[2] = getClass().getResource("/sound/dormitorioLeggera.wav");
		soundURL[3] = getClass().getResource("/sound/biblioteca.wav");
		soundURL[4] = getClass().getResource("/sound/hitmonster.wav");
		soundURL[5] = getClass().getResource("/sound/tenda.wav");
		soundURL[6] = getClass().getResource("/sound/burning.wav");
		soundURL[7] = getClass().getResource("/sound/laboratorio.wav");	
		soundURL[8] = getClass().getResource("/sound/bossMusic.wav");	
		soundURL[9] = getClass().getResource("/sound/powerup.wav");	
		soundURL[10] = getClass().getResource("/sound/coin.wav");	
		soundURL[11] = getClass().getResource("/sound/fanfare.wav");	
		soundURL[12] = getClass().getResource("/sound/dialogue.wav");	
		soundURL[13] = getClass().getResource("/sound/bossFightFaseUno.wav");	



		setMusic(MENU_MUSIC);
		setSE(COLPO);
	}
	
	// per suonare una musica, prima bisogna dire alla clip quale file prendere
	public void setMusic(int i) {
		try {								
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			music = AudioSystem.getClip();
			music.open(ais);
		}
		catch(LineUnavailableException e) {
			e.printStackTrace();
			music.close();
			System.out.println("problemi gravi con la musica");
		} 
		catch (IOException e) {
			music.close();
			System.out.println("problemi gravi con la musica");
			e.printStackTrace();
		} 
		catch (UnsupportedAudioFileException e) {
			music.close();
			System.out.println("problemi gravi con la musica");
			e.printStackTrace();
		}
	}
	
	//idem per gli effetti
	public void setSE(int i) {
		try {								
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			soundEffect = AudioSystem.getClip();
			soundEffect.open(ais);
		}
		catch(LineUnavailableException e) {
			e.printStackTrace();
			soundEffect.close();
			System.out.println("problemi gravi col sound effect");
		} 
		catch (IOException e) {
			soundEffect.close();
			System.out.println("problemi gravi col sound effect");
			e.printStackTrace();
		} 
		catch (UnsupportedAudioFileException e) {
			soundEffect.close();
			System.out.println("problemi gravi col sound effect");
			e.printStackTrace();
		}
	}
	
	public void playMusic(int i) {
		setMusic(i);
		music.start();	
	}
	
	public void loopMusic(int i) {
		setMusic(i);
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		setSE(i);
		soundEffect.start();
		setSEVolume(seVolume);
	}
	
	public void setSEVolume(float v) {
		if (v > 0f && v < 1f) {
			this.seVolume = v;
	    	try {
	    		FloatControl gainControl = (FloatControl) soundEffect.getControl(FloatControl.Type.MASTER_GAIN);   
	    	
	    		float controlValue = 20f * (float) Math.log10(v); // siccome il suono è in decibel, bisogna convertirlo in lineare
	    	
		    	if(controlValue > -80)		//per controllo, sennò viene un numero troppo basso
		    		gainControl.setValue(controlValue);	
		    	
		    	if(v < 0.015f)
		    		gainControl.setValue(gainControl.getMinimum());
		    	
		    }
	    	catch(IllegalArgumentException iae) {
	    		iae.printStackTrace();
	    		System.out.println("problemi con i suond effects");
	    	}
		}
	}
		
	
	// metodo che funziona bene
	public void setMusicVolume(float v) {
		try {
			if (v > 0f && v < 1f) {
				this.musicVolume = v;
		    	
		    	FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);   
		    	
		    	float controlValue = 20f * (float) Math.log10(v); // siccome il suono è in decibel, bisogna convertirlo in lineare
		    	
		    	if(controlValue > -80)		//per controllo, sennò viene un numero troppo basso
		    		gainControl.setValue(controlValue);	
		    	
		    	if(v < 0.015f)
		    		gainControl.setValue(gainControl.getMinimum());
		    }
		}
		catch(IllegalArgumentException iae) {
    		iae.printStackTrace();
    		System.out.println("problemi con il volume della musica");
    	}
	}

	public float getMusicVolume() {
		return musicVolume;
	}
	
	public float getSEVolume() {
		return seVolume;
	}
	
}
