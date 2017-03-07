package com.niit.project.radiom.activity;

import com.niit.project.radiom.ObjectFactroy;
import com.niit.project.radiom.R;
import com.niit.project.radiom.ObjectFactroy.RoundType;
import com.niit.project.radiom.object.Round;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity {
	private Round round;//关卡对象
	private ObjectFactroy factroy;//工厂类，用于创建关卡对象
	private boolean gameIsPaused = false;//游戏暂停的标志位
	private Button pauseStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		factroy = new ObjectFactroy(this, 318, 455);
		
		//获取当前的关卡数，然后创建相关的关卡对象
		int roundInt = getIntent().getIntExtra("round", 1);
		Log.v("round", "在GameActivity中得到的关数为" + roundInt);
		switch (roundInt) {
		case 1:
			round = factroy.createRound(RoundType.round1);
			break;
		case 2:
			round = factroy.createRound(RoundType.round2);
			break;
		case 3:
			round = factroy.createRound(RoundType.round3);
			break;
		}
		//设置布局文件
		setContentView(R.layout.activity_game);		
		
		pauseStart = (Button) findViewById(R.id.pause_start);
		gameIsPaused = false;
		//为暂停/开始按钮添加监听器
		pauseStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (gameIsPaused) {
					gameIsPaused = false;
					pauseStart.setBackgroundResource(R.drawable.pause);
				}
				else {
					gameIsPaused = true;
					pauseStart.setBackgroundResource(R.drawable.start);
				}
			}
		});
	}

	/*
	 * 一些getter和setter方法
	 * */
	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public ObjectFactroy getFactroy() {
		return factroy;
	}

	public void setFactroy(ObjectFactroy factroy) {
		this.factroy = factroy;
	}

	public boolean isGameIsPaused() {
		return gameIsPaused;
	}

	public void setGameIsPaused(boolean gameIsPaused) {
		this.gameIsPaused = gameIsPaused;
	}
	
}
