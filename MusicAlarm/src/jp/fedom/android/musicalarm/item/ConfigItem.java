package jp.fedom.android.musicalarm.item;

import java.util.ArrayList;
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

    /** dummy comment. TODO:update comment */
    private boolean enable;
    /** dummy comment. TODO:update comment */
    private String title;
    /** dummy comment. TODO:update comment */
    private String time;
    /** dummy comment. TODO:update comment */
    private String path;

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
                 .key("enable")
                 .value(item.isEnable())
                 .key("time")
                 .value(item.getTime())
                 .key("title")
                 .value(item.getTitle())
                 .key("path")
                 .value(item.getPath())
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
                         jsonObject.getBoolean("enable"),
                         jsonObject.getString("title"),
                         jsonObject.getString("time"),
                         jsonObject.getString("path")
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
        this(false, "Initial Title", "00:00", "/path/to/file/path");
    }

    /**
     * dummy comment.
     * TODO: update comment
     * @param argEnable enable
     * @param argTitle title
     * @param argTime time
     * @param argPath path
     */
    public ConfigItem(final boolean argEnable, final String argTitle, final String argTime, final String argPath) {
        this.enable = argEnable;
        this.title = argTitle;
        this.time = argTime;
        this.path = argPath;
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
