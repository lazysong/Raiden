package com.niit.project.radiom.object;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * 对BaseBullet的基本继承
 * 通过实例化此对象，可以实现不同的子弹类型，比如子弹的图片，速度等等
 * @author songhui
 *
 */
public class Bullet extends BaseBullet {
	/**
	 * 简单调用了父类的构造方法
	 * @param imagePath
	 * @param assetManager
	 * @param speed
	 * @param currentX
	 * @param currentY
	 * @param destX
	 * @param destY
	 * @param harm
	 * @param isUsed
	 */
	public Bullet(String imagePath, AssetManager assetManager, double speed,
			double currentX, double currentY, double destX, double destY, int harm,
			boolean isUsed) {
		super(imagePath, assetManager, speed, currentX, currentY, destX, destY, harm,
				isUsed);
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
	public void drawSelf(Canvas canvas) {
		//获取bitmap的宽度和高度
		int bitmapHeight = bitmap.getHeight();
		int bitmapWidth= bitmap.getWidth();
		
		//设置图片中要用于填充的区域
		Rect src = new Rect(0, 0, bitmapWidth, bitmapHeight);
		//设置屏幕中要填充图片的区域
		Rect dst = new Rect( (int)(currentX - bitmapWidth/2),
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
	public boolean isOverRange(int left, int right, int top, int bottom) {
		if (currentX <= left
				|| currentX >= right
				|| currentY <= top
				|| currentY >= bottom)
			return true;
		return false;
	}

}
