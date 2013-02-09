package jp.fedom.android.musicalarm.activity;

import java.util.ArrayList;
import java.util.List;

import jp.fedom.android.musicalarm.R;
import jp.fedom.android.musicalarm.item.ConfigItem;
import jp.fedom.android.musicalarm.item.ConfigPreference;
import jp.fedom.android.musicalarm.service.AlarmRegisterService;
import jp.fedom.android.musicalarm.service.AlarmRingService;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

// TODO: Auto-generated Javadoc
/**
 * This is dummy comment. TODO: update comment
 * 
 * @author taka2
 */
public final class MainActivity extends Activity {

    /** for logging. */
    public static final String TAG = "MainActivity";

    /** dummy comment. TODO:update comment */
    private ListView listview;

    /** dummy comment. TODO:update comment */
    private List<ConfigItem> dataList = new ArrayList<ConfigItem>();

    /** dummy comment. TODO:update comment */
    private ConfigAdapter adapter;

    /**
     * dummy comment. TODO:update comment
     * 
     * @author taka
     */
    private class ConfigAdapter extends BaseAdapter {

        /*
         * (non-Javadoc)
         * 
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            return dataList.size();
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public ConfigItem getItem(final int index) {
            return dataList.get(index);
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(final int itemId) {
            return itemId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.configlist_layout, null);
            }
            final ConfigItem citem = (ConfigItem) getItem(position);
            if (citem != null) {
                ((TextView) view.findViewById(R.id.config_title_text)).setText(citem.getTitle());
                ((TextView) view.findViewById(R.id.config_time_text)).setText(citem.getTime());
                ((TextView) view.findViewById(R.id.config_music_text)).setText(citem.getPath());
                ((ToggleButton) view.findViewById(R.id.config_enable_toggle)).setChecked(citem.isEnable());
            }
            return view;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    /**
     * This is dummy comment.
     * TODO:describe comment
     */
    public void onCreate(final Bundle savedState /* =savedInstanceState */) {
        super.onCreate(savedState);
        Log.i("onCreate", "onCreate");

        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.ConfigList);

        final ConfigPreference pref = new ConfigPreference(PreferenceManager.getDefaultSharedPreferences(this));
        dataList = (ArrayList<ConfigItem>) pref.loadConfigItems();

        adapter = new ConfigAdapter();
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
                Log.d(TAG, "clicked item " + arg2);
                final Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                intent.putExtra("configIndex", arg2);
                startActivity(intent);
            }
        });
    }

    /**
     * This is dummy comment. TODO:describe comment
     */
    private void saveConfig() {
        Log.d("menu", "called save");
        final ConfigPreference pref = new ConfigPreference(PreferenceManager.getDefaultSharedPreferences(this));
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setEnable(
                    ((ToggleButton) listview.getChildAt(i).findViewById(R.id.config_enable_toggle)).isChecked());
        }
        pref.saveConfigItems(dataList);
        (Toast.makeText(this, "Saved", Toast.LENGTH_LONG)).show();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    /**
     * This is dummy comment.
     * TODO:describe comment
     */
    public boolean onOptionsItemSelected(final MenuItem item) {
        boolean returnValue = false;
        if (item.getItemId() == R.id.menu_save) {
            saveConfig();
            returnValue = true;
        } else {
            returnValue = super.onOptionsItemSelected(item);
        }
        return returnValue;
    }

    /**
     * This is dummy comment.
     * 
     * @param view
     *            TODO:describe comment
     */
    public void onClickStartMusic(final View view) {
        Log.d(TAG, "called onClickStartMusic");
        startService(new Intent(MainActivity.this, AlarmRingService.class));
    }

    /**
     * This is dummy comment.
     * 
     * @param v
     *            TODO:describe comment
     */
    public void onClickStopMusic(final View v) {
        Log.d(TAG, "called onClickStopMusic");
        stopService(new Intent(MainActivity.this, AlarmRingService.class));
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @param v
     *            view
     */
    public void onClickStartService(final View v) {
        Log.d(TAG, "called onClickStartService");
        startService(new Intent(MainActivity.this, AlarmRegisterService.class));
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @param v
     *            view
     */
    public void onClickStopService(final View v) {
        Log.d(TAG, "called onClickStopService");
        stopService(new Intent(MainActivity.this, AlarmRegisterService.class));
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @return if alarm servive is running, return true;
     */
    private boolean isAlarmServiceRunning() {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        boolean returnValue = false;
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AlarmRegisterService.class.getName().equals(service.service.getClassName())) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @param view
     *            view
     */
    public void onClickCheckService(final View view) {
        Log.d(TAG, "called onClickCheckService");
        final String str = isAlarmServiceRunning() ? "service is running" : "service is NOT running";
        (Toast.makeText(this, str, Toast.LENGTH_SHORT)).show();
    }

}
