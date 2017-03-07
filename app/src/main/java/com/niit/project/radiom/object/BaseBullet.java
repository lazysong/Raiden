package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * 基本的子弹类，通过继承这个类，
 * 可以实现不同类型的子弹效果
 * @author songhui
 *
 */
public abstract class BaseBullet {
	/**
	 * @author songhui
	 * 子弹类型的枚举类型
	 * 代表了子弹的不同类型和发射位置
	 *
	 */
	public static enum BulletType {
		bullet1, bullet1_left, bullet1_right,
		bullet2, bullet2_left, bullet2_right,
		bullet3, bullet3_left, bullet3_right,
		bullet4, bullet4_left, bullet4_right,
		bullet5, bullet5_left, bullet5_right,
		bullet6, bullet6_left, bullet6_right
		};
	
	protected String imagePath;//子弹图片
	protected AssetManager assetManager;
	protected Bitmap bitmap;//用于绘制子弹
	protected double speed = 0.1f;//每毫秒前进的px数
	protected double currentX;//当前的X轴坐标
	protected double currentY;//当前的Y轴坐标
	protected double destX;//目标位置的X轴坐标
	protected double destY;//目标位置的Y轴坐标
	protected int harm;//子弹的伤害值
	protected boolean isUsed;//当命中目标或者超出边界时子弹就被使用了
	
	public BaseBullet(String imagePath, AssetManager assetManager, double speed,
			double currentX, double currentY, double destX, double destY, int harm,
			boolean isUsed ) {
		if (imagePath == null)
			imagePath = "img/bullet2.png";//默认的子弹图片
		else
			this.imagePath = imagePath;
		this.assetManager = assetManager;
		this.speed = speed;
		this.currentX = currentX;
		this.currentY = currentY;
		this.destX = destX;
		this.destY = destY;
		this.harm = harm;
		this.isUsed = isUsed;
		//获得Bitmap对象
		InputStream is = null;
		try {
			is = assetManager.open(imagePath);
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
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

	public void setDestY(int destY) {
		this.destY = destY;
	}

	public int getHarm() {
		return harm;
	}

	public void setHarm(int harm) {
		this.harm = harm;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * 在指定的时间内根据自身速度，当前位置和目标位置来移动到下一个位置
	 * 通过对此函数的不同实现，可以产生子弹不同的运动轨迹
	 * @param time 单位为毫秒
	 */
	public abstract void move (int time);
	/**
	 * 在指定的时间内根据自身速度，当前位置和指定的目标位置来移动到下一个位置
	 * @param time 单位为毫秒
	 * @param destX 指定的X坐标
	 * @param destY 指定的Y坐标
	 */
	public abstract void move (int time, int destX, int destY);
	/**
	 * 根据自身参数在画布上绘制图形
	 * @param canvas
	 */
	public abstract void drawSelf (Canvas canvas);
	/**
	 * 检测是否超出了给定的范围
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 * @return 超出范围则返回true，否则返回false
	 */
	public abstract boolean isOverRange(int left, int right, int top, int bottom);
}
