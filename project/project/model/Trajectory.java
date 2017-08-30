package comp102x.project.model;

public class Trajectory {
	
	private double initialVelocity;
    private double gravity;
	private double tiltAngle;
	private double panAngle;

	public void reset() {

		setGravity(10);
		setInitialVelocity(1000);
		setTiltAngle(35);
		setPanAngle(0);
	}

	public double getInitialVelocity() {
		return initialVelocity;
	}

	public void setInitialVelocity(double initialVelocity) {
		this.initialVelocity = initialVelocity;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getTiltAngle() {
		return tiltAngle;
	}

	public void setTiltAngle(double tiltAngle) {
		this.tiltAngle = tiltAngle;
	}

	public double getPanAngle() {
		return panAngle;
	}

	public void setPanAngle(double panAngle) {
		this.panAngle = panAngle;
	}

	public double getTotalT() {
		return 2 * initialVelocity * Math.sin(Math.toRadians(tiltAngle)) / gravity;
	}

	public double getX(double t) {
		return initialVelocity * Math.cos(Math.toRadians(tiltAngle)) * t
				* Math.sin(Math.toRadians(panAngle));
	}

	public double getY(double t) {
		return initialVelocity * Math.sin(Math.toRadians(tiltAngle)) * t - 0.5 * gravity * t * t;
	}

	public double getZ(double t) {
		return initialVelocity * Math.cos(Math.toRadians(tiltAngle)) * t
				* Math.cos(Math.toRadians(panAngle));
	}

}
