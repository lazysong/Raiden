package com.niit.project.radiom.object;

/**
 * @author songhui
 * 玩家ID
 * 玩家姓名
 * 玩家积分
 */
public class Player {
	private String ID;
	private String name;
	private int scores;
	
	
	public Player(String iD, String name, int scores) {
		super();
		ID = iD;
		this.name = name;
		this.scores = scores;
	}
	
	/*
	 * getter和setter方法
	 * */
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScores() {
		return scores;
	}
	public void setScores(int scores) {
		this.scores = scores;
	}
	public void addScores(int award) {
		scores += award;
	}
	
}
