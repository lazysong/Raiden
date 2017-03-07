package com.niit.project.radiom.music;

import com.niit.project.radiom.R;
import com.niit.project.radiom.music.MusicPlayer.TaskType;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * 用于播放音乐的后台服务
 * @author songhui
 *
 */
public class MusicService extends Service {
	private MediaPlayer bgmPlayer;
	private MediaPlayer awardPlayer;
	private MediaPlayer boomPlayer;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		MusicPlayer.MusicType musicType = (MusicPlayer.MusicType) intent.getSerializableExtra("musicType");
		TaskType taskType = (TaskType) intent.getSerializableExtra("taskType");
		Log.v("service", "onStartCommand() is called, musicType is" + musicType);
		switch (musicType) {
		case background:
			if (taskType == TaskType.play) {
				bgmPlayer = MediaPlayer.create(getBaseContext(), R.raw.bgm1);
				bgmPlayer.setLooping(true);
				bgmPlayer.start();
			}
			else if (taskType == TaskType.pause) {
				bgmPlayer.stop();
			}
			break;
		case boom1:
			boomPlayer = MediaPlayer.create(getBaseContext(), R.raw.bossboom);
			boomPlayer.setLooping(false);
			boomPlayer.start();
		case getAward:
			awardPlayer = MediaPlayer.create(getBaseContext(), R.raw.getaward);
			awardPlayer.setLooping(false);
			awardPlayer.start();
		default:
			break;
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (bgmPlayer != null)
			bgmPlayer.stop();
		if (awardPlayer != null)
			awardPlayer.stop();
		if (boomPlayer != null)
			boomPlayer.stop();
	}
}
