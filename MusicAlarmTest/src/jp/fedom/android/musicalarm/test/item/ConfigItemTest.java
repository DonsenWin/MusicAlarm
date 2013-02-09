package jp.fedom.android.musicalarm.test.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;

import android.util.Log;

import jp.fedom.android.musicalarm.item.ConfigItem;
import jp.fedom.android.musicalarm.item.ConfigItem.DateType;
import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * dummy comment. TODO:describe comment
 */
public final class ConfigItemTest extends TestCase {

    /** sample : Thu 2013-02-07 00:30:09. */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("EEE yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    /**
     * Str2 calender.
     * 
     * @param value
     *            the value
     * @return the calendar
     */
    private static Calendar str2Calender(final String value) {
        Log.d("test", SDF.format(new Date()));
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(SDF.parse(value));
            return cal;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Instantiates a new config item test.
     * 
     * @param name
     *            name
     */
    public ConfigItemTest(final String name) {
        super(name);
    }

    /**
     * dummy comment. TODO:describe comment
     * 
     * @throws Exception
     *             exception
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * dummy comment. TODO:describe comment
     * 
     * @throws Exception
     *             exception
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_daily_beforetime() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Sat 2013-02-02 9:00:00");

        Calendar next = ConfigItem.DateType.daily.getNextDate(now, configSetTime);

        assertEquals("Sat 2013-02-02 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_daily_resetSecond() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Sat 2013-02-02 9:00:30");

        Calendar next = ConfigItem.DateType.daily.getNextDate(now, configSetTime);

        assertEquals("Sat 2013-02-02 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_daily_overtime() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Sat 2013-02-02 11:00:00");

        Calendar next = ConfigItem.DateType.daily.getNextDate(now, configSetTime);

        assertEquals("Sun 2013-02-03 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_daily_beforetime_pm() {
        String configSetTime = "23:00";
        Calendar now = str2Calender("Sat 2013-02-02 20:00:00");

        Calendar next = ConfigItem.DateType.daily.getNextDate(now, configSetTime);

        assertEquals("Sat 2013-02-02 23:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_weekday_Sat_beforetime() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Sat 2013-02-02 09:00:00");

        Calendar next = ConfigItem.DateType.weekDay.getNextDate(now, configSetTime);

        assertEquals("Mon 2013-02-04 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_weekday_Sat_overtime() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Sat 2013-02-02 11:00:00");

        Calendar next = ConfigItem.DateType.weekDay.getNextDate(now, configSetTime);

        assertEquals("Mon 2013-02-04 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_dayoff_Sat_beforetime() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Sat 2013-02-02 09:00:00");

        Calendar next = ConfigItem.DateType.dayOff.getNextDate(now, configSetTime);

        assertEquals("Sat 2013-02-02 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_dayoff_Sat_overtime() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Sat 2013-02-02 11:00:00");

        Calendar next = ConfigItem.DateType.dayOff.getNextDate(now, configSetTime);

        assertEquals("Sun 2013-02-03 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_DateType_dayoff_Mon_beforetime() {
        String configSetTime = "10:00";
        Calendar now = str2Calender("Mon 2013-02-04 09:00:00");

        Calendar next = ConfigItem.DateType.dayOff.getNextDate(now, configSetTime);

        assertEquals("Sat 2013-02-09 10:00:00", SDF.format(new Date(next.getTimeInMillis())));
    }

    /**
     * dummy comment. TODO:describe comment
     */
    public void test_innerJsonGeneral() {
        ArrayList<ConfigItem> sourceList = new ArrayList<ConfigItem>();
        sourceList.add(new ConfigItem(false, "sampletitle", "00:00", "/path/to/file", DateType.daily));
        sourceList.add(new ConfigItem(true, "sampletitle2", "01:00", "/path/to/file/2", DateType.weekDay));
        try {
            final String jsonString = ConfigItem.JsonGenerator.getInstance().genJson(sourceList);
            ArrayList<ConfigItem> createdFromJsonList = (ArrayList<ConfigItem>) ConfigItem.JsonParser.getInstance()
                    .parse(jsonString);

            assertTrue(createdFromJsonList.get(0).equals(
                    new ConfigItem(false, "sampletitle", "00:00", "/path/to/file",
                    DateType.daily)));
            assertTrue(createdFromJsonList.get(1).equals(
                    new ConfigItem(true, "sampletitle2", "01:00", "/path/to/file/2",
                    DateType.weekDay)));
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
    }
}
