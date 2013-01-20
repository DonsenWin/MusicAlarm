package jp.fedom.android.musicalarm.test;

import junit.framework.TestSuite;

import android.test.InstrumentationTestRunner;
import android.test.suitebuilder.TestSuiteBuilder;

public class AllTests extends InstrumentationTestRunner {

	/**
	 * Override this to define all of the tests to run in your package.
	 * @see android.test.InstrumentationTestRunner#getAllTests()
	 */
	@Override
	public TestSuite getAllTests(){
		super.getAllTests();
		return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
	}
	
	/**
	 * Override this to provide access to the class loader of your package.
	 * @see android.test.InstrumentationTestRunner#getAllTests()
	 */
	@Override
	public ClassLoader getLoader(){
		return super.getLoader();
	}

}

