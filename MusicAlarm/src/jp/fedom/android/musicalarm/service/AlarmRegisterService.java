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

public class AlarmRegisterService extends Service {
    /** dummy comment. TODO:update comment */
    private static final int INTERVAL = 10;

	public AlarmRegisterService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Service", "called onCreate");
	}

	private void registerAlarm() {
		final ConfigPreference pref = new ConfigPreference(
				PreferenceManager.getDefaultSharedPreferences(this));
		ArrayList<ConfigItem> dataList = (ArrayList<ConfigItem>) pref
				.loadConfigItems();
		StringBuilder set = new StringBuilder();

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		for (ConfigItem item : dataList) {
			if(item.isEnable()){
			Intent startIntent = new Intent(this, AlarmRingService.class);
			startIntent.setType(String.valueOf(dataList.indexOf(item)));
			PendingIntent sender = PendingIntent.getService(this, 0,startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			Calendar calendar = item.getDateType().getNextDate(Calendar.getInstance(), item.getTime());
			calendar.set(Calendar.SECOND,0);
			am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
			set.append(item.getTime() + ",");
			}
		}
		if(set.length() != 0){
			(Toast.makeText(this, "register alarm set:" + set.toString(), Toast.LENGTH_LONG)).show();
		}else{
			(Toast.makeText(this, "No alarm set", Toast.LENGTH_LONG)).show();
			
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d("Service", "called onStartCommand");
		
		registerAlarm();
		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d("Service", "called onBind" + arg0.toString());
		return null;
	}

	@Override
	public void onDestroy() {
		Log.d("onDestroy", "onDestroy");
		super.onDestroy();
	}

}
