import java.awt.Color;
import java.util.ArrayList;

public class NBody {

	public static void main(String[] args) {	
		
		double dt = Double.parseDouble(args[1]); // time step: you can play this value to determine simulation speed
		double T = Double.parseDouble(args[0]); // end of the universe
		String command = args[2];
		int N = StdIn.readInt(); // number of bodies
		double radius = StdIn.readDouble(); // radius of universe
		
		StdDraw.show(0);
		StdDraw.setXscale(-radius, +radius);
		StdDraw.setYscale(-radius, +radius);
		
		ArrayList<Planet> planetList = new ArrayList<Planet>();
		
		int red=0,blue=0,green=0;
		
		for (int i = 0; i < N; i++) {
			
			Planet planet = new Planet();

			planet.px = StdIn.readDouble();
			planet.py = StdIn.readDouble();
			planet.vx = StdIn.readDouble();
			planet.vy = StdIn.readDouble();
			planet.mass = StdIn.readDouble();
			
			String temp = StdIn.readString();
			//if integer
			if(temp.matches("-?\\d+(\\.\\d+)?")){
				
				planet.image = null;
				red = Integer.parseInt(temp); 
				green = StdIn.readInt(); 
				blue = StdIn.readInt();
				Color color = new Color(red, green, blue);
				planet.color = color;
			}
			//if string
			else{
				planet.image = temp;

			}
			planetList.add(planet);
		}
		
		//draw part
		if(command.equals("brute"))
			brute(T,dt,planetList);	
		else if (command.equals("quad"))
			quad(T,dt,planetList,radius);
	}
	/*Simulates the universe with brute force algorithm.
	 * 
	 * @param T total time
	 * @param dt time interval
	 * @param planetList list of all planets*/
	private static void brute(double T, double dt, ArrayList<Planet> planetList) {
		for (double t = 0.0; t < T; t = t + dt) {

			StdDraw.clear(StdDraw.BLACK);
			
			if(planetList.get(0).image!=null)
				StdDraw.picture(0, 0, "images/starfield.jpg");
			
			for (int i = 0; i < planetList.size(); i++) {

				Planet mainPlanet = planetList.get(i);

				for (int j = 0; j < planetList.size(); j++) {

					Planet secondaryPlanet = planetList.get(j);
					if (i != j)
						mainPlanet.calculateForce(secondaryPlanet);

				}

				mainPlanet.calculateAcceleration();
				mainPlanet.calculateVelocity(dt);
				mainPlanet.restore();

			}
			draw(planetList,dt);
			StdDraw.show(1);
		}
		for (int i = 0; i < planetList.size(); i++) {
			Planet a = planetList.get(i);
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %s\n", a.px, a.py, a.vx, a.vy, a.mass, a.image);
		}
	}
	/*Simulates the universe with Barnes-Hut algorithm.
	 * 
	 * @param T total time
	 * @param dt time interval
	 * @param planetList list of all planets
	 * @param radius radius of the universe*/
	private static void quad(double T,double dt,ArrayList<Planet> planetList,double radius){
		for (double t = 0.0; t < T; t +=dt) {
			StdDraw.clear(StdDraw.BLACK);
			
			if(planetList.get(0).image!=null)
				StdDraw.picture(0, 0, "images/starfield.jpg");
			
			Corner corner = new Corner(0.0, 0.0, radius);
			QuadTree root = new QuadTree(corner,"root");
			
			for (int i = 0; i < planetList.size(); i++) {
				if (corner.inRange(planetList.get(i))) {
					root.insert(planetList.get(i));
				}
					
			}

			//System.out.printf("%d",planetList.size());
			for (int i = 0; i < planetList.size(); i++) {
				planetList.get(i).calculateForceQuad(root);
				planetList.get(i).calculateAcceleration();
				planetList.get(i).calculateVelocity(dt);
				planetList.get(i).restore();
			}
			
			//root.traverse(root, 0);
			draw(planetList,dt);
			StdDraw.show(1);
		}
		for (int i = 0; i < planetList.size(); i++) {
			Planet a = planetList.get(i);
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %s\n", a.px, a.py, a.vx, a.vy, a.mass, a.image);
		}
	}
	/*Draws the planets in the universe.
	 * 
	 *@param planetList list of all planets
	 *@param dt time interval*/
	private static void draw(ArrayList<Planet> planetList, double dt){
		for (int i = 0; i < planetList.size(); i++) {
			
			planetList.get(i).calculatePosition(dt);
			
			if(planetList.get(i).image != null)
				StdDraw.picture(planetList.get(i).px, planetList.get(i).py, "images/" + planetList.get(i).image);
			else{
				StdDraw.setPenColor(planetList.get(i).color);
				StdDraw.point(planetList.get(i).px,planetList.get(i).py); 
			}

		}
	}
}
