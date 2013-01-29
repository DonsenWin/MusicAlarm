package jp.fedom.android.musicalarm.activity;

import java.util.ArrayList;
import java.util.Calendar;

import jp.fedom.android.musicalarm.R;
import jp.fedom.android.musicalarm.item.ConfigItem;
import jp.fedom.android.musicalarm.item.ConfigPreference;
import jp.fedom.android.musicalarm.util.bluetooth.BluetoothA2DPWrapper;
import jp.fedom.android.musicalarm.util.music.MusicWapper;

import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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

    /** for logging. */
    public static final String TAG = "MainActivity";

    /** dummy comment. TODO:update comment */
    private ListView listview;

    /** dummy comment. TODO:update comment */
    private ArrayList<ConfigItem> dataList = new ArrayList<ConfigItem>();

    /** dummy comment. TODO:update comment */
    private ConfigAdapter adapter;

    /** dummy comment. TODO:update comment */
    private static final String SPEAKER_MAC_AD = "30:F9:ED:8F:35:B0";

    /** dummy comment. TODO:update comment */
    private static final int INTERVAL = 60 * 60 * 6;

    /**
     * dummy comment.
     * TODO:update comment
     * @author taka
     */
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
        public View getView(final int position, final View convertView, final ViewGroup parent) {
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
                    public void onClick(final View view) {
                        Log.i("Click", "music_text");
                    }
                });
                view.findViewById(R.id.config_time_text).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Log.i("Click", "config_time_text");
                    }
                });
                view.findViewById(R.id.config_title_text).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Log.i("Click", "config_title_text");
                    }
                });
            }
            return view;
        }

    }

    /**
     * dummy comment.
     * @author taka
     * TODO: pickup for other file
     *
     */
    class ListClickEvent implements AdapterView.OnItemClickListener {

        /**
         * onItemClickメソッドには、AdapterView(adapter)、選択した項目View(TextView)、選択された位置のint値、IDを示すlong値が渡される.
         *
         * @param argPosition dummy
         * @param argView     dummy
         * @param argId       dummy
         * @param argAdapter  dummy
         *
         */
        public void onItemClick(final AdapterView<?> argAdapter,
                                final View argView,
                                final int argPosition,
                                final long argId) {
            Log.i("Click", "clicked List Item");
            final Intent intent = new Intent();
            // start sum acticity
            startActivity(intent);
        }
    }

    /**
     * dummy comment.
     * @author taka
     * TODO: pickup for other file
     *
     */
    class ListSelectEvent implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
            // TODO Auto-generated method stub
            Log.i("Click", "onItemSelected");
        }

        @Override
        public void onNothingSelected(final AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            Log.i("Click", "onNothingSelected");
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
        listview = (ListView) findViewById(R.id.ConfigList);

        final ConfigPreference pref = new ConfigPreference(PreferenceManager.getDefaultSharedPreferences(this));
        dataList = (ArrayList<ConfigItem>) pref.loadConfigItems();

        adapter = new ConfigAdapter();
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListClickEvent());
        listview.setOnItemSelectedListener(new ListSelectEvent());
        Log.i("onCreate", "onCreate");


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
     * @param v
     * TODO:describe comment
     */
    public void onClickStartMusic(final View v) {
        BluetoothA2DPWrapper.getInstance().connect(SPEAKER_MAC_AD);
        MusicWapper.getInstance().start((AudioManager) getSystemService(Context.AUDIO_SERVICE));
    }

    /**
     * This is dummy comment.
     * @param v
     * TODO:describe comment
     */
    public void onClickStopMusic(final View v) {
        BluetoothA2DPWrapper.getInstance().disconnect(SPEAKER_MAC_AD);
        MusicWapper.getInstance().stop();
    }

    /**
     * This is dummy comment.
     * @param v
     * TODO:describe comment
     */
   public void onClickStartAlarm(final View v) {
        Log.d("onClick", "called onClickStartAlarm");
        Intent intent = new Intent(this, MusicActivity.class); // ReceivedActivityを呼び出すインテントを作成
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent sender = PendingIntent.getActivity(this, 0, intent, 0); // ブロードキャストを投げるPendingIntentの作成

        Calendar calendar = Calendar.getInstance(); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
        calendar.add(Calendar.SECOND, INTERVAL); // 現時刻よりINTERVAL秒後を設定

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE); // AlramManager取得
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender); // AlramManagerにPendingIntentを登録
    }

    /**
     * This is dummy comment.
     * TODO:describe comment
     * @param v view
     */
    public void onClickStopAlarm(final View v) {
        Log.d("onClick", "called onClickStopAlarm");
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }
}
