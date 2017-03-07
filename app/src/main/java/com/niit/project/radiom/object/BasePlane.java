package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.niit.project.radiom.object.BaseBullet.BulletType;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * 基本的飞机类，通过继承这个类，可以实现不同类型的飞机
 * 如玩家飞机，boss飞机，喽啰飞机
 * 
 * 对于爆炸效果的绘制，这里提供了两种实现方式：
 * 一种是通过一系列图片构造多个位图对象，逐帧绘制，
 * 另一种方式是通过一张长图构造一个位图对象，每帧绘制位图对象的一部分
 * @author songhui
 * @see drawBoom()函数
 * 
 */
public abstract class BasePlane {
	/**
	 * 存储每种类型子弹的延迟时间，用于控制飞机发射子弹的间隔
	 */
	public static Map<BulletType, Integer> 
			bulletDelayMap = new HashMap<BulletType, Integer>();
	
	/**
	 * 记录飞机所携带的每种类型子弹的数量
	 * */
	protected Map<BaseBullet.BulletType, Integer> bulletsMap 
						= new HashMap<BaseBullet.BulletType, Integer>();
	
	protected String imagePath;//飞机的图片路径
	protected String[] boomImagePaths;//爆炸效果的系列图片路径
	protected String boomImagePath;//爆炸效果的单张图片路径
	protected Bitmap bitmap;//飞机的位图对象
	protected Bitmap[] boomBitmaps;//存放爆炸位图对象的数组
	protected Bitmap boomBitmap;//爆炸效果的单张图片位图对象
	protected int boomFrameCount;//爆炸效果的帧数计数器，不断增加
	protected long shootFrameCount;//发射子弹的帧数计数器，不断增加
	
	protected AssetManager assetManager;
	protected double speed = 0.1;//每毫秒前进的px
	protected double currentX;
	protected double currentY;
	protected double destX;
	protected double destY;
	protected int blood;
	protected int boomCount;
	
//	/**
//	 * 通过传入一系列爆炸图片和其他参数来实例化对象
//	 * @param imagePath 飞机图片路径
//	 * @param boomImagePaths 系列爆炸图片路径
//	 * @param assetManager
//	 * @param speed 飞机速度，单位为每毫秒前进的像素数
//	 * @param currentX 当前X坐标
//	 * @param currentY 当前Y坐标
//	 * @param destX 目标X坐标
//	 * @param destY 目标Y坐标
//	 * @param blood 血量
//	 * @param boomFrameCount 绘制爆炸时的帧数计数器，会不断增加
//	 */
//	public BasePlane(String imagePath, String[] boomImagePaths,
//			AssetManager assetManager, float speed, int currentX, int currentY,
//			int destX, int destY, int blood, int boomFrameCount) {
//		if (imagePath == null)
//			this.imagePath = "img/F22.png";
//		else
//			this.imagePath = imagePath;
//		if (boomImagePaths == null)
//			this.boomImagePaths = new String[]{"img/boom1.png", "img/boom1.png",
//				"img/boom1.png", "img/boom1.png", "img/boom1.png" };
//		else
//			this.boomImagePaths = boomImagePaths;
//		this.assetManager = assetManager;
//		this.speed = speed;
//		this.currentX = currentX;
//		this.currentY = currentY;
//		this.destX = destX;
//		this.destY = destY;
//		this.blood = blood;
//		this.boomFrameCount = 0;
//		this.shootFrameCount = 0;
//		
//		//获得飞机的Bitmap对象
//		InputStream is = null;
//		try {
//			is = assetManager.open(imagePath);
//			bitmap = BitmapFactory.decodeStream(is);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//获得飞机爆炸的Bitmap对象数组
//		try {
//			boomBitmaps = new Bitmap[this.boomImagePaths.length];
//			for (int i = 0; i < this.boomImagePaths.length; i ++) {
//				is = assetManager.open(this.boomImagePaths[i]);
//				boomBitmaps[i] = BitmapFactory.decodeStream(is);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//为每种类型的子弹添加射击延迟时间
//		bulletDelayMap.put(BulletType.bullet1, Integer.valueOf(20));
//		bulletDelayMap.put(BulletType.bullet1_left, Integer.valueOf(20));
//		bulletDelayMap.put(BulletType.bullet1_right, Integer.valueOf(20));
//		bulletDelayMap.put(BulletType.bullet2, Integer.valueOf(2));
//		bulletDelayMap.put(BulletType.bullet2_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet2_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet3, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet3_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet3_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet4, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet4_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet4_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet5, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet5_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet5_right, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet6, Integer.valueOf(1));
//		bulletDelayMap.put(BulletType.bullet6_left, Integer.valueOf(10));
//		bulletDelayMap.put(BulletType.bullet6_right, Integer.valueOf(10));
//	}
	
	public BasePlane(String imagePath, String boomImagePath, int boomCount,
			AssetManager assetManager, float speed, int currentX, int currentY,
			int destX, int destY, int blood, int boomFrameCount) {
		if (imagePath == null)
			this.imagePath = "img/F22.png";
		else
			this.imagePath = imagePath;
		boomImagePaths = null;
		this.boomImagePath = boomImagePath;
		this.assetManager = assetManager;
		this.speed = speed;
		this.currentX = currentX;
		this.currentY = currentY;
		this.destX = destX;
		this.destY = destY;
		this.blood = blood;
		this.boomFrameCount = 0;
		this.shootFrameCount = 0;
		this.boomCount = boomCount;
		
		//获得飞机的Bitmap对象
		InputStream is = null;
		try {
			is = assetManager.open(imagePath);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获得飞机爆炸的Bitmap对象
		try {
			is = assetManager.open(boomImagePath);
			boomBitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//为每种类型的子弹添加射击延迟时间
		bulletDelayMap.put(BulletType.bullet1, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet1_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet1_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet2, Integer.valueOf(4));
		bulletDelayMap.put(BulletType.bullet2_left, Integer.valueOf(4));
		bulletDelayMap.put(BulletType.bullet2_right, Integer.valueOf(4));
		bulletDelayMap.put(BulletType.bullet3, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet3_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet3_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet4, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet4_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet4_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet5, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet5_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet5_right, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet6, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet6_left, Integer.valueOf(20));
		bulletDelayMap.put(BulletType.bullet6_right, Integer.valueOf(20));
		
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String[] getBoomImagePaths() {
		return boomImagePaths;
	}

	public void setBoomImagePaths(String[] boomImagePaths) {
		this.boomImagePaths = boomImagePaths;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap[] getBoomBitmaps() {
		return boomBitmaps;
	}

	public void setBoomBitmaps(Bitmap[] boomBitmaps) {
		this.boomBitmaps = boomBitmaps;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getCurrentX() {
		return currentX;
	}

	public void setCurrentX(double currentX) {
		this.currentX = currentX;
	}

	public double getCurrentY() {
		return currentY;
	}

	public void setCurrentY(double currentY) {
		this.currentY = currentY;
	}

	public double getDestX() {
		return destX;
	}

	public void setDestX(double destX) {
		this.destX = destX;
	}

	public double getDestY() {
		return destY;
	}

	public void setDestY(double destY) {
		this.destY = destY;
	}

	public int getBlood() {
		return blood;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}
	
	public void cutBlood(int harm) {
		blood -= harm;
	}

	public int getBoomFrameCount() {
		return boomFrameCount;
	}
	
	public void addBoomFrameCount() {
		boomFrameCount ++;
	}
	
	public long getShootFrameCount() {
		return shootFrameCount;
	}

	/**
	 * 每次射击一颗子弹，调用此函数来增加shootFrameCount
	 */
	public void addShootFrameCount() {
		shootFrameCount ++;
	}

	public Map<BaseBullet.BulletType, Integer> getBulletsMap() {
		return bulletsMap;
	}

	public void setBulletsMap(Map<BaseBullet.BulletType, Integer> bulletsMap) {
		this.bulletsMap = bulletsMap;
	}

	public void addBullet(BulletType type, int number) {
		bulletsMap.put( type, new Integer(number) );
	}
	
	public int getBoomCount() {
		return boomCount;
	}

	public void setBoomCount(int boomCount) {
		this.boomCount = boomCount;
	}

	/**
	 * 控制飞机的移动，在子类中可以实现飞机的不同移动类型
	 * @param time 每次移动的时间，单位为毫秒
	 * @param destX 目标位置X坐标
	 * @param destY 目标位置Y坐标
	 */
	public abstract void move (int time, int destX, int destY);
	/**
	 * 控制飞机的移动，在子类中可以实现飞机的不同移动类型
	 * @param time 每次移动的时间，单位为毫秒
	 */
	public abstract void move (int time);
	/**
	 * 绘制飞机
	 * @param canvas
	 */
	public abstract void drawSelf (Canvas canvas);
	/**
	 * 绘制飞机爆炸的效果
	 * @param canvas
	 */
	public abstract void drawBoom (Canvas canvas);
	/**
	 * 判断飞机当前能否发射某种类型的子弹
	 * @param type
	 * @return
	 */
	public abstract boolean canShoot (BaseBullet.BulletType type);
	/**
	 * 产生某种类型的子弹，在调用次函数前应该通过
	 * canShoot(BaseBullet.BulletType type)函数来判断能否发射此种类型的子弹
	 * @param type 要产生的子弹类型
	 * @return
	 */
	public abstract BaseBullet createBullet (BaseBullet.BulletType type);
	/**
	 * 检测飞机是否超过了指定的范围
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public abstract boolean isOverRange(int left, int top, int right, int bottom);
	/**
	 * 检测飞机和另外一架飞机的碰撞
	 * @param plane
	 * @return
	 */
	public abstract boolean detectCrash (BasePlane plane);
	/**
	 * 检测飞机和子弹的碰撞
	 * @param bullet
	 * @return
	 */
	public abstract boolean detectCrash (BaseBullet bullet);
}
