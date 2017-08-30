package comp102x.project.model;

import comp102x.ColorImage;

public class GameObject {
	
	protected int x;
	protected int y;
	protected int z;
	
	protected ColorImage image;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public ColorImage getImage() {
		return image;
	}
	public void setImage(ColorImage image) {
		this.image = image;
	}

}
