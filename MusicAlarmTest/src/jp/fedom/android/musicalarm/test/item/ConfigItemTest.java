package jp.fedom.android.musicalarm.test.item;

import java.util.ArrayList;

import org.json.JSONException;

import android.util.Log;

import jp.fedom.android.musicalarm.item.ConfigItem;
import junit.framework.TestCase;

public final class ConfigItemTest extends TestCase {

    /**
     * @param name
     */
    public ConfigItemTest(final String name) {
        super(name);
    }

    /**
     * 
     * TODO:describe comment 
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * 
     * TODO:describe comment 
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * 
     * TODO:describe comment 
     */
    public void test_innerJson_singleParse() {
        final String jsonString = "{\"title\" : \"sampletitle\", \"time\" : \"00:00\", \"path\" : \"\\/path\\/to\\/file\"}";
        ConfigItem item = null;
        try {
            item = ConfigItem.JsonParser.getInstance().parseSingle(jsonString);
        } catch (JSONException e) {
            fail();
        }
        assertEquals("sampletitle"   , item.getTitle());
        assertEquals("00:00"         , item.getTime());
        assertEquals("/path/to/file" , item.getPath());
    }

    /**
     * 
     * TODO:describe comment 
     */
    public void test_innerJson_mutilParse() {
        final String jsonString = "{\"body\" : " +
                                   "[" +
                                   "{\"title\" : \"sampletitle\",  \"time\" : \"00:00\", \"path\" : \"\\/path\\/to\\/file\"}," +
                                   "{\"title\" : \"sampletitle2\", \"time\" : \"01:00\", \"path\" : \"\\/path\\/to\\/file\\/2\"}" +
                                   "]" +
                                   "}";

        ArrayList<ConfigItem> itemList = null;
        try {
            itemList = ConfigItem.JsonParser.getInstance().parse(jsonString);
        } catch (JSONException e) {
            fail();
        }
        
        assertEquals("sampletitle"    , itemList.get(0).getTitle());
        assertEquals("00:00"          , itemList.get(0).getTime());
        assertEquals("/path/to/file"  , itemList.get(0).getPath());

        assertEquals("sampletitle2"   , itemList.get(1).getTitle());
        assertEquals("01:00"          , itemList.get(1).getTime());
        assertEquals("/path/to/file/2", itemList.get(1).getPath());
    
    }

    /**
     * 
     * TODO:describe comment 
     */
    public void test_innerJsonGen_single() {
        final String jsonString = "{\"time\":\"00:00\",\"title\":\"sampletitle\",\"path\":\"\\/path\\/to\\/file\"}";
        ConfigItem item = new ConfigItem();
        item.setTitle("sampletitle");
        item.setTime("00:00");
        item.setPath("/path/to/file");
        try {
             assertEquals(jsonString,ConfigItem.JsonGenerator.getInstance().genSingleJson(item));
        } catch (JSONException e) {
            fail();
        }
    }
    
    /**
     * 
     * TODO:describe comment 
     */
    public void test_innerJsonGen_multi() {
        final String jsonString = "{\"body\":[" +
                                  "{\"time\":\"00:00\",\"title\":\"sampletitle\",\"path\":\"\\/path\\/to\\/file\"}," +
                                  "{\"time\":\"01:00\",\"title\":\"sampletitle2\",\"path\":\"\\/path\\/to\\/file\\/2\"}]";
        
        ConfigItem item = new ConfigItem();
        item.setTitle("sampletitle");
        item.setTime("00:00");
        item.setPath("/path/to/file");
        ConfigItem item2 = new ConfigItem();
        item2.setTitle("sampletitle2");
        item2.setTime("01:00");
        item2.setPath("/path/to/file/2");
        ArrayList<ConfigItem> list = new ArrayList<ConfigItem>();
        list.add(item);
        list.add(item2);
        try {
            Log.d("DEBUG_jsonString",jsonString);
            Log.d("DEBUG_generatate",ConfigItem.JsonGenerator.getInstance().genJson(list));
             assertEquals(jsonString,ConfigItem.JsonGenerator.getInstance().genJson(list));
        } catch (JSONException e) {
            fail();
        }
    }

}
