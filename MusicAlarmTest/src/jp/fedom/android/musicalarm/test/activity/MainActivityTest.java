package jp.fedom.android.musicalarm.test.activity;

import jp.fedom.android.musicalarm.activity.MainActivity;
import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;

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


    /**
     * This is dummy comment.
     * TODO: update comment
     * @throws Exception dummy comment.
     */
    @Override
    protected final void setUp() throws Exception {
        super.setUp();
        targetActivity = getActivity();
        instrumention  = getInstrumentation();
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
    public void test_sample() {

    }

}
