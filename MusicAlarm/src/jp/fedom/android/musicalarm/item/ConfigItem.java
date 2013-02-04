package jp.fedom.android.musicalarm.item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;


/**
 * dummy comment.
 * TODO:update comment
 * @author taka
 */
public class ConfigItem {

	private static final String KEY_ENABLE = "enable";
	private static final String KEY_TIME = "time";
	private static final String KEY_TITLE = "title";
	private static final String KEY_PATH = "path";
	private static final String KEY_DATETYPE = "datetype";
    
    public enum DateType{
        daily,
        weekDay,
        dayOff,
        onceOnly;        
                
        public static DateType[] getCandidate(){
        	return  new DateType[]{daily,weekDay,dayOff,onceOnly};
        }
        
        public String toString(){
            return this.name();
        }
        
        public static DateType convert(String typeStr){
        	DateType[] candidates = getCandidate();
        	for(DateType type : candidates){
        		if(typeStr.equals(type.name())){
        			return type;
        		}
        	}
        	return daily;
        }
        
        
        /**
         * please call with Calendar.getInstance(tz).
         * @param now
         * @return
         */
        public Calendar getNextDate(Calendar now,String config) {
            Calendar next = (Calendar)now.clone();
            
            next.set(Calendar.HOUR_OF_DAY, Integer.parseInt(config.split(":")[0]));
            next.set(Calendar.MINUTE, Integer.parseInt(config.split(":")[1]));
            
            // 今日の設定時刻を過ぎていたら、次の日以降から 
            if(now.after(next)){
               next.add(Calendar.DATE, 1);
            }
                        
            switch (this) {
            case daily:
            case onceOnly:
                // nothing to do
                break;
            case weekDay:
                while(!isWeekDay(next)){
                    next.add(Calendar.DATE, 1);
                }
                break;
            case dayOff:
                while(isWeekDay(next)){
                    next.add(Calendar.DATE, 1);
                }
                break;
            }
            return next;
        }
        
        private boolean isWeekDay(Calendar targetDay){
            if (targetDay == null){
                return false;
            }
            
            if(targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY ||
               targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY ||
               targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ||
               targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ||
               targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                return true;        
            }else{
                return false;
            }
                
        }
        
    }
   
    /** dummy comment. TODO:update comment */
    private boolean enable;
    /** dummy comment. TODO:update comment */
    private String title;
    /** dummy comment. TODO:update comment */
    private String time;
    /** dummy comment. TODO:update comment */
    private String path;
    /** dummy comment. TODO:update comment */
    private DateType dateType;

    /**
     * dummy comment.
     * @author taka
     */
    public enum JsonGenerator {
        /** singleton implement.*/
        jsonGenInstance;

        /**
         * dummy comment.
         * TODO:update comment
         * @return singleton instance
         */
        public static JsonGenerator getInstance() {
            return jsonGenInstance;
        }

        /**
         * dummy comment.
         * TODO:update comment
         * @param list list from items
         * @return singleton instance
         * @throws JSONException if error.
         */
        public String genJson(final List<ConfigItem> list) throws JSONException {
            final JSONArray array = new JSONArray();
            final JSONStringer stringer = new JSONStringer();
            stringer.object().key("body").array();
            for (final ConfigItem item : list) {
                array.put(stringer
                 .object()
                 .key(KEY_ENABLE)
                 .value(item.isEnable())
                 .key(KEY_TIME)
                 .value(item.getTime())
                 .key(KEY_TITLE)
                 .value(item.getTitle())
                 .key(KEY_PATH)
                 .value(item.getPath())
                 .key(KEY_DATETYPE)
                 .value(item.getDateType().toString())
                 .endObject());
            }
            return stringer.endArray().endObject().toString();
        }
    }

    /**
     * dummy comment.
     * @author taka
     */
    public enum JsonParser {
        /** singleton implement.*/
        jsonParserInstance;

        /**
         * dummy comment.
         * @return singleton instance
         */
        public static JsonParser getInstance() {
            return jsonParserInstance;
        }

        /**
         * dummy comment.
         * TODO:update comment
         * @param jsonString jsonString
         * @return List
         * @throws JSONException if error.
         */
        public List<ConfigItem> parse(final String jsonString) throws JSONException {
            final JSONObject rootObject = new JSONObject(jsonString);
            final JSONArray array = rootObject.getJSONArray("body");
            final ArrayList<ConfigItem> list = new ArrayList<ConfigItem>();
            for (int i = 0; i < array.length(); i++) {
                final JSONObject jsonObject = array.getJSONObject(i);
                 list.add(new ConfigItem(
                         jsonObject.getBoolean(KEY_ENABLE),
                         jsonObject.getString(KEY_TITLE),
                         jsonObject.getString(KEY_TIME),
                         jsonObject.getString(KEY_PATH),
                         DateType.convert(jsonObject.getString(KEY_DATETYPE))
                         ));
            }
            return list;
        }
    }

    /**
     * dummy comment.
     * TODO: update comment
     */
    public ConfigItem() {
        this(false, "Initial Title", "00:00", "/mnt/sdcard/media/audio/01.mp3", DateType.daily);
    }

    /**
     * dummy comment.
     * TODO: update comment
     * @param argTitle title
     */
    public DateType getDateType() {
		return this.dateType;
	}

    /**
     * dummy comment.
     * TODO: update comment
     * @param argTitle title
     */
    public void setDateType(DateType argType) {
    	this.dateType = argType;
	}

	/**
     * dummy comment.
     * TODO: update comment
     * @param argEnable enable
     * @param argTitle title
     * @param argTime time
     * @param argPath path
     */
    public ConfigItem(final boolean argEnable, final String argTitle, final String argTime, final String argPath, final DateType argType) {
        this.enable = argEnable;
        this.title = argTitle;
        this.time = argTime;
        this.path = argPath;
        this.dateType = argType;
    }

    /**
     * dummy comment.
     * TODO: update comment
     * @return title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * dummy comment.
     * TODO: update comment
     * @param argTitle title
     */
    public final void setTitle(final String argTitle) {
        this.title = argTitle;
    }

    /**
     * dummy comment.
     * TODO: update comment
     * @return time
     */
    public final String getTime() {
        return time;
    }

    /**
     * dummy comment.
     * TODO: update comment
     * @param argTime time
     */
   public final void setTime(final String argTime) {
        this.time = argTime;
    }

   /**
    * dummy comment.
    * TODO: update comment
     * @return path to music file
    */
  public final String getPath() {
        return path;
    }

  /**
   * dummy comment.
   * TODO: update comment
   * @param argPath path
   */
  public final void setPath(final String argPath) {
        this.path = argPath;
    }

  /**
   * dummy comment.
   * TODO: update comment
     * @return enable
   */
    public final boolean isEnable() {
        return enable;
    }

  /**
    * dummy comment.
    * TODO: update comment
    * @param argEnable enable
    */
    public final void setEnable(final boolean argEnable) {
        this.enable = argEnable;
    }

}
