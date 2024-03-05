package Food_Delivery_Simulator;

import java.util.concurrent.Semaphore;

public class Driver extends Thread{
	//constructor class
	public Driver(int s, Restaurant r, Classes.Schedule cs, double la, double lo, long c) {
		driverSem = new Semaphore(s);
		restaurant = r;
		latitude = la;
		longitude = lo;
		
		cal = c;
		sched = cs;
	}
	
	//private class variables
	private Semaphore driverSem;
	private Restaurant restaurant;
	private double latitude, longitude;
	
	private long cal;
	private Classes.Schedule sched;
	
	//run thread
	public void run() {
		try {
			//waits till a driver is available
			driverSem.acquire();
			
			//makes sure order is ready before continuing
			if(secondsPassed() < sched.getOrder()) {
				sleep((long) ((sched.getOrder() - secondsPassed()) * 1000));
			}
			
			//starts delivery
			System.out.println(printTime() + " Starting delivery of " + sched.getFood() + " from " 
					+ sched.getRName() + "!");
			
			//drive to customer and back
			sleep((long) (calculateDist() * 1000) * 2);
			
			//finishes delivery
			System.out.println(printTime() + " Finished delivery of " + sched.getFood() + " from "
					+ sched.getRName() + "!");
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		} finally {
			driverSem.release();
		}
	}
	
	//calculates distance between customer and restaurant using longitude and latitude
	public double calculateDist() {
		double x = Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(restaurant.getLat()));
		double y = Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(restaurant.getLat())) * 
				Math.cos(Math.toRadians(restaurant.getLong() - longitude));
		
		return (double) Math.round(3963.0 * Math.acos(x + y) * 10.0) / 10.0;
	}
	
	//how many seconds have passed since the start of all deliveries
	public double secondsPassed() {
		return (double) (System.currentTimeMillis() - cal) / 1000.0;
	}
	
	//returns time that has passed since start in [00:00:00.000] format
	public String printTime() {
		long temp = System.currentTimeMillis() - cal;
		
		String time = "[";
		time += String.format("%02d", (int) (temp / 1000 / 60 / 60)) + ":";
		time += String.format("%02d", (int) (temp / 1000 / 60)) + ":";
		time += String.format("%02d", (int) (temp / 1000)) + ".";
		time += String.format("%03d", (int) (temp % 1000)) + "]";
		
		return time;
	}
}
