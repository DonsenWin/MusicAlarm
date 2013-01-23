package jp.fedom.android.musicalarm.util.music;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * 
 * 
 * @author taka
 *
 */
public enum MusicWapper {
	instance;
	
	/* tag for log */
	private static final String TAG = "MusicWapper";
	
	/* for test */
	private static final String MUSIC_FILE_PATH = "/mnt/sdcard/media/audio/01.mp3";

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
	public void start(final AudioManager manager){
		
		if (manager == null){
			Log.w(TAG, "called start with null");
			return;
		}
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
			Log.e(TAG, "IllegalArgumentException", e);
		} catch (SecurityException e) {
			Log.e(TAG, "SecurityException", e);
		} catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException", e);
		} catch (IOException e) {
			Log.e(TAG, "IOException", e);
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
