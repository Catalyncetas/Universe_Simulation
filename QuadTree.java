/*Contains basic operations of a quad tree.*/

public class QuadTree {

	private Planet planet;
	private Corner corner;
	private QuadTree upperLeft;
	private QuadTree upperRight;
	private QuadTree lowerRight;
	private QuadTree lowerLeft;
	public String name;

	public QuadTree(Corner newCorner,String name) {
		this.lowerLeft = null;
		this.lowerRight = null;
		this.upperLeft = null;
		this.upperRight = null;
		this.planet = null;
		this.name = name;
		this.corner = newCorner;
	}

	/*Checks if this node is external or internal
	 * 
	 * @return true if node is external, false if node is internal.
	 */
	public boolean isExternal() {
		if (this.lowerLeft == null && this.lowerRight == null && this.upperLeft == null && this.upperRight == null)
			return true;
		return false;
	}

	/* Selects correct node for the planet and
	 * inserts it to this tree.
	 * 
	 * @param planet planet to insert
	 */
	public void insert(Planet planet) {

		if (this.planet == null && this.isExternal()) {
			this.planet = planet;
			return;
		}
		// if internal
		if (!this.isExternal()) {
			this.centerOfMass(planet);/*!!!!!!!*/
			this.insertToCorner(planet);
			
			
		}
		// if external
		else {
			this.subDivide();	

			this.insertToCorner(this.planet);
			this.insertToCorner(planet);
			
			this.centerOfMass(planet);
		}
	}

	/* Selects corner for given planet
	 * 
	 * @param planet planet to insert
	 */
	private void insertToCorner(Planet planet) {
		if (this.upperLeft.corner.inRange(planet)) {
			upperLeft.insert(planet);
		} else if (this.upperRight.corner.inRange(planet)) {
			upperRight.insert(planet);
		} else if (this.lowerRight.corner.inRange(planet)) {
			lowerRight.insert(planet);
		} else if (this.lowerLeft.corner.inRange(planet)) {
			lowerLeft.insert(planet);
		}
	}

	/*Creates 4 children for this tree element.
	 * 
	 */
	private void subDivide() {
		double tempX = this.corner.getX();
		double tempY = this.corner.getY();
		double tempR = this.corner.getRadius();

		this.upperLeft = new QuadTree(new Corner(tempX - tempR / 2, tempY + tempR / 2, tempR / 2),"upperLeft");
		this.upperRight = new QuadTree(new Corner(tempX + tempR / 2, tempY + tempR / 2, tempR / 2),"upperRight");
		this.lowerRight = new QuadTree(new Corner(tempX + tempR / 2, tempY - tempR / 2, tempR / 2),"lowerRight");
		this.lowerLeft = new QuadTree(new Corner(tempX - tempR / 2, tempY - tempR / 2, tempR / 2),"lowerLeft");
	}
	/*For calculating center of mass.
	 * 
	 * @param planet planet to add to center of the mass
	 */
	public void centerOfMass(Planet planet){
		Planet thisPlanet = this.getPlanet();
		
		double mass = planet.mass + thisPlanet.mass;
		double px = (thisPlanet.px * thisPlanet.mass + planet.px * planet.mass) / mass;
		double py = (thisPlanet.py * thisPlanet.mass + planet.py * planet.mass) / mass;	
		
		this.setPlanet(new Planet(px,py,mass));
		
	}
	/*Traverses the tree, while traversing draws the quadtree 
	 * and prints the planets and their depth with their location 
	 * that are in the corners.
	 * 
	 *  @param root root of the quad tree
	 *  @param i depth of the node*/
	public void traverse(QuadTree root,int i){
		if(root == null){
			return;
		}
		if(root.planet==null){
			System.out.println("null planet,"+i+","+root.name);
			
		}
		else{
			System.out.println(root.planet.image+","+i+","+root.name);
		}
		root.getCorner().draw();
		traverse(root.upperLeft,i+1);
		traverse(root.upperRight,i+1);
		traverse(root.lowerRight,i+1);
		traverse(root.lowerLeft,i+1);
		
	}
	
	
	/**************************
	* Getters and Setters... 
	/**************************/
	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		this.planet = planet;
	}

	public Corner getCorner() {
		return corner;
	}

	public void setCorner(Corner corner) {
		this.corner = corner;
	}

	public QuadTree getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(QuadTree upperLeft) {
		this.upperLeft = upperLeft;
	}

	public QuadTree getUpperRight() {
		return upperRight;
	}

	public void setUpperRight(QuadTree upperRight) {
		this.upperRight = upperRight;
	}

	public QuadTree getLowerRight() {
		return lowerRight;
	}

	public void setLowerRight(QuadTree lowerRight) {
		this.lowerRight = lowerRight;
	}

	public QuadTree getLowerLeft() {
		return lowerLeft;
	}

	public void setLowerLeft(QuadTree lowerLeft) {
		this.lowerLeft = lowerLeft;
	}
}
