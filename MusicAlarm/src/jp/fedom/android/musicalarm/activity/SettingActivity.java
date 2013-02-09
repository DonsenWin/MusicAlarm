package jp.fedom.android.musicalarm.activity;

import jp.fedom.android.musicalarm.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Menu;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingActivity.
 */
public final class SettingActivity extends PreferenceActivity {

    /** The Constant TAG. */
    private static final String TAG = "SettingActivity";

    /*
     * (non-Javadoc)
     * 
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedState) {
        super.onCreate(savedState);
        addPreferencesFromResource(R.layout.activity_setting);
        final Intent intent = getIntent();
        final int defaultInt = -1;
        Log.d(TAG , "config index= " + intent.getIntExtra("configIndex", defaultInt));
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_setting, menu);
        return true;
    }

}
