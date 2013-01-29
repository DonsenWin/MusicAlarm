package jp.fedom.android.musicalarm.activity;

import jp.fedom.android.musicalarm.R;
import jp.fedom.android.musicalarm.util.bluetooth.BluetoothA2DPWrapper;
import jp.fedom.android.musicalarm.util.music.MusicWapper;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

/**
 * This is dummy comment.
 * TODO: update comment
 * @author taka
 *
 */
public class MusicActivity extends Activity {

    /** dummy comment. TODO:update comment */
    private static final String SPEAKER_MAC_AD = "30:F9:ED:8F:35:B0";

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        BluetoothA2DPWrapper.getInstance().connect(SPEAKER_MAC_AD);
        MusicWapper.getInstance().start((AudioManager) getSystemService(Context.AUDIO_SERVICE));
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_music, menu);
        return true;
    }

}
