package jp.fedom.android.musicalarm.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

// TODO: Auto-generated Javadoc
/**
 * The Class TimePickerPreference.
 */
public class TimePickerPreference extends DialogPreference {

    /** The last hour. */
    private int lastHour = 0;

    /** The last minute. */
    private int lastMinute = 0;

    /** The picker. */
    private TimePicker timePicker = null;

    /** The Constant DEFAULT_TIME. */
    private static final String DEFAULT_TIME = "00:00";

    /**
     * Gets the hour.
     * 
     * @param time
     *            the time
     * @return the hour
     */
    private static int getHour(String time) {
        String[] pieces = time.split(":");

        return (Integer.parseInt(pieces[0]));
    }

    /**
     * Gets the minute.
     * 
     * @param time
     *            the time
     * @return the minute
     */
    private static int getMinute(String time) {
        String[] pieces = time.split(":");

        return (Integer.parseInt(pieces[1]));
    }

    /**
     * Gets the time.
     * 
     * @return the time
     */
    public String getTime() {
        return getPersistedString(DEFAULT_TIME);
    }

    /**
     * Instantiates a new time picker preference.
     * 
     * @param context
     *            the context
     */
    public TimePickerPreference(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new time picker preference.
     * 
     * @param context
     *            the context
     * @param attrs
     *            the attrs
     */
    public TimePickerPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new time picker preference.
     * 
     * @param context
     *            the context
     * @param attrs
     *            the attrs
     * @param defStyle
     *            the def style // TODO : what is defStyle?
     */
    public TimePickerPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.preference.DialogPreference#onCreateDialogView()
     */
    @Override
    protected View onCreateDialogView() {
        // TODO: Forbid input figure directly
        this.timePicker = new TimePicker(getContext());
        return this.timePicker;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.preference.DialogPreference#onBindDialogView(android.view.View)
     */
    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        timePicker.setCurrentHour(lastHour);
        timePicker.setCurrentMinute(lastMinute);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.preference.DialogPreference#onDialogClosed(boolean)
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            lastHour = timePicker.getCurrentHour();
            lastMinute = timePicker.getCurrentMinute();

            String time = String.valueOf(lastHour) + ":" + String.valueOf(lastMinute);

            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.preference.Preference#onGetDefaultValue(android.content.res. TypedArray, int)
     */
    @Override
    protected Object onGetDefaultValue(TypedArray typeArray, int index) {
        return typeArray.getString(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.preference.Preference#onSetInitialValue(boolean, java.lang.Object)
     */
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String timeString = null;

        if (restoreValue) {
            if (defaultValue == null) {
                timeString = getPersistedString(DEFAULT_TIME);
            } else {
                timeString = getPersistedString(defaultValue.toString());
            }
        } else {
            timeString = defaultValue.toString();
        }
        lastHour = getHour(timeString);
        lastMinute = getMinute(timeString);
    }
}
