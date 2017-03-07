package com.niit.project.radiom.object;

import com.niit.project.radiom.ObjectFactroy.CaseType;

import android.content.res.AssetManager;

/**
 * 医药箱类，实例化这个类的对象可以产生不同的医药箱
 * @author songhui
 *
 */
public class MedicineCase extends Case {
	private int bloodAmount;//玩家吃到医药箱后增加的血量

	public MedicineCase(String imagePath, AssetManager assetManager,
			float speed, int currentX, int currentY, int destX, int destY,
			int life, boolean isUsed, int bloodAmount, CaseType type) {
		super(imagePath, assetManager, speed, currentX, currentY, destX, destY, life,
				isUsed, type);
		// TODO Auto-generated constructor stub
		this.bloodAmount = bloodAmount;
	}

	public int getBloodAmount() {
		return bloodAmount;
	}

	public void setBloodAmount(int bloodAmount) {
		this.bloodAmount = bloodAmount;
	}

}
