package com.niit.project.radiom.activity;

import com.niit.project.radiom.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
	private Button newGame;
	private Button settings;
	private Button rank;
	private Button help;
	private Button exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		newGame = (Button) findViewById(R.id.newGame);
		settings = (Button) findViewById(R.id.settings);
		rank = (Button) findViewById(R.id.rank);
		help = (Button) findViewById(R.id.help);
		exit = (Button) findViewById(R.id.exit);
		
		newGame.setOnClickListener(this);
		settings.setOnClickListener(this);
		rank.setOnClickListener(this);
		exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		Intent intent = new Intent();
		switch (viewId) {
		case R.id.newGame:
			intent.setClass(this, GameActivity.class);
			intent.putExtra("round", 1);
			startActivity(intent);
			break;
		case R.id.settings:
			intent.setClass(this, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.rank:
			intent.setClass(this, RankActivity.class);
			startActivity(intent);
			break;
		case R.id.exit:
			finish();
			break;
		}
	}
}
