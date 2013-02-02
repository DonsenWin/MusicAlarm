package jp.fedom.android.musicalarm.test.service;

import jp.fedom.android.musicalarm.service.AlarmService;
import android.content.ComponentName;
import android.content.Intent;
import android.test.ServiceTestCase;
import android.test.mock.MockApplication;

public class AlarmServiceTest extends ServiceTestCase {

	public AlarmServiceTest(Class serviceClass) {
		super(serviceClass);
	}
	
	@Override
	public void setUp() throws Exception{
		super.setUp();
	}
	
	/**
	 * not implement yet.
	 */
	public void test_sample(){
		MockApplication app = new MockApplication();
		Intent intent = new Intent(app,AlarmService.class);
		startService(intent);
	}

}
