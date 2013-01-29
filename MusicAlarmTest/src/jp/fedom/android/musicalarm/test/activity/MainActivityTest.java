package jp.fedom.android.musicalarm.test.activity;

import java.util.ArrayList;

import jp.fedom.android.musicalarm.activity.MainActivity;
import jp.fedom.android.musicalarm.item.ConfigItem;
import jp.fedom.android.musicalarm.item.ConfigPreference;
import android.app.Activity;
import android.app.Instrumentation;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * This is dummy comment.
 * TODO: update comment
 * @author taka2
 */
public class MainActivityTest extends
  ActivityInstrumentationTestCase2<MainActivity> {

    /** Target Activity. */
    private Activity targetActivity;
    /** Test instruction. */
    private Instrumentation instrumention;

    /**
     * This is dummy comment.
     * TODO: update comment
     */
    public MainActivityTest() {
        super(MainActivity.class);
    }


    @Override
    /**
     * This is dummy comment.
     * TODO: update comment
     * @throws Exception dummy comment.
     */
    protected final void setUp() throws Exception {
        super.setUp();
        targetActivity = getActivity();
        instrumention  = getInstrumentation();

        Log.d("targetActivity", targetActivity.toString());
        Log.d("instrumention",  instrumention.toString());
    }


    /**
     * This is dummy comment.
     * TODO: update comment
     * @throws Exception dummy comment.
     */
    @Override
    protected final void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * This is dummy comment.
     * TODO: update comment
     */
    public final void test_sample() {
        ConfigPreference pref = new ConfigPreference(PreferenceManager.getDefaultSharedPreferences(targetActivity));
        ArrayList<ConfigItem> items = (ArrayList<ConfigItem>) pref.loadConfigItems();
        assertTrue(items.size() > 0);
    }

}
