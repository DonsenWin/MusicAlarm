package jp.fedom.android.musicalarm.item;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

public class ConfigItem {

    private String title;
    private String time;
    private String path;
    
    public enum JsonGenerator{
        /** singleton implement.*/
        jsonGenInstance;
        
        public static JsonGenerator getInstance(){
            return jsonGenInstance;
        }
        
        public String genSingleJson(ConfigItem item) throws JSONException{
             return  new JSONStringer()
             .object()
                 .key("time")
                 .value(item.getTime())
                 .key("title")
                 .value(item.getTitle())
                 .key("path")
                 .value(item.getPath())
             .endObject()
             .toString();
        }

        public String genJson(ArrayList<ConfigItem> list) throws JSONException {
            JSONArray array = new JSONArray();
            JSONStringer stringer = new JSONStringer();
            stringer.object().key("body").array();
            for(ConfigItem item : list){
                array.put(stringer
                 .object()
                 .key("time")
                 .value(item.getTime())
                 .key("title")
                 .value(item.getTitle())
                 .key("path")
                 .value(item.getPath())
                 .endObject());
            }
            stringer.endArray();
            return stringer.toString();
        }
        
    }
    
    public enum JsonParser{
        /** singleton implement.*/
        jsonParserInstance;
        
        public static JsonParser getInstance(){
            return jsonParserInstance;
        }
        
        public ConfigItem parseSingle(String jsonString) throws JSONException{
            JSONObject rootObject = new JSONObject(jsonString);
            ConfigItem item = new ConfigItem();
            item.setTime(rootObject.getString("time"));
            item.setTitle(rootObject.getString("title"));
            item.setPath(rootObject.getString("path"));
            return item;
        }

        public ArrayList<ConfigItem> parse(String jsonString) throws JSONException{
            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray array = rootObject.getJSONArray("body");
            ArrayList<ConfigItem> list = new ArrayList<ConfigItem>();
            for(int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                ConfigItem item = new ConfigItem();
                item.setTime(jsonObject.getString("time"));
                item.setTitle(jsonObject.getString("title"));
                item.setPath(jsonObject.getString("path"));
                list.add(item);
            }
            return list;
        }
    }
    
    public ConfigItem(){
        this.title="Initial Title";
        this.time="Initial TimeString";
        this.path="/path/to/file/path";
    }
                      
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
}
