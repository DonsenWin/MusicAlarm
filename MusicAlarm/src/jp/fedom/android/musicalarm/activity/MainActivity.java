package jp.fedom.android.musicalarm.activity;

import java.util.ArrayList;
import java.util.List;

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
	
	/* for test MacAddress of Speaker*/
	private static final String SPEAKER_MA = "30:F9:ED:8F:35:B0";

	private static ListView listview;
	private static List<ConfigItem> dataList;
	private static ConfigAdapter adapter;

	private class ConfigAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(final int index) {
			return dataList.get(index);
		}

		@Override
		public long getItemId(final int index) {
			// TODO : what is ItemID for adapter?
			return index;
		}

		@Override
		public View getView(final int position,final View convertView, final ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view   = inflater
						.inflate(R.layout.configlist_layout, null);
			}
			final ConfigItem citem = (ConfigItem) getItem(position);
			if (citem != null) {
				((TextView) convertView.findViewById(R.id.config_title_text))
						.setText(citem.getTitle());
				((TextView) convertView.findViewById(R.id.config_time_text))
						.setText(citem.getTimeString());
				((TextView) convertView.findViewById(R.id.config_music_text))
						.setText(citem.getMusicFilePath());
			}
			return view;
		}

	}

	@Override
	/**
	 * This is dummy comment.
	 * TODO:describe comment
	 */
	public void onCreate(final Bundle savedState /* =savedInstanceState */) {
		super.onCreate(savedState);
		setContentView(R.layout.activity_main);
		dataList = new ArrayList<ConfigItem>();
		listview = (ListView) findViewById(R.id.ConfigList);
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

	/**
	 * This is dummy comment.
	 * TODO:describe comment
	 * @param v
	 */
	public void onClickStartMusic(final View view) {
		BluetoothA2DPWrapper.getInstance().connect(SPEAKER_MA);
		MusicWapper.getInstance().start((AudioManager) getSystemService(Context.AUDIO_SERVICE));
	}

	/**
	 * This is dummy comment.
	 * TODO:describe comment
	 * @param v
	 */
	public void onClickStopMusic(final View view) {
		BluetoothA2DPWrapper.getInstance().disconnect(SPEAKER_MA);
		MusicWapper.getInstance().stop();
	}

}
