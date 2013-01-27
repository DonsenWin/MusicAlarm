package jp.fedom.android.musicalarm.test;

import junit.framework.TestSuite;

import android.test.InstrumentationTestRunner;
import android.test.suitebuilder.TestSuiteBuilder;

/**
 * This is dummy comment.
 * TODO: update comment
 * @author taka2
 */
public final class AllTests extends InstrumentationTestRunner {

    @Override
    /**
     * Override this to define all of the tests to run in your package.
     * @see android.test.InstrumentationTestRunner#getAllTests()
     */
    public TestSuite getAllTests() {
        super.getAllTests();
        return new TestSuiteBuilder(AllTests.class)
                   .includeAllPackagesUnderHere()
                   .build();
    }

    @Override
    /**
     * Override this to provide access to the class loader of your package.
     * @see android.test.InstrumentationTestRunner#getAllTests()
     */
    public ClassLoader getLoader() {
        return super.getLoader();
    }

}

