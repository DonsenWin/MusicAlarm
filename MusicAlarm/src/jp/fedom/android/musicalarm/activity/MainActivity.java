package jp.fedom.android.musicalarm.activity;

import java.util.ArrayList;

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
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    private ArrayList<ConfigItem> dataList = new ArrayList<ConfigItem>();

    /** dummy comment. TODO:update comment */
    private ConfigAdapter adapter;

    /** the callback received when the user "sets" the time in the dialog. */
    private TimePickerListenerWrapper mTimeSetListener = new TimePickerListenerWrapper();

    /**
     * dummy comment. TODO:update comment
     * 
     * @author taka
     */
    private class TimePickerListenerWrapper implements TimePickerDialog.OnTimeSetListener {

        /** dummy comment. TODO:update comment */
        private static final int INVALID = -1;
        /** dummy comment. TODO:update comment */
        private int itemPosition = INVALID;

        /**
         * 
         * dummy comment. TODO:update comment
         * 
         * @author taka
         * @param itemPositionArg
         *            itemPosition for onTimeSet
         */
        public void setItemPosition(final int itemPositionArg) {
            this.itemPosition = itemPositionArg;
        }

        @Override
        /**
         * dummy comment. TODO:update comment
         * 
         * @author taka
         */
        public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
            Log.d("timepicker", "onTimeset called");
            if (itemPosition != INVALID) {
                dataList.get(itemPosition).setTime(
                        String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                Log.d("timepicker", "time set to " + itemPosition + "," + String.format("%02d", hourOfDay) + ":"
                        + String.format("%02d", minute));
                itemPosition = INVALID;
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * dummy comment. TODO:update comment
     * 
     * @author taka
     */
    private class ConfigAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public ConfigItem getItem(final int index) {
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
                ((TextView) view.findViewById(R.id.config_title_text)).setText(citem.getTitle());
                ((TextView) view.findViewById(R.id.config_time_text)).setText(citem.getTime());
                ((TextView) view.findViewById(R.id.config_music_text)).setText(citem.getPath());
                ((ToggleButton) view.findViewById(R.id.config_enable_toggle)).setChecked(citem.isEnable());

                view.findViewById(R.id.config_music_text).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Log.i("Click", "music_text");
                    }
                });
                view.findViewById(R.id.config_time_text).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Log.i("Click", "config_time_text  index : " + dataList.indexOf(view));
                        mTimeSetListener.setItemPosition(listview.getPositionForView(view));
                        final String configTime = dataList.get(listview.getPositionForView(view)).getTime();

                        int hour = Integer.valueOf(configTime.split(":")[0]);
                        int minute = Integer.valueOf(configTime.split(":")[1]);

                        TimePickerDialog tpDialog = new TimePickerDialog(MainActivity.this, mTimeSetListener, hour,
                                minute, true);
                        tpDialog.show();
                        listview.invalidate();
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_save:
            saveConfig();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This is dummy comment.
     * 
     * @param v
     *            TODO:describe comment
     */
    public void onClickStartMusic(final View v) {
        Log.d("click", "called onClickStartMusic");
        startService(new Intent(MainActivity.this, AlarmRingService.class));
    }

    /**
     * This is dummy comment.
     * 
     * @param v
     *            TODO:describe comment
     */
    public void onClickStopMusic(final View v) {
        Log.d("click", "called onClickStopMusic");
        stopService(new Intent(MainActivity.this, AlarmRingService.class));
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @param v
     *            view
     */
    public void onClickStartService(final View v) {
        Log.d("click", "called onClickStartService");
        startService(new Intent(MainActivity.this, AlarmRegisterService.class));
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @param v
     *            view
     */
    public void onClickStopService(final View v) {
        Log.d("click", "called onClickStopService");
        stopService(new Intent(MainActivity.this, AlarmRegisterService.class));
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @return if alarm servive is running, return true;
     */
    private boolean isAlarmServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AlarmRegisterService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This is dummy comment. TODO:describe comment
     * 
     * @param v
     *            view
     */
    public void onClickCheckService(final View v) {
        Log.d("click", "called onClickCheckService");
        String str = isAlarmServiceRunning() ? "service is running" : "service is NOT running";
        (Toast.makeText(this, str, Toast.LENGTH_SHORT)).show();
    }

}
