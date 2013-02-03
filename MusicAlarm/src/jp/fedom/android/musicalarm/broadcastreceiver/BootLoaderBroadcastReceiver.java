package jp.fedom.android.musicalarm.broadcastreceiver;

import jp.fedom.android.musicalarm.service.AlarmRegisterService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootLoaderBroadcastReceiver extends BroadcastReceiver {
 
	@Override
    public void onReceive(Context context, Intent arg1) {
       Intent intent = new Intent(context, AlarmRegisterService.class); 
       context.startService(intent);
    }
}

