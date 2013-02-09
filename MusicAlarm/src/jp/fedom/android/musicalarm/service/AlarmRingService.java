package jp.fedom.android.musicalarm.service;

import jp.fedom.android.musicalarm.activity.MainActivity;
import jp.fedom.android.musicalarm.util.bluetooth.BluetoothA2DPWrapper;
import jp.fedom.android.musicalarm.util.music.MusicWapper;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * dummy comment. TODO:update comment
 * 
 * @author taka
 */
public final class AlarmRingService extends Service {

    /** dummy comment. TODO:update comment */
    private static final String SPEAKER_MAC_AD = "30:F9:ED:8F:35:B0";
    /** for test. */
    private static final String MUSIC_FILE_PATH = "/mnt/sdcard/media/audio/01.mp3";

    @Override
    public IBinder onBind(final Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     */
    private void setupNotification() {
        Log.d("notification", "called setupNotification");
        final Notification notification = new Notification(android.R.drawable.btn_default, "start music",
                System.currentTimeMillis());
        final Intent i = new Intent(getApplicationContext(), MainActivity.class);
        final PendingIntent pend = PendingIntent.getActivity(this, 0, i, 0);
        // builder required above api 11
        notification.setLatestEventInfo(getApplicationContext(), "タイトル", "テキスト", pend);
        final NotificationManager mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mManager.notify(android.R.string.ok, notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        (Toast.makeText(this, "AlarmRingService Start", Toast.LENGTH_LONG)).show();

        BluetoothA2DPWrapper.getInstance().connect(SPEAKER_MAC_AD);
        MusicWapper.getInstance().start((AudioManager) getSystemService(Context.AUDIO_SERVICE), MUSIC_FILE_PATH);
        setupNotification();
    }

    @Override
    public void onDestroy() {
        Log.d("onDestroy", "onDestroy");
        BluetoothA2DPWrapper.getInstance().disconnect(SPEAKER_MAC_AD);
        MusicWapper.getInstance().stop();
        super.onDestroy();
    }

}
