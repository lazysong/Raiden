package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;

import com.niit.project.radiom.ObjectFactroy.CaseType;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author songhui
 * 箱子类，通过继承这个类，可以实现不同的奖励道具，
 * 如弹药箱，血瓶等等
 */
public class Case {
	protected String imagePath;//工具箱的图片路径
	protected AssetManager assetManager;
	protected Bitmap bitmap;
	protected double speed = 0.1f;//每毫秒前进的px
	protected double currentX;
	protected double currentY;
	protected double destX;
	protected double destY;
	protected int life;//生命周期，不断减少，当小于等于0时即消失
	protected boolean isUsed;//当命中目标或者超出边界时子弹就被使用了
	protected CaseType type; //工具箱的类型
	
	public Case(String imagePath, AssetManager assetManager, float speed,
			int currentX, int currentY, int destX, int destY, int life,
			boolean isUsed, CaseType type ) {
		if (imagePath == null)
			imagePath = "img/bullet2.png";
		else
			this.imagePath = imagePath;
		this.assetManager = assetManager;
		this.speed = speed;
		this.currentX = currentX;
		this.currentY = currentY;
		this.destX = destX;
		this.destY = destY;
		this.life = life;
		this.isUsed = isUsed;
		this.type = type;
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
	
	/*
	 * 一些getter和setter方法
	 * 
	 * */
	
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	public void minusLife(int minus) {
		life -= minus;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public CaseType getType() {
		return type;
	}

	public void setType(CaseType type) {
		this.type = type;
	}

	/**
	 * 在画布上绘制图像
	 * @param canvas
	 */
	public void drawSelf(Canvas canvas) {
		//获取bitmap的宽度和高度
		int bitmapHeight = bitmap.getHeight();
		int bitmapWidth= bitmap.getWidth();
		
		//设置图片中要用于填充的区域
		Rect src = new Rect(0, 0, bitmapWidth, bitmapHeight);
		//设置屏幕中要填充图片的区域
		Rect dst = new Rect((int)(currentX - bitmapWidth/2),
				(int)(currentY - bitmapHeight/2), 
				(int)(currentX + bitmapWidth/2), 
				(int)(currentY + bitmapHeight/2));
		
		//当程序退出是可能会出现canvas为空的情况
		if (canvas == null)
			return;
		
        //根据bitmap来绘制图形
		canvas.drawBitmap(bitmap, src, dst, null);
	}
	
	/**
	 * 检测是否超出了指定的范围
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 * @return
	 */
	public boolean isOverRange(int left, int right, int top, int bottom) {
		if (currentX <= left
				|| currentX >= right
				|| currentY <= top
				|| currentY >= bottom)
			return true;
		return false;
	}
	
	/**
	 * 检测工具箱和飞机的碰撞，当判断玩家是否吃到工具箱的时候被调用
	 * @param plane
	 * @return 
	 */
	public boolean detectCrash(BasePlane plane) {
		double distanceX = Math.abs(plane.getCurrentX() - currentX);
		double distanceY = Math.abs(plane.getCurrentY() - currentY);
		if (((bitmap.getWidth() + plane.getBitmap().getWidth())/2 >= distanceX)
				&&
				((bitmap.getHeight() + plane.getBitmap().getHeight())/2 >= distanceY))
			return true;
		else
			return false;
	}
	
	/**
	 * 移动工具箱，从当前位置向目标位置移动，每次更新当前位置
	 * @param time
	 */
	public void move(int time) {
		double distanceX = destX - currentX;//当前位置和目标位置的X轴距离，具有方向性
		double distanceY = destY - currentY;//当前位置和目标位置的Y轴距离，具有方向性
		//当前位置和目标位置的直线距离
		double distance = Math.sqrt(distanceX*distanceX + distanceY*distanceY);
		//如果一次移动的速度大于当前位置和目标位置的直线距离，直接将当前位置更新为目标位置
		if (distance <= speed * time) {
			currentX = destX;
			currentY = destY;
		}
		else {//如果一次移动的速度小于当前位置和目标位置的直线距离，则更新当前位置
			currentX = currentX + distanceX * speed * time / distance;
			currentY = currentY + distanceY * speed * time / distance;
		}
	}
}
