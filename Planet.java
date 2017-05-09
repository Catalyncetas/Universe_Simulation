
/*Class representing all the planets in the universe.*/

import java.awt.Color;

public class Planet {
	
	public final static double G = 0.0000000000667;
	public final static double Theta = 0.5;
	public double px;
	public double py;
	public double vx;
	public double vy;
	public double mass;
	public double fx;
	public double fy;
	public double ax;
	public double ay;
	public String image;
	public Color color;
	
	
	public Planet(){
		px = 0.0;
		py = 0.0;
		vx = 0.0;
		vy = 0.0;
		mass = 0.0;
		image = "";
		color = null;
		fx = 0.0;
		fy = 0.0;
		ax = 0.0;
		ay = 0.0;
	}
	
	public Planet(double px,double py,double mass){
		this.px = px;
		this.py = py;
		vx = 0.0;
		vy = 0.0;
		this.mass = mass;
		image = "";
		color = null;
		fx = 0.0;
		fy = 0.0;
		ax = 0.0;
		ay = 0.0;
	}
	/*Calculate this planets acceleration*/
	public void calculateAcceleration(){
		this.ax = this.fx/this.mass;
		this.ay = this.fy/this.mass;
	}
	/*Calculate this planets velocity
	 * 
	 * @param deltaTime the time passed with current acceleration.*/
	public void calculateVelocity(double deltaTime){
		this.vx += deltaTime*this.ax;
		this.vy += deltaTime*this.ay;
	}
	/*Calculate this planets position
	 * 
	 * @param deltaTime the time passed with current velocity.*/
	public void calculatePosition(double deltaTime){		
		this.px += deltaTime*this.vx;
		this.py += deltaTime*this.vy;
	}
	/*Reset the net force on this planet*/
	public void restore(){
		this.fx = 0.0;
		this.fy = 0.0;
	}
	/*Find distance between this and secondaryPlanets in x axis.
	 * 
	 * @param secondaryPlanet planet to calculate the distance
	 * 
	 * @return distance_in_x_axis distance in x axis, negative or positive*/
	private double distanceXTo(Planet secondaryPlanet){
		return this.px-secondaryPlanet.px;
	}
	/*Find distance between this and secondaryPlanets in y axis
	 * 
	 * @param secondaryPlanet planet to calculate the distance
	 * 
	 * @return distance_in_y_axis distance in y axis, negative or positive*/
	private double distanceYTo(Planet secondaryPlanet){
		return this.py-secondaryPlanet.py;
	}
	/*Find distance between this and secondaryPlanet
	 * 
	 * @param secondaryPlanet planet 
	 * 
	 * @return distance positive distance between two planets*/
	public double distanceTo(Planet secondaryPlanet){
		double x = this.distanceXTo(secondaryPlanet);
		double y = this.distanceYTo(secondaryPlanet);
		return Math.sqrt(x*x + y*y);
	}
	/*Calculate the force that a planet does to this planet
	 * 
	 * @param secondaryPlanet the planet that does the force to this planet*/
	public void calculateForce(Planet secondaryPlanet){
		
		double tempF = 0.0;
		double distance = 0.0;
		double EPS = 3E4;

		distance = this.distanceTo(secondaryPlanet);
		
		tempF = -(G*this.mass*secondaryPlanet.mass)/(distance*distance+EPS*EPS);// multiply with -1 because distance can be negative		
		
		this.fx += tempF*(this.distanceXTo(secondaryPlanet)/distance);
		this.fy += tempF*(this.distanceYTo(secondaryPlanet)/distance);
	}
	/*Calculates the force that the whole tree does to this planet
	 * 
	 * @param root quad tree, which harbors all planets*/
	public void calculateForceQuad(QuadTree root){
		
		Planet planetInTree = root.getPlanet();
		
		if(planetInTree == null || planetInTree.equals(this))
			return;
		
		double r = root.getCorner().getRadius()*2;
		double d = this.distanceTo(planetInTree);
		
		//if external
		if(root.isExternal()){
			this.calculateForce(planetInTree);
		}
		
		else if((r/d)<Theta){
			this.calculateForce(planetInTree);
		}
		//if internal
		else{
			
			this.calculateForceQuad(root.getUpperLeft());
			this.calculateForceQuad(root.getUpperRight());
			this.calculateForceQuad(root.getLowerRight());
			this.calculateForceQuad(root.getLowerLeft());
			
		}
		
	}

}
