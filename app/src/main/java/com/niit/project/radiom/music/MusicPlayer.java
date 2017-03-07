package com.niit.project.radiom.music;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 用于控制音乐播放的类，
 * 可以播放、停止、暂停背景音乐和各种音效
 * @author songhui
 * 
 */
public class MusicPlayer {
	//音乐类型
	public static enum MusicType {background, boom1, getAward, roundPassed};
	//任务类型
	public static enum TaskType {play, pause, stop};
	private Context context;
	private SharedPreferences sp;
	//状态为，音乐开关是否打开
	private boolean musicOn;
	
	/**
	 * 构造函数
	 * @param context
	 */
	public MusicPlayer(Context context) {
		this.context = context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	/**
	 * 播放音乐
	 * @param musicType
	 */
	public void playMusic(MusicType musicType) {
		musicOn = sp.getBoolean("musicOn", false);
		if (!musicOn)
			return;
		Intent service = new Intent();
		service.setAction("com.niit.project.radiom.MusicService");
		service.putExtra("musicType", musicType);
		service.putExtra("taskType", TaskType.play);
		//启动服务
		context.startService(service);
	}
	
	/**
	 * 停止音乐
	 * @param musicType
	 */
	public void stopMusic(MusicType musicType) {
		musicOn = sp.getBoolean("musicOn", false);
		if (!musicOn)
			return;
		Intent service = new Intent();
		service.setAction("com.niit.project.radiom.MusicService");
		service.putExtra("musicType", musicType);
		service.putExtra("taskType", TaskType.stop);
		//停止服务
		context.stopService(service);
	}
	
	/**
	 * 暂停音乐
	 * @param musicType
	 */
	public void pauseMusic(MusicType musicType) {
		musicOn = sp.getBoolean("musicOn", false);
		if (!musicOn)
			return;
		Intent service = new Intent();
		service.setAction("com.niit.project.radiom.MusicService");
		service.putExtra("musicType", musicType);
		service.putExtra("taskType", TaskType.pause);
		//启动服务
		context.startService(service);
	}
}
