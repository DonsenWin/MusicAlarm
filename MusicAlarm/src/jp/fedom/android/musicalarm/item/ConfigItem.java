package jp.fedom.android.musicalarm.item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * dummy comment. TODO:update comment
 * 
 * @author taka
 */
public class ConfigItem {

    /** dummy comment. TODO:update comment */
    private static final String KEY_ENABLE = "enable";
    /** dummy comment. TODO:update comment */
    private static final String KEY_TIME = "time";
    /** dummy comment. TODO:update comment */
    private static final String KEY_TITLE = "title";
    /** dummy comment. TODO:update comment */
    private static final String KEY_PATH = "path";
    /** dummy comment. TODO:update comment */
    private static final String KEY_DATETYPE = "datetype";

    /**
     * dummy comment. TODO:update comment
     * 
     * @author taka
     */
    public enum DateType {
        /** dummy comment. TODO:update comment */
        daily,
        /** dummy comment. TODO:update comment */
        weekDay,
        /** dummy comment. TODO:update comment */
        dayOff,
        /** dummy comment. TODO:update comment */
        onceOnly;

        /**
         * This is dummy comment. TODO:describe comment[
         * 
         * @return name of enum
         */
        public String toString() {
            return this.name();
        }

        /**
         * This is dummy comment. TODO:describe comment
         * 
         * @param typeStr
         *            name of enum
         * @return enum. if not exists, daily
         */
        public static DateType convert(final String typeStr) {
            DateType[] candidates = DateType.values();
            for (DateType type : candidates) {
                if (typeStr.equals(type.name())) {
                    return type;
                }
            }
            return daily;
        }

        /**
         * please call with Calendar.getInstance(tz).
         * 
         * @param now
         *            now.
         * @param config
         *            format: ^[0-9]{1,2}:[0-9]{1,2}$
         * @return next time which must alart
         */
        public Calendar getNextDate(final Calendar now, final String config) {
            Calendar next = (Calendar) now.clone();

            next.set(Calendar.HOUR_OF_DAY, Integer.parseInt(config.split(":")[0]));
            next.set(Calendar.MINUTE, Integer.parseInt(config.split(":")[1]));

            // reset second
            next.set(Calendar.SECOND, 0);

            // 今日の設定時刻を過ぎていたら、次の日以降から
            if (now.after(next)) {
                next.add(Calendar.DATE, 1);
            }

            switch (this) {
            case daily:
            case onceOnly:
                // nothing to do
                break;
            case weekDay:
                while (!isWeekDay(next)) {
                    next.add(Calendar.DATE, 1);
                }
                break;
            case dayOff:
                while (isWeekDay(next)) {
                    next.add(Calendar.DATE, 1);
                }
                break;
            default:
                // nothing to do
                break;
            }
            return next;
        }

        /**
         * dummy comment. TODO:update comment
         * 
         * @param targetDay
         *            check date
         * @return if target date is Mon - Fri, return true. if null, return false.
         */
        private boolean isWeekDay(final Calendar targetDay) {
            if (targetDay == null) {
                return false;
            }

            return targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
                    || targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
                    || targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
                    || targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
                    || targetDay.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;

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
     * 
     * @author taka
     */
    public enum JsonGenerator {
        /** singleton implement. */
        jsonGenInstance;

        /**
         * dummy comment. TODO:update comment
         * 
         * @return singleton instance
         */
        public static JsonGenerator getInstance() {
            return jsonGenInstance;
        }

        /**
         * dummy comment. TODO:update comment
         * 
         * @param list
         *            list from items
         * @return singleton instance
         * @throws JSONException
         *             if error.
         */
        public String genJson(final List<ConfigItem> list) throws JSONException {
            final JSONArray array = new JSONArray();
            final JSONStringer stringer = new JSONStringer();
            stringer.object().key("body").array();
            for (final ConfigItem item : list) {
                array.put(stringer.object().key(KEY_ENABLE).value(item.isEnable()).key(KEY_TIME).value(item.getTime())
                        .key(KEY_TITLE).value(item.getTitle()).key(KEY_PATH).value(item.getPath()).key(KEY_DATETYPE)
                        .value(item.getDateType().toString()).endObject());
            }
            return stringer.endArray().endObject().toString();
        }
    }

    /**
     * dummy comment.
     * 
     * @author taka
     */
    public enum JsonParser {
        /** singleton implement. */
        jsonParserInstance;

        /**
         * dummy comment.
         * 
         * @return singleton instance
         */
        public static JsonParser getInstance() {
            return jsonParserInstance;
        }

        /**
         * dummy comment. TODO:update comment
         * 
         * @param jsonString
         *            jsonString
         * @return List
         * @throws JSONException
         *             if error.
         */
        public List<ConfigItem> parse(final String jsonString) throws JSONException {
            final JSONObject rootObject = new JSONObject(jsonString);
            final JSONArray array = rootObject.getJSONArray("body");
            final ArrayList<ConfigItem> list = new ArrayList<ConfigItem>();
            for (int i = 0; i < array.length(); i++) {
                final JSONObject jsonObject = array.getJSONObject(i);
                list.add(new ConfigItem(jsonObject.getBoolean(KEY_ENABLE), jsonObject.getString(KEY_TITLE), jsonObject
                        .getString(KEY_TIME), jsonObject.getString(KEY_PATH), DateType.convert(jsonObject
                        .getString(KEY_DATETYPE))));
            }
            return list;
        }
    }

    /**
     * dummy comment. TODO: update comment
     */
    public ConfigItem() {
        this(false, "Initial Title", "00:00", "/mnt/sdcard/media/audio/01.mp3", DateType.daily);
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @return datetype
     */
    public final DateType getDateType() {
        return this.dateType;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @param argType
     *            typetitle
     */
    public final void setDateType(final DateType argType) {
        this.dateType = argType;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @param argEnable
     *            enable
     * @param argTitle
     *            title
     * @param argTime
     *            time
     * @param argPath
     *            path
     * @param argType
     *            type
     */
    public ConfigItem(final boolean argEnable, final String argTitle, final String argTime, final String argPath,
            final DateType argType) {
        this.enable = argEnable;
        this.title = argTitle;
        this.time = argTime;
        this.path = argPath;
        this.dateType = argType;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @return title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @param argTitle
     *            title
     */
    public final void setTitle(final String argTitle) {
        this.title = argTitle;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @return time
     */
    public final String getTime() {
        return time;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @param argTime
     *            time
     */
    public final void setTime(final String argTime) {
        this.time = argTime;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @return path to music file
     */
    public final String getPath() {
        return path;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @param argPath
     *            path
     */
    public final void setPath(final String argPath) {
        this.path = argPath;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @return enable
     */
    public final boolean isEnable() {
        return enable;
    }

    /**
     * dummy comment. TODO: update comment
     * 
     * @param argEnable
     *            enable
     */
    public final void setEnable(final boolean argEnable) {
        this.enable = argEnable;
    }

}
