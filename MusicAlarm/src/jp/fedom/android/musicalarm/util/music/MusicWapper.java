package jp.fedom.android.musicalarm.util.music;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * This is dummy comment. TODO: update comment
 * 
 * @author taka2
 * 
 */
public enum MusicWapper {
    /** Singleton implement. */
    instance;

    /** tag for log. */
    private static final String TAG = "MusicWapper";

    /** Music Player. */
    private MediaPlayer musicPlayer;

    /** Audio Player. */
    private AudioManager audioManager;

    /**
     * for Singleton.
     * 
     * @return singleInstance of this class
     */
    public static MusicWapper getInstance() {
        return instance;
    }

    /**
     * getSystemService(Context.AUDIO_SERVICE).
     * 
     * @param manager
     *            please (AudioManager) getSystemService(Context.AUDIO_SERVICE)
     * @param path
     *            path to audio file
     */
    public void start(final AudioManager manager, final String path) {
        if (manager == null) {
            Log.w(TAG, "called start with null");
            return;
        }
        if (musicPlayer != null) {
            stop();
        }

        this.audioManager = manager;
        this.audioManager.setBluetoothScoOn(true);
        this.audioManager.setMode(AudioManager.MODE_IN_CALL);

        musicPlayer = new MediaPlayer();
        try {
            musicPlayer.setDataSource(path);
            musicPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            musicPlayer.prepare();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException", e);
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException", e);
        } catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException", e);
        } catch (IOException e) {
            Log.e(TAG, "IOException", e);
        }

        musicPlayer.start();
    }

    /**
     * This is dummy comment. TODO: update comment
     */
    public void stop() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }

        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.setBluetoothScoOn(false);
        }
    }

}
