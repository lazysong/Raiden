package com.niit.project.radiom.object;

import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.niit.project.radiom.object.BaseBullet.BulletType;

/**
 * 玩家飞机类
 * @author songhui
 *
 */
public class PlayerPlane extends BasePlane {
	/**
	 * 子弹类型的数组，在发射子弹和检测碰撞的时候会被使用
	 */
	BulletType[] types = new BulletType[]
			{
				BulletType.bullet1, BulletType.bullet1_left, BulletType.bullet1_right,
				BulletType.bullet2,	BulletType.bullet2_left, BulletType.bullet2_right,
				BulletType.bullet3,	BulletType.bullet3_left, BulletType.bullet3_right,
				BulletType.bullet4,	BulletType.bullet4_left, BulletType.bullet4_right,
				BulletType.bullet5,	BulletType.bullet5_left, BulletType.bullet5_right,
				BulletType.bullet6,	BulletType.bullet6_left, BulletType.bullet6_right,
			};

	
	/**
	 * 构造函数，通过一张长图路径和其他参数来实例化类
	 * 在使用一张长图来绘制爆炸效果的时候调用此构造函数
	 * */
	public PlayerPlane(String imagePath, String boomImagePath, int boomCount, 
			AssetManager assetManager, float speed, int currentX, int currentY,
			int destX, int destY, int blood, int boomFrameCount) {
		super(imagePath, boomImagePath, boomCount, assetManager, speed, currentX, currentY,
				destX, destY, blood, boomFrameCount);
	}

	/**
	 * 增加玩家飞机的血量，在玩家吃到了医药箱的时候回调用此方法
	 * @param addAmount
	 */
	public void addBlood(int addAmount) {
		blood += addAmount;
	}
	
	/**
	 * 为玩家添加一定数量的某种类型子弹
	 * @param type 子弹类型
	 * @param amount 子弹的数量
	 */
	public void addBullets(BulletType type, int amount) {
		//如果当前飞机没有装备此种类型的子弹，则添加此类型
		if (!bulletsMap.containsKey(type))
			bulletsMap.put(type, Integer.valueOf(amount));
		else { //如果当前飞机已经装备了这种类型的子弹,则修改此类型
			int oldAmount = bulletsMap.get(type);
			bulletsMap.remove(type);
			bulletsMap.put(type, oldAmount + amount);
		}
	}
	
	/**
	 * 通过一个Map对象来为玩家添加一系列一定数量的类型的子弹
	 * @param map
	 */
	public void addBullets(Map<BulletType, Integer> map) {
		for (int i = 0; i < types.length; i ++) {
			if (map.containsKey(types[i]))
				addBullet(types[i], map.get(types[i]));
		}
	}
	
	/**
	 * 减少某种类型的子弹数量，在每次发射完该类型子弹之后会调用这个方法
	 * @param type
	 * @param amount
	 */
	public void cutBullets(BulletType type, int amount) {
		for (int i = 0; i < types.length; i ++) {
			if (type == types[i]) {
				int oldAmount = bulletsMap.get(type);
				bulletsMap.remove(type);
				bulletsMap.put(type, oldAmount - amount);
			}
		}
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
		//每张小图的大小
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

	/*
	 * 先对子弹类型-数目映射的列表做循环，每次循环判断当前是什么种类的子弹
	 * 然后看当前的帧数计数器是否等于该类型子弹的射击延迟数相等，
	 * 并且查看当前的当前类型子弹的数目是否大于零，
	 * 如果两种条件都符合的话，则返回true，否则则返回false
	 * */
	@Override
	public boolean canShoot(BulletType type) {
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
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX, currentY, currentX, -1, 20, false);
		case bullet1_left://发射位置为左边
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX - 10, currentY, currentX - 20, -1, 20, false);
		case bullet1_right://发射位置为右边
			return new Bullet("img/bullet1.png", assetManager, 0.1, currentX + 10, currentY, currentX + 20, -1, 20, false);
		case bullet2:
			return new Bullet("img/bullet2.png", assetManager, 0.1, currentX, currentY, currentX, -1, 20, false);
		case bullet2_left://发射位置为左边
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX - 10, currentY, currentX - 20, -1, 20, false);
		case bullet2_right://发射位置为右边
			return new Bullet("img/bullet2.png", assetManager, 0.1f, currentX + 10, currentY, currentX + 20, -1, 20, false);
		case bullet3:
			return new Bullet("img/bullet3.png", assetManager, 0.5f, currentX, currentY, currentX, -1, 100, false);		
		case bullet3_left:
			return new Bullet("img/bullet3.png", assetManager, 0.5f, currentX - 10, currentY, currentX - 80, -1, 100, false);
		case bullet3_right:
			return new Bullet("img/bullet3.png", assetManager, 0.5f, currentX + 10, currentY, currentX + 80, -1, 100, false);
		case bullet4:
			return new Bullet("img/bullet4.png", assetManager, 0.5f, currentX, currentY, currentX, -1, 100, false);		
		case bullet4_left:
			return new Bullet("img/bullet4.png", assetManager, 0.5f, currentX - 10, currentY, currentX - 20, -1, 100, false);
		case bullet4_right:
			return new Bullet("img/bullet4.png", assetManager, 0.5f, currentX + 10, currentY, currentX + 20, -1, 100, false);
		default:
			return null;
		}
	}

	@Override
	public boolean isOverRange(int left, int top, int right, int bottom) {
		if (currentX < left + bitmap.getWidth()/2 
				|| currentX > right - bitmap.getWidth()/2
				|| currentY < bitmap.getHeight()/2
				|| currentY > bottom - bitmap.getHeight()/2)
			return true;
		return false;
	}

	@Override
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

	@Override
	public boolean detectCrash(BaseBullet bullet) {
		double distanceX = Math.abs(bullet.getCurrentX() - currentX);
		double distanceY = Math.abs(bullet.getCurrentY() - currentY);
		if (((bitmap.getWidth() + bullet.getBitmap().getWidth())/2 >= distanceX)
				&&
				((bitmap.getHeight() + bullet.getBitmap().getHeight())/2 >= distanceY))
			return true;
		else
			return false;
	}

}
