package jp.fedom.android.musicalarm.ui;

import jp.fedom.android.musicalarm.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class MusicScannerPreference.
 */
public class MusicScannerPreference extends Preference {

    /**
     * The Class BackgroundTask.
     */
    private class BackgroundTask extends AsyncTask<Integer, Integer, Boolean> {

        /** The dialog. */
        private ProgressDialog dialog;

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getContext(), getContext().getString(R.string.preference_dialog_scanning),
                    getContext().getString(R.string.preference_dialog_pleasewait), true);
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    (Toast.makeText(getContext(), "canceled", Toast.LENGTH_LONG)).show();
                }
            });
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
        }

    }

    /** The Constant TAG. */
    private static final String TAG = "MusicScannerPreference";

    /**
     * Instantiates a new music scanner preference.
     * 
     * @param context
     *            the context
     */
    public MusicScannerPreference(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new music scanner preference.
     * 
     * @param context
     *            the context
     * @param attrs
     *            the attrs
     */
    public MusicScannerPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new music scanner preference.
     * 
     * @param context
     *            the context
     * @param attrs
     *            the attrs
     * @param defStyle
     *            the def style
     */
    public MusicScannerPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d(TAG, "called onPreferenceClick");
                new BackgroundTask().execute();
                return false;
            }
        });
    }

}
