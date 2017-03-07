package com.niit.project.radiom.object;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author songhui
 * 这是一个背景类，通过实例化这个类，并调用相关的方法，
 * 可以实现背景的移动功能
 * 背景移动方式：通过将一张图片的分成两部分，
 * 然后每次把这两部分放在屏幕不同的位置上，以此来实现移动效果
 *
 */
public class Background {
	private Bitmap bitmap;//用于绘制背景
	private AssetManager assetManager;
	private String imagePath;//背景图片资源
	private int screenWidth;//屏幕宽度
	private int screenHeight;//屏幕高度
	private int top;//背景移动时第二张图片的左上方点y轴坐标

	/**
	 * 构造函数
	 * @param assetManager
	 * @param imagePath
	 * @param screenWidth
	 * @param screenHeight
	 * @param top
	 */
	public Background(AssetManager assetManager,
			String imagePath, int screenWidth, int screenHeight, int top) {
		super();
		this.assetManager = assetManager;
		this.imagePath = imagePath;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.top = top;
		//获得bitmap对象
		InputStream is;
		try {
			is = assetManager.open(imagePath);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * 每次改变背景图片第二部分向屏幕填充的位置，以此实现背景移动
	 */
	public void move() {
		top = (top + 1) % screenHeight;
	}
	
	/**
	 * 根据当前的top值来绘制背景
	 * @param canvas 用于绘图的画布对象
	 */
	public void drawSelf(Canvas canvas) {		
		Rect src = new Rect(0, 0, screenWidth, screenHeight);//截取背景图片的范围
		Rect dst1 = new Rect(0, top, screenWidth, top + screenHeight);//填充屏幕的第一个部分
		Rect dst2 = new Rect(0, top-screenHeight, screenWidth, top);//填充屏幕的第二个部分
		
		//绘制背景
		if (canvas == null)
			return;
		canvas.drawBitmap(bitmap, src, dst1, null);
		canvas.drawBitmap(bitmap, src, dst2, null);
	}
}
