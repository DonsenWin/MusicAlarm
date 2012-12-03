package jp.fedom.android.musicalarm.activity;

import java.io.IOException;
import java.util.Set;

import jp.fedom.android.musicalarm.R;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";
	public static MediaPlayer player;
	public static AudioManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		prepareBlueTooth();
		
		
	}

	private void prepareBlueTooth() {
		BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
		if(!bt.equals(null)){
			showMessage("bluetoothをサポートしています");
		}else{
			showMessage("bluetoothをサポートしていません。");
			return;
		}
		
		if(bt.isEnabled()){			
			showMessage("bluetoothは有効です。");
		}else{
			showMessage("bluetoothは無効です。");
		}
		
		Set<BluetoothDevice> pairedDevices = bt.getBondedDevices();
		for(BluetoothDevice device: pairedDevices){
			showMessage("name : " + device.getName() + " address : " + device.getAddress() +" status : " + device.getBondState());
		}
	}


	private void showMessage(String string) {
		((TextView)findViewById(R.id.message)).setText(((TextView)findViewById(R.id.message)).getText().toString() + "\n" +  string);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClickStartMusic(View view) {
		Log.v(TAG, "called onClickStartMusic");
		manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		manager.setBluetoothScoOn(true);
		manager.setMode(AudioManager.MODE_IN_CALL);

		player = new MediaPlayer();
		String path = "/mnt/sdcard/media/audio/01 情熱大陸2007.mp3";
		try {
			player.setDataSource(path);
			player.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
			player.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		player.start();

	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		onClickEndMusic(null);
	}
	
	public void onClickUpdateBlueToothStatus(View view){
		prepareBlueTooth();
	}

	public void onClickEndMusic(View view) {
		player.stop();
		manager.setMode(AudioManager.MODE_NORMAL);
		manager.setBluetoothScoOn(false);
	}

}
