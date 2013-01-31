package jp.fedom.android.musicalarm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {

	public AlarmService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Service", "called onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d("Service", "called onStartCommand");
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
