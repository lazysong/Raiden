package com.niit.project.radiom;

import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;

import com.niit.project.radiom.object.Background;
import com.niit.project.radiom.object.BaseBullet.BulletType;
import com.niit.project.radiom.object.BulletsCase;
import com.niit.project.radiom.object.Case;
import com.niit.project.radiom.object.EnemyPlane;
import com.niit.project.radiom.object.EnemyPlane.MoveStyle;
import com.niit.project.radiom.object.MedicineCase;
import com.niit.project.radiom.object.PlayerPlane;
import com.niit.project.radiom.object.Round;

public class ObjectFactroy {
	public static enum PlaneType {
		player1, player2, player3, 
		simpleEnemy, smallEnemy1, smallEnemy2, smallEnemy3, 
		smallEnemy4, smallEnemy5, smallEnemy6,
		boss1, boss2, boss3 };
	public static enum CaseType { medicineCase, bulletsCase, bulletsCase1, bulletsCase2 };
	public static final int MEDICINE_CASE = 1;
	public static final int BULLETS_CASE = 2;
	public static enum EnemyType {
		simpleEnemy, boss1, boss2, boss3,
		smallEnemy1, smallEnemy2, smallEnemy3, 
		smallEnemy4, smallEnemy5, smallEnemy6,};
	public static enum RoundType {round1, round2, round3};
	public static enum BackgroundType {sky, forest, desert};
	
	private Context context;
	private AssetManager assetManager;
	private int screenWidth;
	private int screenHeight;
	
	private MoveStyle[] moveStyles = new EnemyPlane.MoveStyle[]
			{ 
				MoveStyle.left_right, MoveStyle.line, 
				MoveStyle.slash, MoveStyle.still,
				MoveStyle.pos_slash
			};
	
	public ObjectFactroy(Context context, int screenWidth, int screenHeight) {
		this.context = context;
		assetManager = this.context.getAssets();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	/**
	 * @param type
	 * @return
	 */
	public PlayerPlane createPlayerPlane(PlaneType type) {
		switch (type) {
		case player1:
			return new PlayerPlane("img/myPlane1.png", "img/boom.png", 10, 
					assetManager, 0.5f, screenWidth /2, screenHeight -40, 
					screenWidth /2, screenHeight -40, 10000, 6);//最后一个参数和爆炸的图片数组长度有关
		case player2:
			return new PlayerPlane("img/myPlane2.png", "img/boom.png", 10, 
					assetManager, 0.5f, screenWidth /2, screenHeight -40, 
					screenWidth /2, screenHeight -40, 20000, 6);//最后一个参数和爆炸的图片数组长度有关
		case player3:
			return new PlayerPlane("img/myPlane3.png", "img/boom.png", 10, 
					assetManager, 0.5f, screenWidth /2, screenHeight -40, 
					screenWidth /2, screenHeight -40, 30000, 6);//最后一个参数和爆炸的图片数组长度有关
		default:
			return null;
		}
	}
	
	public EnemyPlane createEnemyPlane(EnemyType type) {
		Random random = new Random();
		int currentX, currentY, destX, destY;
		EnemyPlane enemyPlane = null;
		switch (type) {
		case smallEnemy1:
			//确定随机位置
			currentX = random.nextInt(screenWidth - 40) + 20;
			currentY = random.nextInt(screenHeight / 3) + 10;
			destX = random.nextInt(screenWidth - 20) + 10;
			destY = screenHeight + 10;
			enemyPlane = new EnemyPlane("img/smallEnemy_1.png", "img/smallEnemyBoom1.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 20, 6, 40, EnemyType.smallEnemy1);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet1, 1000);
			enemyPlane.setStep(random.nextInt(5));
			enemyPlane.setMoveStyle(moveStyles[random.nextInt(5)]);
			return enemyPlane;
		case smallEnemy2:
			currentX = random.nextInt(screenWidth - 40) + 20;
			currentY = random.nextInt(screenHeight / 3) + 10;
			destX = random.nextInt(screenWidth - 20) + 10;
			destY = screenHeight + 10;
			enemyPlane = new EnemyPlane("img/smallEnemy_2.png", "img/smallEnemyBoom1.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 20, 6, 40, EnemyType.smallEnemy2);//最后一个参数和爆炸的图片数组长度有关
//			enemyPlane.addBullet(BulletType.bullet1, 1000);
			enemyPlane.setStep(random.nextInt(5));
			enemyPlane.setMoveStyle(moveStyles[random.nextInt(5)]);
			return enemyPlane;
		case smallEnemy3:
			currentX = random.nextInt(screenWidth - 40) + 20;
			currentY = random.nextInt(screenHeight / 3) + 10;
			destX = random.nextInt(screenWidth - 20) + 10;
			destY = screenHeight + 10;
			enemyPlane = new EnemyPlane("img/smallEnemy_3.png", "img/smallEnemyBoom1.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 40, 6, 40, EnemyType.smallEnemy3);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet1, 1000);
			enemyPlane.setStep(random.nextInt(5));
			enemyPlane.setMoveStyle(moveStyles[random.nextInt(5)]);
			return enemyPlane;
		case smallEnemy4:
			currentX = random.nextInt(screenWidth - 40) + 20;
			currentY = random.nextInt(screenHeight / 3) + 10;
			destX = random.nextInt(screenWidth - 20) + 10;
			destY = screenHeight + 10;
			enemyPlane = new EnemyPlane("img/smallEnemy_4.png", "img/smallEnemyBoom1.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 40, 6, 40, EnemyType.smallEnemy4);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet1, 1000);
			enemyPlane.setStep(random.nextInt(5));
			enemyPlane.setMoveStyle(moveStyles[random.nextInt(5)]);
			return enemyPlane;
		case smallEnemy5:
			currentX = random.nextInt(screenWidth - 40) + 20;
			currentY = random.nextInt(screenHeight / 3) + 10;
			destX = random.nextInt(screenWidth - 20) + 10;
			destY = screenHeight + 10;
			enemyPlane = new EnemyPlane("img/smallEnemy_5.png", "img/smallEnemyBoom1.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 40, 6, 40, EnemyType.smallEnemy5);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet1, 1000);
			enemyPlane.setStep(random.nextInt(5));
			enemyPlane.setMoveStyle(moveStyles[random.nextInt(5)]);
			return enemyPlane;
		case smallEnemy6:
			currentX = random.nextInt(screenWidth - 40) + 20;
			currentY = random.nextInt(screenHeight / 3) + 10;
			destX = random.nextInt(screenWidth - 20) + 10;
			destY = screenHeight + 10;
			enemyPlane = new EnemyPlane("img/smallEnemy_6.png", "img/smallEnemyBoom.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 20, 6, 40, EnemyType.smallEnemy6);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet1, 1000);
			enemyPlane.setStep(random.nextInt(5));
			enemyPlane.setMoveStyle(moveStyles[random.nextInt(5)]);
			return enemyPlane;
		case boss1:
			currentX = screenWidth / 2;
			currentY = 10;
			destX = screenWidth / 2;
			destY = screenHeight / 4;
			enemyPlane = new EnemyPlane("img/boss1.png", "img/boom.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 20000, 6, 500, EnemyType.boss1);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet5, 1000);
			enemyPlane.addBullet(BulletType.bullet1_left, 1000);
			enemyPlane.addBullet(BulletType.bullet1_right, 1000);
			enemyPlane.setStep(5);
			enemyPlane.setMoveStyle(MoveStyle.left_right);
			return enemyPlane;
		case boss2:
			currentX = screenWidth / 2;
			currentY = 10;
			destX = screenWidth / 2;
			destY = screenHeight / 4;
			enemyPlane =  new EnemyPlane("img/boss2.png", "img/boom.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 5000, 6, 1000, EnemyType.boss1);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet5, 1000);
			enemyPlane.addBullet(BulletType.bullet1_left, 1000);
			enemyPlane.addBullet(BulletType.bullet1_right, 1000);
			enemyPlane.setStep(5);
			enemyPlane.setMoveStyle(MoveStyle.left_right);
			return enemyPlane;
		case boss3:
			currentX = screenWidth / 2;
			currentY = 10;
			destX = screenWidth / 2;
			destY = screenHeight / 4;
			enemyPlane =  new EnemyPlane("img/boss3.png", "img/boom.png", 10, 
					assetManager, 0.05f, currentX, currentY, 
					destX, destY, 10000, 6, 5000, EnemyType.boss1);//最后一个参数和爆炸的图片数组长度有关
			enemyPlane.addBullet(BulletType.bullet5, 1000);
			enemyPlane.addBullet(BulletType.bullet1_left, 1000);
			enemyPlane.addBullet(BulletType.bullet1_right, 1000);
			enemyPlane.setStep(5);
			enemyPlane.setMoveStyle(MoveStyle.left_right);
			return enemyPlane;
		default:
			return null;
		}
	}
	
	public Background createBackground(BackgroundType type) {
		AssetManager assetManager = context.getAssets();
		switch (type) {
		case forest:
			return new Background(assetManager, "img/forest.png", 320, 450, 0);
		case sky:
			return new Background(assetManager, "img/sky.png", 320, 450, 0);
		case desert:
			return new Background(assetManager, "img/desert.png", 320, 450, 0);
		default:
			return null;
		}
	}
	
//	public MedicineCase createMedicineCase(CaseType type) {
//		switch (type) {
//		case medicineCase:
//			return new MedicineCase("img/medicineCase.png", assetManager, 0.05f, 200, 30, 100, 300, 100, false, 500, CaseType.medicineCase);
//		default:
//			return null;
//		}
//	}
	
//	public BulletsCase createBulletsCase(CaseType type) {
//		BulletsCase bulletCase = null;
//		switch (type) {
//		case bulletsCase:
//			bulletCase = new BulletsCase("img/bulletsCase.png", assetManager, 0.05f, 100, 30, 200, 300, 100, false, CaseType.bulletsCase);
//			bulletCase.addBullets(BulletType.bullet3, 400);
//			return bulletCase;
//		case bulletsCase1:
//			bulletCase = new BulletsCase("img/bulletsCase.png", assetManager, 0.05f, 100, 30, 200, 300, 100, false, CaseType.bulletsCase1);
//			bulletCase.addBullets(BulletType.bullet3_left, 400);
//			bulletCase.addBullets(BulletType.bullet3_right, 400);
//			return bulletCase;
//		case bulletsCase2:
//			bulletCase = new BulletsCase("img/bulletsCase.png", assetManager, 0.05f, 100, 30, 200, 300, 100, false, CaseType.bulletsCase2);
//			bulletCase.addBullets(BulletType.bullet4, 10);
//			return bulletCase;
//		default:
//			return null;
//		}
//	}
	
	public Case createCase(CaseType type) {
		BulletsCase bulletCase = null;
		//确定随机位置
		Random random = new Random();
		int currentX = random.nextInt(screenWidth - 20) + 10;
		int currentY = random.nextInt(screenHeight / 3) + 10;
		int destX = random.nextInt(screenWidth - 20) + 10;
		int destY = screenHeight + 10;
		switch (type) {
		case medicineCase:
			return new MedicineCase("img/medicineCase.png", assetManager, 0.05f, currentX, currentY, destX, destY, 100, false, 100, CaseType.medicineCase);
		case bulletsCase:
			bulletCase = new BulletsCase("img/bulletsCase.png", assetManager, 0.05f, currentX, currentY, destX, destY, 100, false, CaseType.bulletsCase);
			bulletCase.addBullets(BulletType.bullet3, 40);
			return bulletCase;
		case bulletsCase1:
			bulletCase = new BulletsCase("img/bulletsCase.png", assetManager, 0.05f, currentX, currentY, destX, destY, 100, false, CaseType.bulletsCase1);
			bulletCase.addBullets(BulletType.bullet3_left, 40);
			bulletCase.addBullets(BulletType.bullet3_right, 40);
			return bulletCase;
		case bulletsCase2:
			bulletCase = new BulletsCase("img/bulletsCase.png", assetManager, 0.05f, currentX, currentY, destX, destY, 100, false, CaseType.bulletsCase2);
			bulletCase.addBullets(BulletType.bullet4, 40);
			return bulletCase;
		default:
			return null;
		}
	}
	
	public Round createRound(RoundType type) {
		Round round = null;
		switch (type) {
		case round1:
			round = new Round(1, 1000 * 60 * 2, BackgroundType.forest);
			round.addCase(CaseType.bulletsCase, 1, 600);
			round.addCase(CaseType.bulletsCase1, 1, 800);
//			round.addCase(CaseType.bulletsCase2, 1, 130);
			round.addCase(CaseType.medicineCase, 1, 700);
			round.addEnemy(EnemyType.smallEnemy1, 4, 80);
			round.addEnemy(EnemyType.smallEnemy2, 2, 100);
//			round.addEnemy(EnemyType.boss1, 1, 5000);
			return round;
		case round2:
			round = new Round(2, 1000 * 60 * 3, BackgroundType.sky);
			round.addCase(CaseType.bulletsCase1, 1, 600);
			round.addCase(CaseType.bulletsCase2, 1, 800);
			round.addCase(CaseType.medicineCase, 1, 1000);
			round.addEnemy(EnemyType.smallEnemy2, 2, 80);
			round.addEnemy(EnemyType.smallEnemy3, 1, 100);
			round.addEnemy(EnemyType.smallEnemy4, 2, 150);
//			round.addEnemy(EnemyType.boss1, 1, 5000);
//			round.addEnemy(EnemyType.boss2, 1, 10000);
			return round;
		case round3:
			round = new Round(3, 1000 * 60 * 3, BackgroundType.desert);
			round.addCase(CaseType.bulletsCase1, 1, 600);
			round.addCase(CaseType.bulletsCase2, 1, 800);
			round.addCase(CaseType.medicineCase, 1, 1000);
			round.addEnemy(EnemyType.smallEnemy4, 1, 80);
			round.addEnemy(EnemyType.smallEnemy5, 2, 100);
			round.addEnemy(EnemyType.smallEnemy6, 1, 150);
//			round.addEnemy(EnemyType.boss2, 1, 7000);
//			round.addEnemy(EnemyType.boss3, 1, 1500);
			return round;
		default:
			return null;
		}
	}

}
