package jp.fedom.android.musicalarm.activity;

import java.util.ArrayList;

import jp.fedom.android.musicalarm.R;
import jp.fedom.android.musicalarm.item.ConfigItem;
import jp.fedom.android.musicalarm.item.ConfigPreference;
import jp.fedom.android.musicalarm.item.ConfigItem.DateType;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingActivity.
 */
public final class SettingActivity extends PreferenceActivity {

	/** The Constant TAG. */
	private static final String TAG = "SettingActivity";

	/** The Constant INVALIDATE. */
	private static final int INVALIDATE = -1;

	/** The config index. */
	private int configIndex = INVALIDATE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(final Bundle savedState) {
		super.onCreate(savedState);
		addPreferencesFromResource(R.layout.activity_setting);
		final Intent intent = getIntent();
		configIndex = intent.getIntExtra("configIndex", INVALIDATE);
		Log.d(TAG, "config index= " + configIndex);
		if (configIndex == INVALIDATE) {
			this.finish();
			Log.d(TAG, "stop with some happen. config index is INVALIDATE");
		}
		ConfigPreference pref = new ConfigPreference(
				PreferenceManager.getDefaultSharedPreferences(this));

		ConfigItem item = pref.loadConfigItems().get(configIndex);

		// TODO: implement correct
		/* set default data from config item */
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
//		PreferenceManager manager = this.getPreferenceManager();
		Editor defaultEditor = prefs.edit();
		defaultEditor
				.putBoolean(getString(R.string.preference_item_enable_key),
						item.isEnable());
		defaultEditor.putString(getString(R.string.preference_item_title_key),
				item.getTitle());
		defaultEditor.putString(
				getString(R.string.preference_list_datetype_key), item
						.getDateType().name());
		defaultEditor.apply();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (isChangedConfig()) {
				showSaveDiscardAlart();
			} else {
				finish();
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onUserLeaveHint()
	 */
	@Override
	public void onUserLeaveHint() {
		if (isChangedConfig()) {
			showSaveDiscardAlart();
		}
	}

	/**
	 * This is dummy comment. TODO:describe comment
	 */
	private void saveConfig() {
		Log.d("menu", "called save");
		ConfigPreference pref = new ConfigPreference(
				PreferenceManager.getDefaultSharedPreferences(this));
		ArrayList<ConfigItem> list = (ArrayList<ConfigItem>) pref
				.loadConfigItems();
		list.set(configIndex, getConfigfromUI());
		pref.saveConfigItems(list);
	}

	/**
	 * Gets the configfrom ui.
	 * 
	 * @return the configfrom ui
	 */
	private ConfigItem getConfigfromUI() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean enable = prefs.getBoolean(
				getString(R.string.preference_item_enable_key), false);
		String title = prefs.getString(
				getString(R.string.preference_item_title_key), "title "
						+ configIndex);
		String datatype = prefs.getString(
				getString(R.string.preference_list_datetype_key),
				DateType.daily.name());

		return new ConfigItem(enable, title, "00:00", "path",
				DateType.convert(datatype));
	}

	/**
	 * Show save discard alart.
	 */
	private void showSaveDiscardAlart() {
		new AlertDialog.Builder(this)
				.setTitle("Save/Discard")
				.setMessage("Save Config Change?")
				.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								(Toast.makeText(SettingActivity.this, "saved",
										Toast.LENGTH_SHORT)).show();
								saveConfig();
								finish();
							}
						})
				.setNegativeButton("Discard",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								(Toast.makeText(SettingActivity.this,
										"discarded", Toast.LENGTH_SHORT))
										.show();
								finish();
							}
						}).show();
	}

	/**
	 * Checks if is changed config.
	 * 
	 * @return true, if is changed config
	 */
	private boolean isChangedConfig() {
		final ConfigPreference pref = new ConfigPreference(
				PreferenceManager.getDefaultSharedPreferences(this));
		ConfigItem previous = pref.loadConfigItems().get(configIndex);
		return !(previous.equals(getConfigfromUI()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	public boolean onOptionsItemSelected(final MenuItem item) {
		boolean returnValue = false;
		if (item.getItemId() == R.id.menu_save) {
			saveConfig();
			returnValue = true;
		} else {
			returnValue = super.onOptionsItemSelected(item);
		}
		return returnValue;
	}

}
