package comp102x.project.model;

import java.util.Random;

import comp102x.ColorImage;
import comp102x.project.control.GameEngine;
import comp102x.project.task.TargetUpdater;
import comp102x.project.view.PlayGameView;

public class GameModel {
	
	public static int START = 0;
	public static int PLAYING = 1;
	public static int AIMING = 2;
	public static int SHOOTING = 3;
	public static int END = 4;

	private int allowedTime = 30;// in seconds
	private int updateTargetDelay = 1500; // in milliseconds
	
	private int numberOfTargetRows = 3;
	private int numberOfTargetPerRow = 3;
	
	private int targetZSeparation = 20000;
	private int targetXSeparation = 30000; 
	private int targetBaseZ = 50000;

	private int remainingTime;
	private int level;
	private int hitCount;
	private int score;
	private String playerName;

	private Target[] targets;
	private Bullet bullet;
	private Trajectory trajectory;
	
	private int state;
	
	public GameModel() {
		
		int numberOfTargets = numberOfTargetPerRow * numberOfTargetRows; 
		
		targets = new Target[numberOfTargets];
		for (int i = 0; i < numberOfTargets; i++) {
			targets[i] = new Target();
			targets[i].setOriginalImage(new ColorImage("images/target" + i + ".png"));
		}
		
		bullet = new Bullet();
		trajectory = new Trajectory();

	}

	public int getAllowedTime() {
		return allowedTime;
	}

	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		
		if (level == 1)
			updateTargetDelay = 1500;
		else if (level == 2)
			updateTargetDelay = 900;
		else
			updateTargetDelay = 600;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getScore() {
		
		return hitCount * 500 + remainingTime * 300;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Target[] getTargets() {
		return targets;
	}

	public void setTargets(Target[] targets) {
		this.targets = targets;
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public int getState() {
		return state;
	}

	public synchronized void setState(int state) {
		if (this.state == END) return;
		this.state = state;
	}

	public int getUpdateTargetDelay() {
		return updateTargetDelay;
	}

	public void setUpdateTargetDelay(int updateTargetDelay) {
		this.updateTargetDelay = updateTargetDelay;
	}

	public void reset() {
		
		state = START;
		remainingTime = allowedTime;
		level = 1;
		hitCount = 0;
		playerName = "noname";
		
		int numberOfTargets = numberOfTargetPerRow * numberOfTargetRows;
		
		for(int i = 0; i < numberOfTargets; i++) {
			
			targets[i].reset();
			targets[i].setX((i % numberOfTargetPerRow - 1) * targetXSeparation);
			targets[i].setY(0);
			targets[i].setZ(i / numberOfTargetPerRow * targetZSeparation + targetBaseZ);
			targets[i].setHit(false);
		}

		bullet.reset();
		trajectory.reset();
		
	}	
	
	public void updateTargetPositions() {
		
		TargetUpdater updater = new TargetUpdater();
		updater.updateTarget(targets, level);
	}
	
}

