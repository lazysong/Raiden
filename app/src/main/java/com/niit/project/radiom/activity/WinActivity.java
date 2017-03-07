package com.niit.project.radiom.activity;

import com.niit.project.radiom.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinActivity extends Activity {
	private Button backToMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win);
		
		backToMain = (Button) findViewById(R.id.backToMain);
		backToMain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(WinActivity.this, MainActivity.class);
				WinActivity.this.startActivity(intent);
				WinActivity.this.finish();
			}
		});
	}
}
