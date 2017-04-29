/*Represents a Quad in the Quad tree*/
public class Corner {
	private double x;
	private double y;
	private double radius;

	public Corner(double x, double y, double radius) {
		this.radius = radius;
		this.x = x;
		this.y = y;
	}

	/* Checks if planet is in range
	 * 
	 * @param planet planet to check
	 * 
	 * @return true if planet is in range of this corner, false if not.
	 */
	public boolean inRange(Planet planet) {

		if (planet.px <= this.x + radius && planet.px >= this.x - radius && planet.py <= this.y + radius
				&& planet.py >= this.y - radius)
			return true;
		return false;

	}
	/*Draws a square representing this corner. For testing purposes.*/
	public void draw() {
        StdDraw.rectangle(x, y, radius, radius);
        
	}
	/**************************
	* Getters and Setters... 
	/**************************/
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
