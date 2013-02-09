package jp.fedom.android.musicalarm.test.service;

import jp.fedom.android.musicalarm.service.AlarmRegisterService;
import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.mock.MockApplication;

// TODO: Auto-generated Javadoc
/**
 * This is dummy comment. TODO: update comment
 * 
 * @author taka2
 */
public final class AlarmRegisterServiceTest extends ServiceTestCase<AlarmRegisterService> {

    /**
     * Instantiates a new alarm register service test.
     *
     * @param serviceClass the service class
     */
    public AlarmRegisterServiceTest(final Class<AlarmRegisterService> serviceClass) {
        super(serviceClass);
    }

    /* (non-Javadoc)
     * @see android.test.ServiceTestCase#setUp()
     */
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
