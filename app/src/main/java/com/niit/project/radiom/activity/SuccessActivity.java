package com.niit.project.radiom.activity;

import com.niit.project.radiom.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SuccessActivity extends Activity {
	private Button nextRound;
	private Button backToMain;
	private TextView showResult;
	private int currentTopScore;
	private int score;
	private SharedPreferences sp;
	private int round;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
		
		nextRound = (Button) findViewById(R.id.nextRound);
		backToMain = (Button) findViewById(R.id.backToMain);
		showResult = (TextView) findViewById(R.id.showResult);
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();
		
		currentTopScore = getIntent().getIntExtra("currentTopScore", 0);
		score = getIntent().getIntExtra("score", 0);
		round = getIntent().getIntExtra("round", 1);
		
		Log.v("score", "sharedpreference round1:" + sp.getInt("round1TopScore", 0)
				+ "round2:" + sp.getInt("round2TopScore", 0)
				+ "round3:" + sp.getInt("round3TopScore", 0)
				+ "currentTopScore" + currentTopScore
				+ "score" + score
				+ "round" + round);
		
		String result = "";
		if (currentTopScore > score) {
			result = "闯关成功\n本关目前最高分为" + currentTopScore + "\n你的分数为" + score + "\n继续努力！";
		}
		else if (currentTopScore == score) {
			result = "闯关成功，你追平了当前的最高分，你的分数为" + score + "\n恭喜！";
		}
		else {
			switch (round) {
			case 1:
				editor.putInt("round1TopScore", score);
				editor.commit();
				break;
			case 2:
				editor.putInt("round2TopScore", score);
				editor.commit();
				break;
			case 3:
				editor.putInt("round3TopScore", score);
				editor.commit();
				break;
			}
			result = "闯关成功，而且你刷新了最高分记录，你的分数为" + score + "\n恭喜！";
		}
		showResult.setText(result);
		
		nextRound.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int round = getIntent().getIntExtra("round", 1);
				Log.v("round", "在SucceActivity中得到的关数为" + round);
				Intent intent = new Intent();
				switch (round) {
				case 1://通过第一关，前往第二关
					intent.setClass(SuccessActivity.this, GameActivity.class);
					intent.putExtra("round", round + 1);
					SuccessActivity.this.startActivity(intent);
					SuccessActivity.this.finish();
					break;
				case 2://通过第二关，前往第三关
					intent.setClass(SuccessActivity.this, GameActivity.class);
					intent.putExtra("round", round + 1);
					SuccessActivity.this.startActivity(intent);
					SuccessActivity.this.finish();
					break;
				case 3://通过第三关，通关
//					intent.setClass(SuccessActivity.this, WinActivity.class);
////					intent.putExtra("round", round + 1);
//					SuccessActivity.this.startActivity(intent);
//					SuccessActivity.this.finish();
					break;
				default:
					break;
				}
			}
		});
		
		backToMain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SuccessActivity.this, MainActivity.class);
				SuccessActivity.this.startActivity(intent);
				SuccessActivity.this.finish();
			}
		});
	}
}
