package comp102x.project.model;

import comp102x.ColorImage;

public class Target extends GameObject {

	private boolean hit;
	private ColorImage originalImage;
	private ColorImage explodeImage;

	public Target() {

//		originalImage = new ColorImage("images/target.png");
//		originalImage.setMovable(false);

		explodeImage = new ColorImage("images/explode.png");
		explodeImage.setMovable(false);
	}

	public void setOriginalImage(ColorImage originalImage) {
		
		this.originalImage = originalImage;
		this.originalImage.setMovable(false);
	}

	public void reset() {

		hit = false;
		originalImage.setScale(1.0);
		image = originalImage;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {

		this.hit = hit;
		ColorImage updateImage = hit ? explodeImage : originalImage;

		updateImage.setX(image.getX());
		updateImage.setY(image.getY());
		updateImage.setScale(image.getScale());

		image = updateImage;
		
	}

}
