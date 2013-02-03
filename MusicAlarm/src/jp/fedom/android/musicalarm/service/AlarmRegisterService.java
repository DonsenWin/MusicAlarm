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

	private void registerAlarm(){
        final ConfigPreference pref = new ConfigPreference(PreferenceManager.getDefaultSharedPreferences(this));
        ArrayList<ConfigItem> dataList = (ArrayList<ConfigItem>) pref.loadConfigItems();     
        (Toast.makeText(this,dataList.get(0).getTitle() , Toast.LENGTH_LONG)).show();
        
        Intent startIntent = new Intent(this, AlarmRingService.class); // ReceivedActivity���Ăяo���C���e���g���쐬
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent sender = PendingIntent.getService(this, 0, startIntent, 0); // �u���[�h�L���X�g�𓊂���PendingIntent�̍쐬

        Calendar calendar = Calendar.getInstance(); // Calendar�擾
        calendar.setTimeInMillis(System.currentTimeMillis()); // ���ݎ������擾
        calendar.add(Calendar.SECOND, INTERVAL); // ���������INTERVAL�b���ݒ�

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE); // AlramManager�擾
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender); // AlramManager��PendingIntent��o�^	
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
