package comp102x.project.view;

import java.awt.MouseInfo;

import comp102x.CanvasObject;
import comp102x.ColorImage;
import comp102x.Text;
import comp102x.project.control.GameEngine;
import comp102x.project.model.Bullet;
import comp102x.project.model.PerspectiveProjection;
import comp102x.project.model.Target;
import comp102x.project.model.Trajectory;

public class PlayGameView extends View {

	private ColorImage background;
	private ColorImage aim;
	private Text timeText;
	private Text scoreText;
	
	public PlayGameView() {
		
		background = new ColorImage("images/background.png");
		background.setMovable(false);
		
		aim = new ColorImage("images/aim.png");
		aim.setMovable(false);
		
		timeText = new Text("");
		timeText.setSize(40);
		timeText.setMovable(false);
		timeText.setColor(157, 63, 255);
		timeText.setX(20);
		timeText.setY(10);
		
		scoreText = new Text("");
		scoreText.setSize(40);
		scoreText.setMovable(false);
		scoreText.setColor(157, 63, 255);
		scoreText.setX(20);
		scoreText.setY(60);
	}
	
//	public void resetAim() {
//		
//		aim.setX(-aim.getWidth());
//	}

	public void loadView(Target[] targets, Bullet bullet) {

		elements.clear();

		elements.add(background);
		elements.add(timeText);
		elements.add(scoreText);

		for (int i = targets.length - 1; i >= 0; i--)
			elements.add(targets[i].getImage());

		elements.add(aim);
		elements.add(bullet.getImage());
	}

	public void updateElement(CanvasObject oldElement, CanvasObject newElement) {

		int index = elements.indexOf(oldElement);
		if (index != -1)
			elements.set(index, newElement);
	}

	@Override
	public void performClickAction(int x, int y, GameEngine gameEngine) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void performKeyAction(char key) {
		// TODO Auto-generated method stub
		
	}

	public void updateAim(Bullet bullet, PerspectiveProjection pp,
			Trajectory trajectory) {

		double totalTime = trajectory.getTotalT();

		double x = bullet.getX() + trajectory.getX(totalTime);
		double y = bullet.getY() + trajectory.getY(totalTime);
		double z = bullet.getZ() + trajectory.getZ(totalTime);

		double[] xy = pp.translate(x, y, z);
		double scale = pp.getScale(z);

		aim.setX((int) (xy[0] - aim.getWidth() * scale / 2));
		aim.setY((int) (xy[1] - aim.getHeight() * scale / 2));
		aim.setScale(scale);
	}

	public void setHitCount(int hitcount) {
		scoreText.setText("Hit Count: " + hitcount);
	}
	
	public void setTime(int time) {
		timeText.setText("Time: " + time);
	}

}
