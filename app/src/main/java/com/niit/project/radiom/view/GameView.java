package com.niit.project.radiom.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.niit.project.radiom.ObjectFactroy;
import com.niit.project.radiom.ObjectFactroy.CaseType;
import com.niit.project.radiom.ObjectFactroy.EnemyType;
import com.niit.project.radiom.ObjectFactroy.PlaneType;
import com.niit.project.radiom.activity.FailActivity;
import com.niit.project.radiom.activity.GameActivity;
import com.niit.project.radiom.activity.SuccessActivity;
import com.niit.project.radiom.activity.WinActivity;
import com.niit.project.radiom.music.MusicPlayer;
import com.niit.project.radiom.music.MusicPlayer.MusicType;
import com.niit.project.radiom.object.Background;
import com.niit.project.radiom.object.BaseBullet;
import com.niit.project.radiom.object.BaseBullet.BulletType;
import com.niit.project.radiom.object.BulletsCase;
import com.niit.project.radiom.object.Case;
import com.niit.project.radiom.object.EnemyPlane;
import com.niit.project.radiom.object.MedicineCase;
import com.niit.project.radiom.object.Player;
import com.niit.project.radiom.object.PlayerPlane;
import com.niit.project.radiom.object.Round;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	//Holder对象
	private SurfaceHolder holder;
	//上下文对象
	private GameActivity context;
	
	/**
	 * 屏幕宽度和高度
	 */
	private int screenWidth;
	private int screenHeight;
	
	/**
	 * 一些状态位和计数器
	 */
	private boolean gameIsRunning;//游戏是否正在运行
	private boolean successed;//是否成功通关
	private long planesFrameCount = 0;//用来产生敌机和工具箱的帧数计数器
	private boolean musicIsPaused;//音乐是否被暂停
	private boolean gameIsBreaked = false;
	
	/**
	 * 判断双击事件的变量
	 * */
	private long firClick;  
	private long secClick;
	private int count;
	
	/**
	 * 游戏中的一些对象
	 */
	private ObjectFactroy factroy;//用于生产各种对象的工厂类
	private Canvas canvas;//画布对象
	private Background background;//背景对象
	private Round round;//关卡对象
	private Player player;//玩家对象
	private PlayerPlane playerPlane;//玩家飞机
	private List<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();//敌机列表
	private List<BaseBullet> playerBullets = new ArrayList<BaseBullet>();//玩家的子弹列表
	private List<BaseBullet> enemyBullets = new ArrayList<BaseBullet>();//敌人的子弹列表
	private List<Case> cases = new ArrayList<Case>();//工具箱列表
	private EnemyPlane boss1;
	private EnemyPlane boss2;
	private EnemyPlane boss3;
	
	/**
	 * 一些类型的数组
	 * */
	private EnemyType[] enemyTypes = new EnemyType[]{
			EnemyType.simpleEnemy, EnemyType.smallEnemy1, EnemyType.smallEnemy2,
			EnemyType.smallEnemy3, EnemyType.smallEnemy4, EnemyType.smallEnemy5, 
			EnemyType.smallEnemy6, EnemyType.boss1, EnemyType.boss2, EnemyType.boss3};
	private BulletType[] bulletTypes = new BulletType[] {
			BulletType.bullet1, BulletType.bullet1_left, BulletType.bullet1_right,
			BulletType.bullet2,	BulletType.bullet2_left, BulletType.bullet2_right,
			BulletType.bullet3,	BulletType.bullet3_left, BulletType.bullet3_right,
			BulletType.bullet4,	BulletType.bullet4_left, BulletType.bullet4_right,
			BulletType.bullet5,	BulletType.bullet5_left, BulletType.bullet5_right,
			BulletType.bullet6,	BulletType.bullet6_left, BulletType.bullet6_right,
		};
	private CaseType[] caseTypes = new CaseType[] { 
			CaseType.medicineCase,
			CaseType.bulletsCase, CaseType.bulletsCase1, CaseType.bulletsCase2 };
	
	/**
	 * 用于控制时间的成员
	 */
	private TimerTask timerTask;
//	private Timer timer;
	private int tempTime = 10;

	
	//用于控制音乐播放的成员
	private MusicPlayer musicPlayer;
	
	//当前关卡的最高分记录
	private int currentTopScore;
	
	private SharedPreferences sp;

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		holder = getHolder();
		holder.addCallback(this);//添加回调接口
		factroy = ((GameActivity)context).getFactroy();
		player = new Player("007", "大仙", 0);//初始化玩家对象
		round = ((GameActivity)context).getRound();//初始化关卡对象
		this.context = (GameActivity)context;//获取上下文对象
		musicPlayer = new MusicPlayer(context);//初始化音乐播放对象
		//获取SharedPreference对象
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
		
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		holder = getHolder();
		holder.addCallback(this);//添加回调接口
		factroy = ((GameActivity)context).getFactroy();
		player = new Player("007", "大仙", 0);//初始化玩家对象
		round = ((GameActivity)context).getRound();//初始化关卡对象
		this.context = (GameActivity)context;//获取上下文对象
		musicPlayer = new MusicPlayer(context);//初始化音乐播放对象
		//获取SharedPreference对象
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		/*
		 * 初始化一些状态位
		 * */
		gameIsRunning = true;
		musicIsPaused = false;
		successed = true;
		
		//获取屏幕的宽度和高度
		screenWidth = getWidth();
		screenHeight = getHeight();
		
		//获取当前关卡的最高分记录
		switch (round.getRoundNumber()) {
		case 1:
			currentTopScore = sp.getInt("round1TopScore", 0);
			break;
		case 2:
			currentTopScore = sp.getInt("round2TopScore", 0);
			break;
		case 3:
			currentTopScore = sp.getInt("round3TopScore", 0);
			break;
		default:
			break;
		}
		
		/*
		 * 开始定时
		 * */
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				gameIsRunning = false;
			}
		};
//		timer = new Timer();
//		timer.schedule(timerTask, round.getTime());
		
		
		//创建玩家飞机
		switch (round.getRoundNumber()) {
		case 1:
			playerPlane = factroy.createPlayerPlane(PlaneType.player1);
			break;
		case 2:
			playerPlane = factroy.createPlayerPlane(PlaneType.player2);
			break;
		case 3:
			playerPlane = factroy.createPlayerPlane(PlaneType.player3);
			break;
		}
		playerPlane.addBullet(BulletType.bullet2, 100000);
		
		//设置玩家飞机的目标坐标
		playerPlane.setDestX(screenWidth /2);
		playerPlane.setDestY(screenHeight -40);
		
		//播放背景音乐
		musicPlayer.playMusic(MusicType.background);
		
		//初始化背景对象
		background = factroy.createBackground(round.getBackgroundType());
		
		//开启线程
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		gameIsRunning = false;
		releaseResource();
		//停止播放背景音乐
		musicPlayer.stopMusic(MusicType.background);
	}

	private void releaseResource() {
		// TODO Auto-generated method stub
		if (!playerBullets.isEmpty())
			playerBullets.clear();
		if (!enemyPlanes.isEmpty())
			enemyPlanes.clear();
		if (!enemyBullets.isEmpty())
			enemyBullets.clear();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (gameIsRunning) {
			//如果游戏暂停，同时暂停背景音乐，并跳过其他操作
			if (this.context.isGameIsPaused()) {
				if (!musicIsPaused) {
					musicPlayer.pauseMusic(MusicType.background);
					musicIsPaused = true;
				}
				continue;
			}
			if (musicIsPaused) {
				musicPlayer.playMusic(MusicType.background);
				musicIsPaused = false;
			}
			
			int sleepTime = 50;//线程休眠时间，单位为毫秒
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//锁定画布，开始绘图
			canvas = holder.lockCanvas();
			//创建敌机的帧数计数器递增
			planesFrameCount ++;
			//创建敌机
			createEnemyPlanes();
			//创建boss
			switch (round.getRoundNumber()) {
			case 1:
				if (player.getScores() > 1000) {
					if (boss1 == null) {
						boss1 = factroy.createEnemyPlane(EnemyType.boss1);
						boss1.setBlood(1000);
						boss1.setAwardScores(500);
						enemyPlanes.add(boss1);
					}
				}
				break;
			case 2:
				if (player.getScores() > 1000 && player.getScores() <= 2000) {
					if (boss1 == null) {
						boss1 = factroy.createEnemyPlane(EnemyType.boss1);
						boss1.setBlood(3000);
						boss1.setAwardScores(500);
						enemyPlanes.add(boss1);
					}
				}
				else if (player.getScores() > 3000 && player.getScores() <= 4000) {
					if (boss2 == null) {
						boss2 = factroy.createEnemyPlane(EnemyType.boss2);
						boss2.setBlood(10000);
						boss2.setAwardScores(10000);
						enemyPlanes.add(boss2);
					}
				}
				break;
			case 3:
				if (player.getScores() > 1000 && player.getScores() <= 2000) {
					if (boss1 == null) {
						boss1 = factroy.createEnemyPlane(EnemyType.boss1);
						boss1.setBlood(1000);
						boss1.setAwardScores(500);
						enemyPlanes.add(boss1);
					}
				}
				else if ( player.getScores() > 3000 && player.getScores() <= 4000) {
					if (boss2 == null) {
						boss2 = factroy.createEnemyPlane(EnemyType.boss2);
						boss2.setBlood(3000);
						boss2.setAwardScores(1000);
						enemyPlanes.add(boss2);
					}
				}
				else if ( player.getScores() > 7000 && player.getScores() < 10000) {
					if (boss3 == null) {
						boss3 = factroy.createEnemyPlane(EnemyType.boss3);
						boss3.setBlood(5000);
						boss3.setAwardScores(30000);
						enemyPlanes.add(boss3);
					}
				}
				break;
			}
			
			//创建敌机子弹
			createEnemyBullets();
			//创建玩家子弹
			createPlayerBullets();
			//创建工具箱
			createCases();
			//移动，包括移动背景、玩家飞机、玩家子弹、所有敌机、所有敌机子弹
			move(sleepTime);
			//工具箱生命递减
			minusCaseLife();
			//绘制图像，包括背景、玩家飞机、玩家子弹、所有敌机、所有敌机子弹
			if (canvas == null)
				return;
			if (gameIsBreaked)
				return;
			draw();
			//碰撞检测，包括玩家飞机和所有敌机、所有敌机子弹的碰撞，还包括玩家子弹和所有敌机的碰撞
			detectCrash();
			//检测所有敌机的状态，包括范围和血量，如果超出范围或血量为小于0则直接绘制爆炸效果并移除此敌机
			detectEnemiesState();
			//检测boss状态
//			if (boss != null && boss.getBlood() <=0 ) {
//				gameIsRunning = false;
//				successed = true;
//			}
			
			if ( boss1 != null && boss1.getBlood() <= 0 ) {
				//绘制爆炸效果
				if (canvas == null)
					return;
				if (boss1.getBoomFrameCount() 
						< boss1.getBoomCount()) {
					boss1.drawBoom(canvas);
					boss1.addBoomFrameCount();
				}
				else {
					player.addScores(boss1.getAwardScores());
					//如果boss死亡，播放音效
					musicPlayer.playMusic(MusicType.boom1);
					enemyPlanes.remove(boss1);
					boss1 = null;
					if (round.getRoundNumber() == 1) {
						gameIsRunning = false;
						successed = true;
					}
				}
			}
			
			if ( boss2 != null && boss2.getBlood() <= 0 ) {
				//绘制爆炸效果
				if (canvas == null)
					return;
				if (boss2.getBoomFrameCount() 
						< boss2.getBoomCount()) {
					boss2.drawBoom(canvas);
					boss2.addBoomFrameCount();
				}
				else {
					player.addScores(boss2.getAwardScores());
					//如果boss死亡，播放音效
					musicPlayer.playMusic(MusicType.boom1);
					enemyPlanes.remove(boss2);
					boss2 = null;
					if (round.getRoundNumber() == 2) {
						gameIsRunning = false;
						successed = true;
					}
				}
			}
			
			if ( boss3 != null && boss3.getBlood() <= 0 ) {
				//绘制爆炸效果
				if (canvas == null)
					return;
				if (boss3.getBoomFrameCount() 
						< boss3.getBoomCount()) {
					boss3.drawBoom(canvas);
					boss3.addBoomFrameCount();
				}
				else {
					player.addScores(boss3.getAwardScores());
					//如果boss死亡，播放音效
					musicPlayer.playMusic(MusicType.boom1);
					enemyPlanes.remove(boss3);
					boss3 = null;
					if (round.getRoundNumber() == 3) {
						gameIsRunning = false;
						successed = true;
					}
				}
			}
			//检测所有敌机子弹的状态，包括范围和是否被使用，如果超出范围或已经被使用则直接移除子弹
			detectEnemyBulletsState();
			//检测玩家飞机的状态，包括血量,如果血量小于0则直接绘制爆炸效果，闯关失败
			detectPlayerState();
			//检测玩家子弹的状态，包括范围和是否被使用，如果超出范围或已经被使用则直接移除子弹
			detectPlayerBulletsState();
			//检测工具箱的状态
			detectCasesState();
			//显示玩家血量和积分
			show();
			//解锁画布
			holder.unlockCanvasAndPost(canvas);
		}
		//游戏结束时释放资源
		releaseResource();
		//取消定时器
//		timer.cancel();
		musicPlayer.stopMusic(MusicType.background);
		//根据状态跳转到相应的界面
		if (!gameIsBreaked) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (successed) {
				Intent intent = new Intent();
				//若还有下一关，则跳转到成功界面
				if (round.getRoundNumber() < 3) {
					intent.setClass(getContext(), SuccessActivity.class);
					intent.putExtra("round", round.getRoundNumber());
					intent.putExtra("score", player.getScores());
					intent.putExtra("currentTopScore", currentTopScore);
				}
				else
					intent.setClass(getContext(), WinActivity.class);
				getContext().startActivity(intent);
				((GameActivity)getContext()).finish();
				
			}
			else {
				//跳转到失败界面
				Intent intent = new Intent();
				intent.setClass(getContext(), FailActivity.class);
				intent.putExtra("round", round.getRoundNumber());
				intent.putExtra("score", player.getScores());
				intent.putExtra("currentTopScore", currentTopScore);
				getContext().startActivity(intent);
				((GameActivity)getContext()).finish();
			}
		}
	}
	
	/**
	 * 所有工具箱的生命时间减少1
	 */
	private void minusCaseLife() {
		for (int i = 0; i < cases.size(); i ++) {
			cases.get(i).minusLife(1);
		}
	}

	/**
	 * 检测工具箱的状态
	 */
	private void detectCasesState() {
		for (int i = 0; i < cases.size(); i ++) {
			//如果工具箱已经被使用或者生命周期结束，则将其从工具箱列表中移除
			if ( cases.get(i).isUsed() || cases.get(i).getLife() <= 0 ) {
				//移除该子弹
				cases.remove(i);
				
			}
			//如果工具箱超出范围，则将其从工具箱列表中移除
			else if ( cases.get(i).isOverRange(0, 320, 0, 450) ) {
				cases.remove(i);
			}
		}
	}

	/**
	 * 创建工具箱并添加到工具箱列表中
	 */
	private void createCases() {
		//从关卡类获得敌机类型-敌机数量，敌机类型-出现间隔的映射
		Map<CaseType, Integer> amountMap = round.getCaseNumberMap();
		Map<CaseType, Integer> delayMap = round.getCaseDelayMap();
		Case baseCase = null;
		
		for (int i = 0; i < caseTypes.length; i ++) {
			if( amountMap.containsKey(caseTypes[i]))
				if ( planesFrameCount % delayMap.get(caseTypes[i]) == 1 
				&& planesFrameCount > tempTime) {
					for (int j = 0; j < amountMap.get(caseTypes[i]); j ++) {
						baseCase = factroy.createCase(caseTypes[i]);
						cases.add(baseCase);
					}
				}
		}
	}

	/**
	 * 展示玩家分数，关卡数，血量等等
	 */
	private void show() {
		// TODO Auto-generated method stub
		if (canvas == null)
			return;
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(16);
		canvas.drawText("当前关卡：" + round.getRoundNumber(), 180, 20, paint);
		canvas.drawText("目前最高分：" + currentTopScore, 180, 40, paint);
		canvas.drawText("玩家血量：" + playerPlane.getBlood(), 180, 60, paint);
		canvas.drawText("玩家积分：" + player.getScores(), 180, 80, paint);
	}

	/**
	 * 创建玩家子弹
	 */
	private void createPlayerBullets() {
		// TODO Auto-generated method stub
		playerPlane.addShootFrameCount();
		
		for (int i = 0; i < bulletTypes.length; i ++) {
			if (playerPlane.getBulletsMap().containsKey(bulletTypes[i])) {
				if ( playerPlane.canShoot(bulletTypes[i]) ) {
					playerBullets.add(playerPlane.createBullet(bulletTypes[i]));
					//相应的子弹数量减1
					playerPlane.cutBullets(bulletTypes[i], 1);
				}
			}
		}
	}

	/**
	 * 检测玩家子弹的状态
	 */
	private void detectPlayerBulletsState() {
		for (int i = 0; i < playerBullets.size(); i ++) {
			//如果子弹已经被使用，则将其从敌机子弹列表中移除
			if ( playerBullets.get(i).isUsed() ) {
				//移除该子弹
				playerBullets.remove(i);
				
			}
			//如果敌机子弹超出范围，则将其从敌机列表中移除
			else if ( playerBullets.get(i).isOverRange(0, 320, 0, 450) ) {
				playerBullets.remove(i);
			}
		}
	}

	/**
	 * 检测所有敌机子弹的状态，包括范围和是否被使用，
	 * 如果超出范围或已经被使用则直接移除子弹
	 */
	private void detectEnemyBulletsState() {
		for (int i = 0; i < enemyBullets.size(); i ++) {
			//如果子弹已经被使用，则将其从敌机子弹列表中移除
			if ( enemyBullets.get(i).isUsed() ) {
				//移除该子弹
				enemyBullets.remove(i);
			}
			//如果敌机子弹超出范围，则将其从敌机列表中移除
			else if ( enemyBullets.get(i).isOverRange(0, screenWidth, 0, screenHeight) ) {
				enemyBullets.remove(i);
			}
		}
	}

	/**
	 * 检测玩家的状态
	 */
	private void detectPlayerState() {
		//如果飞机被击毁，则绘制爆炸效果，并将其从敌机列表中移除
		if ( playerPlane.getBlood() <= 0 ) {
			//绘制爆炸效果
			if (canvas == null)
				return;
			if (playerPlane.getBoomFrameCount() 
					< playerPlane.getBoomCount()) {
				playerPlane.drawBoom(canvas);
				playerPlane.addBoomFrameCount();
			}
			else {
				gameIsRunning = false;
				successed = false;
			}
		}			
	}

	/**
	 * 检测所有敌机的状态，包括范围和血量，
	 * 如果超出范围或血量为小于0则直接绘制爆炸效果并移除此敌机
	 */
	private void detectEnemiesState() {
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			//如果敌机被击毁，则绘制爆炸效果，并将其从敌机列表中移除
			if ( enemyPlanes.get(i).getBlood() <= 0 ) {
				//绘制爆炸效果
				if (canvas == null)
					return;
				if (enemyPlanes.get(i).getBoomFrameCount() 
						< enemyPlanes.get(i).getBoomCount()) {
					enemyPlanes.get(i).drawBoom(canvas);
					enemyPlanes.get(i).addBoomFrameCount();
				}
				else {
					player.addScores(enemyPlanes.get(i).getAwardScores());
					//如果boss死亡，播放音效
					if (enemyPlanes.get(i).getType() == EnemyType.boss1
							|| enemyPlanes.get(i).getType() == EnemyType.boss1
							|| enemyPlanes.get(i).getType() == EnemyType.boss1) {
						musicPlayer.playMusic(MusicType.boom1);
					}
					enemyPlanes.remove(i);
				}
			}
			//如果敌机超出范围，则将其从敌机列表中移除
			else if ( enemyPlanes.get(i).isOverRange(0, 0, 320, 450) ) {
				enemyPlanes.remove(i);
			}
		}
		
	}

	/**
	 * 检测碰撞事件
	 */
	private void detectCrash() {
		// TODO Auto-generated method stub
		//检测玩家飞机和敌机的碰撞
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			if ( enemyPlanes.get(i).detectCrash(playerPlane) ) {
				switch (enemyPlanes.get(i).getType()) {
				//简单敌机
				case smallEnemy1:
					//撞机事件发生，玩家飞机扣20点血量
					playerPlane.cutBlood(20);
					//撞机事件发生，敌方飞机扣20点血量
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy2:
					//撞机事件发生，玩家飞机扣20点血量
					playerPlane.cutBlood(20);
					//撞机事件发生，敌方飞机扣20点血量
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy3:
					//撞机事件发生，玩家飞机扣20点血量
					playerPlane.cutBlood(20);
					//撞机事件发生，敌方飞机扣20点血量
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy4:
					//撞机事件发生，玩家飞机扣20点血量
					playerPlane.cutBlood(20);
					//撞机事件发生，敌方飞机扣20点血量
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy5:
					//撞机事件发生，玩家飞机扣20点血量
					playerPlane.cutBlood(20);
					//撞机事件发生，敌方飞机扣20点血量
					enemyPlanes.get(i).cutBlood(20);
					break;
				case smallEnemy6:
					//撞机事件发生，玩家飞机扣20点血量
					playerPlane.cutBlood(20);
					//撞机事件发生，敌方飞机扣20点血量
					enemyPlanes.get(i).cutBlood(20);
					break;
				case boss1:
					//撞机事件发生，玩家飞机扣100点血量
					playerPlane.cutBlood(100);
					//撞机事件发生，敌方飞机扣100点血量
					enemyPlanes.get(i).cutBlood(10);
					break;
				case boss2:
					//撞机事件发生，玩家飞机扣100点血量
					playerPlane.cutBlood(300);
					//撞机事件发生，敌方飞机扣100点血量
					enemyPlanes.get(i).cutBlood(10);
					break;
				case boss3:
					//撞机事件发生，玩家飞机扣100点血量
					playerPlane.cutBlood(500);
					//撞机事件发生，敌方飞机扣100点血量
					enemyPlanes.get(i).cutBlood(10);
					break;
				default:
					break;
				}
				
			}
		}
		//检测玩家飞机和敌机子弹的碰撞
		for (int i = 0; i < enemyBullets.size(); i ++) {
			if (playerPlane.detectCrash(enemyBullets.get(i))) {
				//如果碰撞发生，玩家飞机扣血
				playerPlane.cutBlood(enemyBullets.get(i).getHarm());
				//如果碰撞发生，敌机子弹被使用
				enemyBullets.get(i).setUsed(true);
			}
		}
		//检测敌机和玩家子弹的碰撞
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			for (int j = 0; j < playerBullets.size(); j ++) {
				if ( enemyPlanes.get(i).detectCrash(playerBullets.get(j)) ) {
					enemyPlanes.get(i).cutBlood(playerBullets.get(j).getHarm());
					playerBullets.get(j).setUsed(true);
				}
			}
		}
		//检测玩家飞机和工具箱的碰撞
		for (int i = 0; i < cases.size(); i ++) {
			CaseType type = cases.get(i).getType();
			if ( cases.get(i).detectCrash(playerPlane) ) {
				switch (type) {
				case medicineCase:
					cases.get(i).setUsed(true);
					playerPlane.addBlood(((MedicineCase)cases.get(i)).getBloodAmount());
//					Log.v("crash", "碰撞发生，玩家飞机和医药箱碰撞了");
					break;
				default:
					cases.get(i).setUsed(true);
					Map<BulletType, Integer> map = ((BulletsCase)cases.get(i)).getBulletsMap();
					for (int j = 0; j < bulletTypes.length; j ++) {
						if (map.containsKey(bulletTypes[j])) {
							playerPlane.addBullet(bulletTypes[j], map.get(bulletTypes[j]));
							Log.v("crash", "向飞机中添加了" + bulletTypes[j] + map.get(bulletTypes[j]) +"颗");
						}
							
					}
//					if (map.containsKey(BulletType.bullet4)) {
//						playerPlane.addBullet(BulletType.bullet4, map.get(BulletType.bullet4))
//					}
					Log.v("crash", "碰撞发生，玩家飞机和弹药箱碰撞了,此时弹药箱的bulletMap为" + map);
					break;
				}
				//播放音效
				musicPlayer.playMusic(MusicType.getAward);
			}
		}
	}

	/**
	 * 移动屏幕中的所有物品
	 * @param time
	 */
	private void move(int time) {
		// TODO Auto-generated method stub
		//移动背景
		background.move();
		//移动玩家飞机
		playerPlane.move(time);
		//移动玩家飞机子弹
		for (int i = 0; i < playerBullets.size(); i ++) {
			playerBullets.get(i).move(time);
		}
		//移动敌机
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			enemyPlanes.get(i).styleMove(time);
		}
		//移动敌机子弹
		for (int i = 0; i < enemyBullets.size(); i ++) {
			enemyBullets.get(i).move(time);
			if (i == 0) {
//				Log.v("tag", "子弹移动完成，当前坐标x：" + enemyBullets.get(i).getCurrentX() + "y:" + enemyBullets.get(i).getCurrentY());
			}
		}
		//移动工具
		for (int i = 0; i < cases.size(); i ++) {
			cases.get(i).move(time);
		}
	}

	/**
	 * 创建敌机
	 */
	private void createEnemyPlanes() {
		// TODO Auto-generated method stub
		//从关卡类获得敌机类型-敌机数量，敌机类型-出现间隔的映射
		Map<EnemyType, Integer> amountMap = round.getEnemyNumberMap();
		Map<EnemyType, Integer> delayMap = round.getEnemyDelayMap();
		EnemyPlane enemyPlane = null;
		
		for (int i = 0; i < enemyTypes.length; i ++) {
			if( amountMap.containsKey(enemyTypes[i]))
				if ( planesFrameCount % delayMap.get(enemyTypes[i]) == 1
					&& planesFrameCount > tempTime) {
					for (int j = 0; j < amountMap.get(enemyTypes[i]); j ++) {
						enemyPlane = factroy.createEnemyPlane(enemyTypes[i]);
						enemyPlanes.add(enemyPlane);
					}
				}
		}
	}

	/**
	 * 创建敌机的子弹
	 */
	private void createEnemyBullets() {
		// TODO Auto-generated method stub
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			enemyPlanes.get(i).addShootFrameCount();
			for (int j = 0; j < bulletTypes.length; j ++) {
				if (enemyPlanes.get(i).getBulletsMap().containsKey(bulletTypes[j])) {
					if ( enemyPlanes.get(i).canShoot(bulletTypes[j]) ) {
						enemyBullets.add(enemyPlanes.get(i).createBullet(bulletTypes[j]));
					}
				}
			}
		}
		
	}

	/**
	 * 绘制图像
	 */
	private void draw() {
		// TODO Auto-generated method stub
		if (canvas == null)
			return;
		background.drawSelf(canvas);
		//绘制玩家的子弹
		for (int i = 0; i < playerBullets.size(); i ++) {
			if ( ! (playerBullets.get(i).isUsed()) )
				playerBullets.get(i).drawSelf(canvas);
		}
		playerPlane.drawSelf(canvas);
		//绘制敌机子弹
		for (int i = 0; i < enemyBullets.size(); i ++) {
			if ( ! (enemyBullets.get(i).isUsed()) )
				enemyBullets.get(i).drawSelf(canvas);
		}
		//绘制敌机
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			if (enemyPlanes.get(i).getBlood() > 0)
				enemyPlanes.get(i).drawSelf(canvas);
		}
		
		//绘制工具箱
		for (int i = 0; i < cases.size(); i ++) {
			if (!cases.get(i).isUsed() && cases.get(i).getLife() > 0)
				cases.get(i).drawSelf(canvas);
		}
	}

	/**
	 * 触发大招
	 * 所有敌机扣1000点血量
	 * 所有敌机子弹消失
	 */
	private void createBigBoom() {
		// TODO Auto-generated method stub
		for (int i = 0; i < enemyPlanes.size(); i ++) {
			enemyPlanes.get(i).cutBlood(1000);
		}
		enemyBullets.clear();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			//判断是否为双击事件
			count++;  
            if(count == 1){  
                firClick = System.currentTimeMillis();  
            } 
            else if (count == 2){  
                secClick = System.currentTimeMillis();  
                if(secClick - firClick < 1000){  
                    //双击事件  
                	if (playerPlane.getBlood() >= 500) {
                		createBigBoom();  
                		playerPlane.cutBlood(100);
                	}
                } 
                count = 0;  
                firClick = 0;  
                secClick = 0; 
            }
             
		case MotionEvent.ACTION_MOVE:
			//记录目标位置
			int touchX = (int)event.getX();
			int touchY = (int)event.getY();
			if(touchX > screenWidth - 22)
				playerPlane.setDestX(screenWidth -22);
			else if (touchX < 22)
				playerPlane.setDestX(22);
			else
				playerPlane.setDestX(touchX);
			if(touchY > screenHeight)
				playerPlane.setDestY(screenHeight- 35 - 25);
			else if (touchY < 70 + 25)
				playerPlane.setDestY(35);
			else
				playerPlane.setDestY(touchY - 35 - 25);
			break;
		}
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			gameIsBreaked = true;
//			timer.cancel();
		}
		return super.onKeyDown(keyCode, event);
	}
}

	
