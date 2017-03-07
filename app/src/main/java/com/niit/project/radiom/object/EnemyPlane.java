package com.niit.project.radiom.object;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.niit.project.radiom.ObjectFactroy.EnemyType;
import com.niit.project.radiom.object.BaseBullet.BulletType;

/**
 * 敌机类，通过实例化这个类，可以产生不同类型的敌机，
 * 如小型敌机、boss敌机等等
 * @author songhui
 *
 */
public class EnemyPlane extends BasePlane {
	public static enum MoveStyle { left_right, line, slash, pos_slash, still };
	private int awardScores;//敌机被击毁之后的奖励积分
	private EnemyType type;//敌机的类型，如boss1
	private MoveStyle moveStyle;
	private int step;

	
	/**
	 * 构造函数，通过传入一张长图和其他参数来实例化类
	 * 当要通过一张图来绘制爆炸效果的时候调用此构造函数
	 * @param imagePath
	 * @param boomImagePath
	 * @param boomCount
	 * @param assetManager
	 * @param speed
	 * @param currentX
	 * @param currentY
	 * @param destX
	 * @param destY
	 * @param blood
	 * @param boomFrameCount
	 * @param awardScores
	 * @param type
	 */
	public EnemyPlane(String imagePath, String boomImagePath, int boomCount, 
			AssetManager assetManager, float speed, int currentX, int currentY,
			int destX, int destY, int blood, int boomFrameCount, int awardScores, EnemyType type) {
		super(imagePath, boomImagePath, boomCount, assetManager, speed, currentX, currentY,
				destX, destY, blood, boomFrameCount);
		this.awardScores = awardScores;
		this.type = type;
	}

	/*
	 * 一些getter和setter方法
	 * */
	public int getAwardScores() {
		return awardScores;
	}

	public void setAwardScores(int awardScores) {
		this.awardScores = awardScores;
	}

	public EnemyType getType() {
		return type;
	}

	public void setType(EnemyType type) {
		this.type = type;
	}

	public MoveStyle getMoveStyle() {
		return moveStyle;
	}

	public void setMoveStyle(MoveStyle moveStyle) {
		this.moveStyle = moveStyle;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Override
	public void move(int time, int destX, int destY) {
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

	@Override
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

	public void styleMove (int time) {
		switch (moveStyle) {
		case left_right:
			if (currentX < 20 || currentX > 300)
				step = -step;
			currentX += step;
			break;
		case line:
			currentY += step;
			break;
		case slash:
			currentX += step;
			currentY += step;
			break;
		case pos_slash:
			currentX -= step;
			currentY -= step;
		case still:
			break;
		}
	}
	
	@Override
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

	@Override
	public void drawBoom(Canvas canvas) {
		//获取bitmap的宽度和高度
		int bitmapHeight = boomBitmap.getHeight();
		int bitmapWidth= boomBitmap.getWidth();
		
		//设置图片中要用于填充的区域
		Rect src = new Rect(boomFrameCount * bitmapWidth / boomCount, 0, 
				(boomFrameCount + 1) * bitmapWidth / boomCount, bitmapHeight);
		//设置屏幕中要填充图片的区域
		Rect dst = new Rect((int)(currentX - bitmapWidth/(2 * boomCount)),
				(int)(currentY - bitmapWidth/(2 * boomCount)), 
				(int)(currentX + bitmapWidth/(2 * boomCount)), 
				(int)(currentY + bitmapWidth/(2 * boomCount)));
		
		//当程序退出是可能会出现canvas为空的情况
		if (canvas == null)
			return;
		
        //根据bitmap来绘制图形
		canvas.drawBitmap(boomBitmap, src, dst, null);
	}
	
	@Override
	public boolean canShoot(BulletType type) {
		// TODO Auto-generated method stub
		if ( getShootFrameCount() % bulletDelayMap.get(type).longValue() == 0 
				&& bulletsMap.get(type).intValue() > 0 )
			return true;
		else
			return false;
	}

	@Override
	public BaseBullet createBullet(BulletType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case bullet1:
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX, currentY, currentX, 480, 20, false);
		case bullet1_left://发射位置为左边
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX - 40, currentY, currentX - 120, 480, 20, false);
		case bullet1_right://发射位置为右边
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX + 40, currentY, currentX + 120, 480, 20, false);
		case bullet2:
			return new Bullet("img/bullet2.png", assetManager, 0.1, currentX, currentY, currentX, 480, 20, false);
		case bullet2_left://发射位置为左边
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX - 40, currentY, currentX - 60, 480, 20, false);
		case bullet2_right://发射位置为右边
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX + 40, currentY, currentX + 60, 480, 20, false);
		case bullet3:
			return new Bullet("img/bullet3.png", assetManager, 0.1f, currentX, currentY, currentX, 480, 100, false);		
		case bullet3_left:
			return new Bullet("img/bullet3.png", assetManager, 0.1f, currentX - 10, currentY, currentX - 40, 480, 100, false);
		case bullet3_right:
			return new Bullet("img/bullet3.png", assetManager, 0.1f, currentX + 10, currentY, currentX + 40, 480, 100, false);
		case bullet4:
			return new Bullet("img/bullet4.png", assetManager, 0.1f, currentX, currentY, currentX, 480, 100, false);		
		case bullet4_left:
			return new Bullet("img/bullet4.png", assetManager, 0.1f, currentX - 10, currentY, currentX - 40, 480, 100, false);
		case bullet4_right:
			return new Bullet("img/bullet4.png", assetManager, 0.1f, currentX + 10, currentY, currentX + 40, 480, 100, false);
		default:
			return null;
		}
	}

	@Override
	public boolean isOverRange(int left, int top, int right, int bottom) {
		if (currentX <= left
				|| currentX >= right
				|| currentY <= top
				|| currentY >= bottom)
			return true;
		return false;
	}

	@Override
	public boolean detectCrash(BasePlane plane) {
		//敌机和玩家飞机的X轴距离绝对值
		double distanceX = Math.abs(plane.getCurrentX() - currentX);
		//敌机和玩家飞机的Y轴距离绝对值
		double distanceY = Math.abs(plane.getCurrentY() - currentY);
		if (((bitmap.getWidth() + plane.getBitmap().getWidth())/2 >= distanceX)
				&&
				((bitmap.getHeight() + plane.getBitmap().getHeight())/2 >= distanceY))
			return true;
		else
			return false;
	}

	@Override
	public boolean detectCrash(BaseBullet bullet) {
		//敌机和玩家子弹的X轴距离绝对值
		double distanceX = Math.abs(bullet.getCurrentX() - currentX);
		//敌机和玩家子弹的Y轴距离绝对值
		double distanceY = Math.abs(bullet.getCurrentY() - currentY);
		if (((bitmap.getWidth() + bullet.getBitmap().getWidth())/2 >= distanceX)
				&&
				((bitmap.getHeight() + bullet.getBitmap().getHeight())/2 >= distanceY))
			return true;
		else
			return false;
	}

}
