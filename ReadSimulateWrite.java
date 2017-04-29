
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.util.Scanner;

/**
 *
 * @author levent
 */
public class ReadSimulateWrite {

	public static void main(String[] args) {

		double dt = Double.parseDouble(args[1]); // time step: you can play this value to determine simulation speed
		double T = Double.parseDouble(args[0]); // end of the universe
		int N = StdIn.readInt(); // number of bodies
		double radius = StdIn.readDouble(); // radius of universe
		double px = 0;
		double py = 0;
		double vx = 0;
		double vy = 0;
		double mass = 0;
		String image = "";
		int red;
		int green;
		int blue;

		StdDraw.show(0);
		StdDraw.setXscale(-radius, +radius);
		StdDraw.setYscale(-radius, +radius);
		Color color = null;

		// read and write
		StdOut.printf("%d\n", N);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < N; i++) {
			px = StdIn.readDouble();
			py = StdIn.readDouble();
			vx = StdIn.readDouble();
			vy = StdIn.readDouble();
			mass = StdIn.readDouble();

			//image = StdIn.readString();
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %s\n", px, py, vx, vy, mass, image);
			
			  red = StdIn.readInt(); green = StdIn.readInt(); blue =
			  StdIn.readInt(); color = new Color(red, green, blue);
			  StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %d %d %d\n",
			  px, py, vx, vy, mass, red, green, blue);
			 
		}

		
		  // simulate one body with constant velocity 
		for (double t = 0.0; t<T; t = t + dt) {
		 
		  StdDraw.clear(StdDraw.BLACK); px = px + dt*vx; py = py + dt*vy;
		  StdDraw.setPenColor(color); StdDraw.point(px, py); StdDraw.show(10);
		  //you can play this value to determine simulation speed
		  
		  }
		 

		// simulate one body with constant velocity
		
		/*for (double t = 0.0; t < T; t = t + dt) {

			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");	
			px = px + dt * vx;
			py = py + dt * vy;
			StdDraw.picture(px, py, "images/" + image);
			StdDraw.show(10); // you can play this value to determine simulation speed

			
		}*/
	}
}
