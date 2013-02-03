package jp.fedom.android.musicalarm.test.item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;

import jp.fedom.android.musicalarm.item.ConfigItem;
import junit.framework.TestCase;

/**
 * dummy comment.
 * TODO:describe comment
 */
public final class ConfigItemTest extends TestCase {

    /**
     * @param name name
     */
    public ConfigItemTest(final String name) {
        super(name);
    }

    /**
     * dummy comment.
     * TODO:describe comment
     * @throws Exception exception
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * dummy comment.
     * TODO:describe comment
     * @throws Exception exception
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_DateType_daily_beforetime() {
        // set 10:00
        String configSetTime = "10:00";       
        
        // now = 2013-2-2 9:00(Sat) 
        Calendar now = Calendar.getInstance();
        now.set(2013,Calendar.FEBRUARY,2,9,00);
        
        Calendar next = ConfigItem.DateType.daily.getNextDate(now,configSetTime);
        
        // 2013-2-2 10:00(Sat) 
        assertEquals(Calendar.SATURDAY, next.get(Calendar.DAY_OF_WEEK));
        assertEquals(2013, next.get(Calendar.YEAR));
        assertEquals(Calendar.FEBRUARY, next.get(Calendar.MONTH));
        assertEquals(2, next.get(Calendar.DATE));
        assertEquals(10, next.get(Calendar.HOUR));
        assertEquals(00, next.get(Calendar.MINUTE));
    }

    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_DateType_daily_overtime() {
        // set 10:00
        String configSetTime = "10:00";       
                
        // 2013-2-2 11:00(Sat) 
        Calendar now = Calendar.getInstance();
        now.set(2013,Calendar.FEBRUARY,2,11,00);
        
        Calendar next = ConfigItem.DateType.daily.getNextDate(now,configSetTime);
        
        // 2013-2-3 10:00(Sun) 
        assertEquals(Calendar.SUNDAY, next.get(Calendar.DAY_OF_WEEK));
        assertEquals(2013, next.get(Calendar.YEAR));
        assertEquals(Calendar.FEBRUARY, next.get(Calendar.MONTH));
        assertEquals(3, next.get(Calendar.DATE));
        assertEquals(10, next.get(Calendar.HOUR));
        assertEquals(00, next.get(Calendar.MINUTE));
        
    }

    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_DateType_weekday_Sat_beforetime() {                
        // now : 2013-2-2 9:00(Sat) 
        Calendar now = Calendar.getInstance();
        now.set(2013,Calendar.FEBRUARY, 2,9,00);
        // set 10:00
        String configSetTime = "10:00";       
        
        Calendar next = ConfigItem.DateType.weekDay.getNextDate(now,configSetTime);
        
        // 2013-2-4 10:00(mon) 
        assertEquals(Calendar.MONDAY, next.get(Calendar.DAY_OF_WEEK));
        assertEquals(2013, next.get(Calendar.YEAR));
        assertEquals(Calendar.FEBRUARY, next.get(Calendar.MONTH));
        assertEquals(4, next.get(Calendar.DATE));
        assertEquals(10, next.get(Calendar.HOUR));
        assertEquals(00, next.get(Calendar.MINUTE));
        
    }

    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_DateType_weekday_Sat_overtime() {                
        // now : 2013-2-2 11:00(Sat) 
        Calendar now = Calendar.getInstance();
        now.set(2013,Calendar.FEBRUARY, 2,11,00);
        // set 10:00
        String configSetTime = "10:00";       
        
        Calendar next = ConfigItem.DateType.weekDay.getNextDate(now,configSetTime);
        
        // 2013-2-4 10:00(mon) 
        assertEquals(Calendar.MONDAY, next.get(Calendar.DAY_OF_WEEK));
        assertEquals(2013, next.get(Calendar.YEAR));
        assertEquals(Calendar.FEBRUARY, next.get(Calendar.MONTH));
        assertEquals(4, next.get(Calendar.DATE));
        assertEquals(10, next.get(Calendar.HOUR));
        assertEquals(00, next.get(Calendar.MINUTE));
        
    }

    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_DateType_dayoff_Sat_beforetime() {
        // now : 2013-2-2 9:00(Sat) 
        Calendar now = Calendar.getInstance();
        now.set(2013,Calendar.FEBRUARY, 2,9,00);

        // set 10:00
        String configSetTime = "10:00";       
        
        Calendar next = ConfigItem.DateType.dayOff.getNextDate(now,configSetTime);
        
        // 2013-2-2 10:00(Sat) 
        assertEquals(Calendar.SATURDAY, next.get(Calendar.DAY_OF_WEEK));
        assertEquals(2013, next.get(Calendar.YEAR));
        assertEquals(Calendar.FEBRUARY, next.get(Calendar.MONTH));
        assertEquals(2, next.get(Calendar.DATE));
        assertEquals(10, next.get(Calendar.HOUR));
        assertEquals(00, next.get(Calendar.MINUTE));
    }
    
    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_DateType_dayoff_Sat_overtime() {
        // now : 2013-2-2 11:00(Sat) 
        Calendar now = Calendar.getInstance();
        now.set(2013,Calendar.FEBRUARY, 2,11,00);

        // set 10:00
        String configSetTime = "10:00";       
        
        Calendar next = ConfigItem.DateType.dayOff.getNextDate(now,configSetTime);
        
        // 2013-2-3 10:00(Sun) 
        assertEquals(Calendar.SUNDAY, next.get(Calendar.DAY_OF_WEEK));
        assertEquals(2013, next.get(Calendar.YEAR));
        assertEquals(Calendar.FEBRUARY, next.get(Calendar.MONTH));
        assertEquals(3, next.get(Calendar.DATE));
        assertEquals(10, next.get(Calendar.HOUR));
        assertEquals(00, next.get(Calendar.MINUTE));
    }
    
    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_DateType_dayoff_Mon_beforetime() {
        // now : 2013-2-4 9:00(Mon) 
        Calendar now = Calendar.getInstance();
        now.set(2013,Calendar.FEBRUARY, 4,9,00);

        // set 10:00
        String configSetTime = "10:00";       
        
        Calendar next = ConfigItem.DateType.dayOff.getNextDate(now,configSetTime);
        
        // 2013-2-9 10:00(Sat) 
        assertEquals(Calendar.SATURDAY, next.get(Calendar.DAY_OF_WEEK));
        assertEquals(2013, next.get(Calendar.YEAR));
        assertEquals(Calendar.FEBRUARY, next.get(Calendar.MONTH));
        assertEquals(9, next.get(Calendar.DATE));
        assertEquals(10, next.get(Calendar.HOUR));
        assertEquals(00, next.get(Calendar.MINUTE));
    }
    
    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_innerJson_mutilParse() {
        final String jsonString =
                "{\"body\" : "
                 + "["
                 + "{\"enable\" : false, \"title\" : \"sampletitle\", "
                 +   " \"time\" : \"00:00\", \"path\" : \"\\/path\\/to\\/file\"},"
                 + "{\"enable\" : true, \"title\" : \"sampletitle2\", "
                 +   "\"time\" : \"01:00\", \"path\" : \"\\/path\\/to\\/file\\/2\"}"
                 + "]"
                 + "}";

        ArrayList<ConfigItem> itemList = null;
        try {
            itemList = (ArrayList<ConfigItem>) ConfigItem.JsonParser.getInstance().parse(jsonString);
        } catch (JSONException e) {
            fail();
        }

        assertEquals(false            , itemList.get(0).isEnable());
        assertEquals("sampletitle"    , itemList.get(0).getTitle());
        assertEquals("00:00"          , itemList.get(0).getTime());
        assertEquals("/path/to/file"  , itemList.get(0).getPath());

        assertEquals(true             , itemList.get(1).isEnable());
        assertEquals("sampletitle2"   , itemList.get(1).getTitle());
        assertEquals("01:00"          , itemList.get(1).getTime());
        assertEquals("/path/to/file/2", itemList.get(1).getPath());
    }

    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_innerJsonGen_multi() {
        final String jsonString = "{\"body\":["
                                   + "{\"enable\":false,\"time\":\"00:00\","
                                   + "\"title\":\"sampletitle\",\"path\":\"\\/path\\/to\\/file\"},"
                                   + "{\"enable\":true,\"time\":\"01:00\","
                                   + "\"title\":\"sampletitle2\",\"path\":\"\\/path\\/to\\/file\\/2\"}]}";

        ArrayList<ConfigItem> list = new ArrayList<ConfigItem>();
        list.add(new ConfigItem(false, "sampletitle",  "00:00", "/path/to/file"));
        list.add(new ConfigItem(true,  "sampletitle2", "01:00", "/path/to/file/2"));
        try {
            assertEquals(jsonString, ConfigItem.JsonGenerator.getInstance().genJson(list));
        } catch (JSONException e) {
            fail();
        }
    }

    /**
     * dummy comment.
     * TODO:describe comment
     */
    public void test_innerJsonGeneral() {
        ArrayList<ConfigItem> list = new ArrayList<ConfigItem>();
        list.add(new ConfigItem(false, "sampletitle",  "00:00", "/path/to/file"));
        list.add(new ConfigItem(true,  "sampletitle2", "01:00", "/path/to/file/2"));
       try {
            String jsonString = ConfigItem.JsonGenerator.getInstance().genJson(list);
            ArrayList<ConfigItem> list2 = (ArrayList<ConfigItem>) ConfigItem.JsonParser.getInstance().parse(jsonString);

            assertEquals(false             , list2.get(0).isEnable());
            assertEquals("sampletitle"     , list2.get(0).getTitle());
            assertEquals("00:00"           , list2.get(0).getTime());
            assertEquals("/path/to/file"   , list2.get(0).getPath());

            assertEquals(true              , list2.get(1).isEnable());
            assertEquals("sampletitle2"    , list2.get(1).getTitle());
            assertEquals("01:00"           , list2.get(1).getTime());
            assertEquals("/path/to/file/2" , list2.get(1).getPath());
        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }
    }
}
