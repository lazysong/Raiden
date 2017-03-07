package com.niit.project.radiom.activity;

import com.niit.project.radiom.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends Activity {
	private CheckBox musicCheck;
	private CheckBox vibrate;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		musicCheck = (CheckBox) findViewById(R.id.musicCheck);
		vibrate  = (CheckBox) findViewById(R.id.vibrate);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		if (sp.getBoolean("musicOn", false))
			musicCheck.setChecked(true);
		else
			musicCheck.setChecked(false);
		musicCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor edit = sp.edit();
				if (isChecked) {
					edit.putBoolean("musicOn", true);
				}
				else
					edit.putBoolean("musicOn", false);
				edit.commit();
			}
		});
	}
}
