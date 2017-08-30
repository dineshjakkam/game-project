package comp102x.project.model;

public class PerspectiveProjection {

	private static final int UP = 1;
	private static final int DOWN = -1;
	private static final int LEFT = -1;
	private static final int RIGHT = 1;

	private double xAngle = 25;
	private double yAngle = 0;
	private double zAngle = 0;

	private double dist = 100000;

	private double screenWidth = 800;
	private double screenHeight = 600;
	private double x0 = screenWidth / 2;
	private double y0 = screenHeight;

	private double minX = -50000;
	private double maxX = 50000;
	private double minY = 0;
	private double maxY = 35000;
	private double mixZ = 0;
	private double maxZ = 100000;

	private double minScale = 0.2;

	private int xAxisDirection = PerspectiveProjection.RIGHT;
	private int yAxisDirection = PerspectiveProjection.DOWN;

	public double getWidth() {
		return screenWidth;
	}

	public void setWidth(double width) {
		this.screenWidth = width;
	}

	public double getHeight() {
		return screenHeight;
	}

	public void setHeight(double height) {
		this.screenHeight = height;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMixZ() {
		return mixZ;
	}

	public void setMixZ(double mixZ) {
		this.mixZ = mixZ;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}

	public double getMinScale() {
		return minScale;
	}

	public void setMinScale(double minScale) {
		this.minScale = minScale;
	}

	public int getxAxisDirection() {
		return xAxisDirection;
	}

	public void setxAxisDirection(int xAxisDirection) {
		if (xAxisDirection == PerspectiveProjection.LEFT
				|| xAxisDirection == PerspectiveProjection.RIGHT)
			this.xAxisDirection = xAxisDirection;
	}

	public int getyAxisDirection() {
		return yAxisDirection;
	}

	public void setyAxisDirection(int yAxisDirection) {
		if (yAxisDirection == PerspectiveProjection.UP
				|| yAxisDirection == PerspectiveProjection.DOWN)
			this.yAxisDirection = yAxisDirection;
	}

	public double getX0() {
		return x0;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}

	public double getY0() {
		return y0;
	}

	public void setY0(double y0) {
		this.y0 = y0;
	}

	public double getxAngle() {
		return xAngle;
	}

	public void setxAngle(double xAngle) {
		this.xAngle = xAngle;
	}

	public double getyAngle() {
		return yAngle;
	}

	public void setyAngle(double yAngle) {
		this.yAngle = yAngle;
	}

	public double getzAngle() {
		return zAngle;
	}

	public void setzAngle(double zAngle) {
		this.zAngle = zAngle;
	}

	public double getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(double screenWidth) {
		this.screenWidth = screenWidth;
	}

	public double getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(double screenHeight) {
		this.screenHeight = screenHeight;
	}

	public double[] translate(double x, double y, double z) {

		y += 1000;

		double ax = Math.toRadians(xAngle);
		double ay = Math.toRadians(yAngle);
		double az = Math.toRadians(zAngle);

		double rotatedX = Math.cos(ay) * (Math.sin(az) * y + Math.cos(az) * x)
				- Math.sin(ay) * z;
		double rotatedY = Math.sin(ax)
				* (Math.cos(ay) * z + Math.sin(ay)
						* (Math.sin(az) * y + Math.cos(az) * x)) + Math.cos(ax)
				* (Math.cos(az) * y - Math.sin(az) * x);
		double rotatedZ = Math.cos(ax)
				* (Math.cos(ay) * z + Math.sin(ay)
						* (Math.sin(az) * y + Math.cos(az) * x)) - Math.sin(ax)
				* (Math.cos(az) * y - Math.sin(az) * x);

		double newX = rotatedX * dist / (rotatedZ + dist);
		double newY = rotatedY * dist / (rotatedZ + dist);

		newX = xAxisDirection * (newX - minX) / (maxX - minX) * (screenWidth);
		newY = yAxisDirection * (newY - minY) / (maxY - minY) * (screenHeight)
				+ y0;

		return new double[] { newX, newY };
	}

	public double getScale(double z) {

		return (1.0 - z / maxZ) * (1 - minScale) + minScale;
	}
}
