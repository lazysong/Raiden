package com.niit.project.radiom.object;

import java.util.HashMap;
import java.util.Map;

import com.niit.project.radiom.ObjectFactroy.BackgroundType;
import com.niit.project.radiom.ObjectFactroy.CaseType;
import com.niit.project.radiom.ObjectFactroy.EnemyType;

/**
 * 关卡类，通过实例化这个类，可以定制各种类型的关卡
 * 每个关卡有每种类型敌机的数量和出现时间
 * 还包含每种工具箱的数量和出现时间
 * @author songhui
 *
 */
public class Round {
	private int roundNumber;//关卡数量
	//某种类型敌机的数量
	private Map<EnemyType,Integer> enemyNumberMap = new HashMap<EnemyType,Integer>();
	//某种类型敌机的出现时间
	private Map<EnemyType,Integer> enemyDelayMap = new HashMap<EnemyType,Integer>();
	//某种类型工具箱的数量
	private Map<CaseType,Integer> caseNumberMap = new HashMap<CaseType,Integer>();
	//某种类型工具箱的出现间隔时间
	private Map<CaseType,Integer> caseDelayMap = new HashMap<CaseType,Integer>();
	//关卡的限定时间，单位为毫秒
	private int time;
	//关卡的背景类型
	private BackgroundType backgroundType;
	
	public Round(int roundNumber, int time, BackgroundType backgroundType) {
		this.roundNumber = roundNumber;
		this.time = time;
		this.backgroundType = backgroundType;
	}
	
	/**
	 * 添加某种类型敌机
	 * @param type 敌机类型
	 * @param number 每次出现的数量
	 * @param delay 出现的间隔时间
	 */
	public void addEnemy(EnemyType type, int number, int delay) {
		if ( !enemyNumberMap.containsKey(type) && !enemyDelayMap.containsKey(type)) {
			enemyNumberMap.put(type, Integer.valueOf(number));
			enemyDelayMap.put(type, Integer.valueOf(delay));
		}
	}
	
	/**
	 * 添加某种类型的工具箱
	 * @param type 工具箱类型
	 * @param number 每次出现的数量
	 * @param delay 出现的间隔时间
	 */
	public void addCase(CaseType type, int number, int delay) {
		if ( !caseNumberMap.containsKey(type) && !caseDelayMap.containsKey(type)) {
			caseNumberMap.put(type, Integer.valueOf(number));
			caseDelayMap.put(type, Integer.valueOf(delay));
		}
	}

	/*
	 * 一些getter和setter方法
	 * */
	
	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public BackgroundType getBackgroundType() {
		return backgroundType;
	}

	public void setBackgroundType(BackgroundType backgroundType) {
		this.backgroundType = backgroundType;
	}

	public Map<EnemyType, Integer> getEnemyNumberMap() {
		return enemyNumberMap;
	}

	public void setEnemyNumberMap(Map<EnemyType, Integer> enemyNumberMap) {
		this.enemyNumberMap = enemyNumberMap;
	}

	public Map<EnemyType, Integer> getEnemyDelayMap() {
		return enemyDelayMap;
	}

	public void setEnemyDelayMap(Map<EnemyType, Integer> enemyDelayMap) {
		this.enemyDelayMap = enemyDelayMap;
	}

	public Map<CaseType, Integer> getCaseNumberMap() {
		return caseNumberMap;
	}

	public void setCaseNumberMap(Map<CaseType, Integer> caseNumberMap) {
		this.caseNumberMap = caseNumberMap;
	}

	public Map<CaseType, Integer> getCaseDelayMap() {
		return caseDelayMap;
	}

	public void setCaseDelayMap(Map<CaseType, Integer> caseDelayMap) {
		this.caseDelayMap = caseDelayMap;
	}

}
