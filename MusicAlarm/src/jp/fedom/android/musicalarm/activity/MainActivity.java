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
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This is dummy comment.
 * TODO: update comment
 * @author taka2
 */
public final class MainActivity extends Activity {

	/* for logging */
	public static final String TAG = "MainActivity";
	private ListView listview;
	private ArrayList<ConfigItem> dataList = new ArrayList<ConfigItem>(); 
	private ConfigAdapter adapter;
	
	private static final String SPEAKER_MAC_AD = "30:F9:ED:8F:35:B0";

    // TODO: pickup for file
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

		public long getItemId(final int itemId) {
			return itemId;
		}

		@Override
		public View getView(final int position, final View convertView,final ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.configlist_layout, null);
			}
			final ConfigItem citem = (ConfigItem) getItem(position);
			if (citem != null) {
				((TextView) view.findViewById(R.id.config_title_text))
						.setText(citem.getTitle());
				((TextView) view.findViewById(R.id.config_time_text))
						.setText(citem.getTime());
				((TextView) view.findViewById(R.id.config_music_text))
						.setText(citem.getPath());
				view.findViewById(R.id.config_music_text).setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						Log.i("Click","music_text");
					}
				});
				view.findViewById(R.id.config_time_text).setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						Log.i("Click","config_time_text");
					}
				});
				view.findViewById(R.id.config_title_text).setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						Log.i("Click","config_title_text");
					}
				});
			}
			
			return view;
		}

	}
	
    // TODO: pickup for file
    class ListClickEvent implements AdapterView.OnItemClickListener {

        // onItemClickメソッドには、AdapterView(adapter)、選択した項目View(TextView)、選択された位置のint値、IDを示すlong値が渡される
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        	Log.i("Click","clicked List Item");
        	Intent intent = new Intent();
        	// start sum acticity
            startActivity(intent);
    	}
    	
    }
    // TODO: pickup for file
    class ListSelectEvent implements AdapterView.OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
        	Log.i("Click","onItemSelected");
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
        	Log.i("Click","onNothingSelected");
			// TODO Auto-generated method stub
			
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
		listview.setOnItemClickListener(new ListClickEvent());
		listview.setOnItemSelectedListener(new ListSelectEvent());
    	Log.i("onCreate","onCreate");


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

	
	public void onClickStartMusic(View v){
		BluetoothA2DPWrapper.getInstance().connect(SPEAKER_MAC_AD);
		MusicWapper.getInstance().start((AudioManager)getSystemService(Context.AUDIO_SERVICE));
	}
	public void onClickStopMusic(View v){
		BluetoothA2DPWrapper.getInstance().disconnect(SPEAKER_MAC_AD);
		MusicWapper.getInstance().stop();
	}
}
