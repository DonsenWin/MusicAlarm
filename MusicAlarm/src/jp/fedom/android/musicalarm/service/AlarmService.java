package jp.fedom.android.musicalarm.service;

import java.util.ArrayList;
import java.util.Calendar;

import jp.fedom.android.musicalarm.activity.MusicActivity;
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

public class AlarmService extends Service {
    /** dummy comment. TODO:update comment */
    private static final int INTERVAL = 10;

	public AlarmService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Service", "called onCreate");
	}

	private void registerAlarm(){
        final ConfigPreference pref = new ConfigPreference(PreferenceManager.getDefaultSharedPreferences(this));
        ArrayList<ConfigItem> dataList = (ArrayList<ConfigItem>) pref.loadConfigItems();     
        (Toast.makeText(this,dataList.get(0).getTitle() , Toast.LENGTH_LONG)).show();
        
        Intent startIntent = new Intent(this, MusicActivity.class); // ReceivedActivityを呼び出すインテントを作成
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent sender = PendingIntent.getActivity(this, 0, startIntent, 0); // ブロードキャストを投げるPendingIntentの作成

        Calendar calendar = Calendar.getInstance(); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
        calendar.add(Calendar.SECOND, INTERVAL); // 現時刻よりINTERVAL秒後を設定

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE); // AlramManager取得
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender); // AlramManagerにPendingIntentを登録	
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
