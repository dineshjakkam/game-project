package comp102x.project.model;

import comp102x.ColorImage;
import comp102x.project.view.GameScreen;

public class Bullet extends GameObject {

	public Bullet() {

		image = new ColorImage("images/bullet.png");
		image.setMovable(false);
		reset();
	}
	
	public void reset() {
		
		x = 0;
		y = 0;
		z = 0;
	}
}
