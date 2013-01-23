package jp.fedom.android.musicalarm.activity;

import java.util.ArrayList;

import jp.fedom.android.musicalarm.R;
import jp.fedom.android.musicalarm.item.ConfigItem;
import jp.fedom.android.musicalarm.util.bluetooth.BluetoothA2DPWrapper;
import jp.fedom.android.musicalarm.util.music.MusicWapper;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This is dummy comment.
 * 
 * @author taka2
 */
public final class MainActivity extends Activity {

	/* for logging */
	public static final String TAG = "MainActivity";

	private class ConfigAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int index) {
			return dataList.get(index);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater
						.inflate(R.layout.configlist_layout, null);
			}
			ConfigItem citem = (ConfigItem) getItem(position);
			if (citem != null) {
				((TextView) convertView.findViewById(R.id.config_title_text))
						.setText(citem.getTitle());
				((TextView) convertView.findViewById(R.id.config_time_text))
						.setText(citem.getTimeString());
				((TextView) convertView.findViewById(R.id.config_music_text))
						.setText(citem.getMusicFilePath());
			}
			return convertView;
		}

	}

	ListView listview;
	ArrayList<ConfigItem> dataList = new ArrayList<ConfigItem>(); 
	ConfigAdapter adapter;
	@Override
	/**
	 * This is dummy comment.
	 * TODO:describe comment
	 */
	public void onCreate(final Bundle savedState /* =savedInstanceState */) {
		super.onCreate(savedState);
	   setContentView(R.layout.activity_main);
	   listview = (ListView)findViewById(R.id.ConfigList);
	    dataList.add(new ConfigItem());
	    dataList.add(new ConfigItem());
	    dataList.add(new ConfigItem());
	    dataList.add(new ConfigItem());
	    dataList.add(new ConfigItem());
	    adapter = new ConfigAdapter();
	  adapter.notifyDataSetChanged();
	   listview.setAdapter(adapter);

	}

	@Override
	/**
	 * This is dummy comment.
	 * TODO:describe comment
	 */
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	/**
	 * This is dummy comment.
	 * TODO:describe comment
	 */
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void onClickStartMusic(View v){
		BluetoothA2DPWrapper b = BluetoothA2DPWrapper.getInstance();
		b.connect("30:F9:ED:8F:35:B0");
		MusicWapper m = MusicWapper.getInstance();
		m.start((AudioManager)getSystemService(Context.AUDIO_SERVICE));
		
	}
	public void onClickStopMusic(View v){
		BluetoothA2DPWrapper b = BluetoothA2DPWrapper.getInstance();
		b.disconnect("30:F9:ED:8F:35:B0");
		MusicWapper m = MusicWapper.getInstance();
		m.stop();
	}

}
