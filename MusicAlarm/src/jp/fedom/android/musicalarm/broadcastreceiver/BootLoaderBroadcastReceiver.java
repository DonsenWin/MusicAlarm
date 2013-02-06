package jp.fedom.android.musicalarm.broadcastreceiver;

import jp.fedom.android.musicalarm.service.AlarmRegisterService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This is dummy comment. TODO:describe comment
 */
public final class BootLoaderBroadcastReceiver extends BroadcastReceiver {

    @Override
    /**
     * This is dummy comment.
     * TODO:describe comment
     */
    public void onReceive(final Context context, final Intent arg1) {
        Intent intent = new Intent(context, AlarmRegisterService.class);
        context.startService(intent);
    }
}
