package jp.fedom.android.musicalarm.test.service;

import jp.fedom.android.musicalarm.service.AlarmRegisterService;
import android.content.ComponentName;
import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.mock.MockApplication;

/**
 * This is dummy comment. TODO: update comment
 * 
 * @author taka2
 */
public final class AlarmRegisterServiceTest extends ServiceTestCase {

    public AlarmRegisterServiceTest(Class serviceClass) {
        super(serviceClass);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * not implement yet.
     */
    public void test_sample() {
        MockApplication app = new MockApplication();
        Intent intent = new Intent(app, AlarmRegisterService.class);
        startService(intent);
    }

}
