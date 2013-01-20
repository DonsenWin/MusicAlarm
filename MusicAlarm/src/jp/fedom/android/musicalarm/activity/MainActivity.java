package jp.fedom.android.musicalarm.activity;

import java.io.IOException;
import java.util.Set;

import jp.fedom.android.musicalarm.R;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author taka2
 * 
 */
public final class MainActivity extends Activity {

	public static final String TAG = "MainActivity";
	private MediaPlayer player;
	private AudioManager manager;

	@Override
	/**
	 * 
	 * TODO:describe comment 
	 */
	public final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		prepareBlueTooth();
	}

	/**
	 * 
	 * TODO:describe comment
	 */
	private final void prepareBlueTooth() {
		final BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
		if (bt == null) {
			showMessage("bluetoothをサポートしていません。");
			return;
		} else {
			showMessage("bluetoothをサポートしています");

			if (bt.isEnabled()) {
				showMessage("bluetoothは有効です。");
				final Set<BluetoothDevice> pairedDevices = bt.getBondedDevices();
				for (final BluetoothDevice device : pairedDevices) {
					showMessage("name : " + device.getName() + " address : "
							+ device.getAddress() + " status : "
							+ device.getBondState());
				}
			} else {
				showMessage("bluetoothは無効です。");
			}
		}

	}

	/**
	 * 
	 * TODO:describe comment
	 */
	private final void showMessage(final String string) {
		((TextView) findViewById(R.id.message))
				.setText(((TextView) findViewById(R.id.message)).getText()
						.toString() + "\n" + string);
	}

	@Override
	/**
	 * 
	 * TODO:describe comment 
	 */
	public final boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * 
	 * TODO:describe comment
	 */
	public final void onClickStartMusic(final View view) {
		Log.v(TAG, "called onClickStartMusic");
		if (manager != null || player != null) {
			onClickEndMusic(null);
		}

		manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		manager.setBluetoothScoOn(true);
		manager.setMode(AudioManager.MODE_IN_CALL);

		player = new MediaPlayer();
		final String path = "/mnt/sdcard/media/audio/01 情熱大陸2007.mp3";
		try {
			player.setDataSource(path);
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

		if (player != null) {
			player.start();
		}
	}

	@Override
	/**
	 * 
	 * TODO:describe comment 
	 */
	public final void onDestroy() {
		onClickEndMusic(null);

		super.onDestroy();
	}

	/**
	 * 
	 * TODO:describe comment
	 */
	public final void onClickUpdateBlueToothStatus(final View view) {
		prepareBlueTooth();
	}

	/**
	 * 
	 * TODO:describe comment
	 */
	public final void onClickEndMusic(final View view) {
		if (player != null) {
			player.stop();
		}

		if (manager != null) {
			manager.setMode(AudioManager.MODE_NORMAL);
			manager.setBluetoothScoOn(false);
		}
	}

}
