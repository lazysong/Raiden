package com.niit.project.radiom.object;

import java.util.HashMap;
import java.util.Map;

import com.niit.project.radiom.ObjectFactroy.CaseType;
import com.niit.project.radiom.object.BaseBullet.BulletType;

import android.content.res.AssetManager;

/**
 * @author songhui
 * 弹药箱类，通过实例化这个类的对象，可以实现不同类型的弹药箱，
 * 每种弹药箱有不同类型，不同数量的子弹
 */
public class BulletsCase extends Case {
	/**
	 * 每种类型子弹的数量
	 */
	private Map<BulletType, Integer> bulletsMap
							= new HashMap<BulletType, Integer>();

	/**
	 * 构造函数，简单地调用了父类的构造函数
	 * @param imagePath
	 * @param assetManager
	 * @param speed
	 * @param currentX
	 * @param currentY
	 * @param destX
	 * @param destY
	 * @param life
	 * @param isUsed
	 * @param type
	 */
	public BulletsCase(String imagePath, AssetManager assetManager,
			float speed, int currentX, int currentY, int destX, int destY,
			int life, boolean isUsed, CaseType type) {
		super(imagePath, assetManager, speed, currentX, currentY, destX, destY, life,
				isUsed, type);
	}

	public Map<BulletType, Integer> getBulletsMap() {
		return bulletsMap;
	}

	public void setBulletsMap(Map<BulletType, Integer> bulletsMap) {
		this.bulletsMap = bulletsMap;
	}
	
	/**
	 * 为弹药箱添加一定数量的某种类型子弹
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
}
