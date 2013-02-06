package jp.fedom.android.musicalarm.service;

import java.util.ArrayList;
import java.util.Calendar;

import jp.fedom.android.musicalarm.item.ConfigItem;
import jp.fedom.android.musicalarm.item.ConfigPreference;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * dummy comment. TODO:update comment
 * 
 * @author taka
 */
public final class AlarmRegisterService extends Service {

    /**
     * dummy comment. TODO:update comment
     */
    public AlarmRegisterService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service", "called onCreate");
    }

    /**
     * dummy comment. TODO:update comment
     */
    private void registerAlarm() {
        final ConfigPreference pref = new ConfigPreference(PreferenceManager.getDefaultSharedPreferences(this));
        ArrayList<ConfigItem> dataList = (ArrayList<ConfigItem>) pref.loadConfigItems();
        StringBuilder set = new StringBuilder();

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        for (ConfigItem item : dataList) {
            if (item.isEnable()) {
                Intent startIntent = new Intent(this, AlarmRingService.class);
                startIntent.setType(String.valueOf(dataList.indexOf(item)));
                PendingIntent sender = PendingIntent
                        .getService(this, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                Calendar calendar = item.getDateType().getNextDate(Calendar.getInstance(), item.getTime());
                calendar.set(Calendar.SECOND, 0);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                set.append(item.getTime() + ",");
            }
        }
        if (set.length() != 0) {
            (Toast.makeText(this, "register alarm set:" + set.toString(), Toast.LENGTH_LONG)).show();
        } else {
            (Toast.makeText(this, "No alarm set", Toast.LENGTH_LONG)).show();

        }
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("Service", "called onStartCommand");

        registerAlarm();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(final Intent arg0) {
        Log.d("Service", "called onBind" + arg0.toString());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("onDestroy", "onDestroy");
        super.onDestroy();
    }

}
