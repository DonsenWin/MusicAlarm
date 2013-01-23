package jp.fedom.android.musicalarm.util.music;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;

/**
 * 
 * @author taka
 *
 */
public enum MusicWapper {
	instance;
	
	/* for test */
	private final String MUSIC_FILE_PATH = "/mnt/sdcard/media/audio/01.mp3";

	/* Music Player TODO:create class */
	private MediaPlayer player;

	/* Audio Player TODO:create class */
	private AudioManager manager;
	
	/**
	 * for Singleton
	 * @return singleInstance of this class
	 */
	public static MusicWapper getInstance() {
		return instance;
	}
	
	/**
	 * getSystemService(Context.AUDIO_SERVICE)
	 * @param manager
	 */
	public void start(AudioManager manager){ 
		if (manager != null || player != null) {
			stop();
		}

		this.manager =  manager;
		this.manager.setBluetoothScoOn(true);
		this.manager.setMode(AudioManager.MODE_IN_CALL);

		player = new MediaPlayer();
		try {
			player.setDataSource(MUSIC_FILE_PATH);
			player.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
			player.prepare();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		player.start();
	}
	
	public void stop(){
		if (player != null) {
			player.stop();
		}

		if (manager != null) {
			manager.setMode(AudioManager.MODE_NORMAL);
			manager.setBluetoothScoOn(false);
		}
	}

}
