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

/**
 * 闯关失败跳转的界面
 * @author songhui
 *
 */
public class FailActivity extends Activity {
	private Button restart;
	private Button backToMain;
	private TextView showResult;
	private int currentTopScore;
	private int score;
	private SharedPreferences sp;
	private int round;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fail);

		restart = (Button) findViewById(R.id.restart);
		backToMain = (Button) findViewById(R.id.backToMain);
		showResult = (TextView) findViewById(R.id.showResult);

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = sp.edit();

		currentTopScore = getIntent().getIntExtra("currentTopScore", 0);
		score = getIntent().getIntExtra("score", 0);
		round = getIntent().getIntExtra("round", 1);
		
		String result = "";
		//比较玩家分数和当前最高分数
		if (currentTopScore > score) {
			result = "闯关失败\n本关目前最高分为" + currentTopScore + "\n你的分数为" + score
					+ "\n继续努力！";
		} else if (currentTopScore == score) {
			result = "闯关失败，但是你追平了当前的最高分，你的分数为" + score;
		} else {
			//修改当前关卡的最高分记录
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
			result = "闯关失败，但是你刷新了最高分记录，你的分数为" + score + "\n加油！";
		}
		//显示结果
		showResult.setText(result);
		restart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int round = getIntent().getIntExtra("round", 1);
				Log.v("round", "在FailActivity中得到的关数为" + round);
				Intent intent = new Intent();
				switch (round) {
				case 1:// 第一关失败，重新开始第一关
					intent.setClass(FailActivity.this, GameActivity.class);
					intent.putExtra("round", round);
					FailActivity.this.startActivity(intent);
					FailActivity.this.finish();
					break;
				case 2:// 第二关失败，重新开始第二关
					intent.setClass(FailActivity.this, GameActivity.class);
					intent.putExtra("round", round);
					FailActivity.this.startActivity(intent);
					FailActivity.this.finish();
					break;
				case 3:// 第三关失败，重新开始第三关
					intent.setClass(FailActivity.this, WinActivity.class);
					intent.putExtra("round", round);
					FailActivity.this.startActivity(intent);
					FailActivity.this.finish();
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
				intent.setClass(FailActivity.this, MainActivity.class);
				FailActivity.this.startActivity(intent);
				FailActivity.this.finish();
			}
		});
	}
}
