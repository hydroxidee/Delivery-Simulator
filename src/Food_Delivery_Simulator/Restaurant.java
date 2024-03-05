package Food_Delivery_Simulator;

import java.util.ArrayList;

public class Restaurant {
	//restaurant constructor
	public Restaurant(String n, String a, double lat, double longi, int d, ArrayList<String> m) {
		name = n;
		address = a;
		latitude = lat;
		longitude = longi;
		drivers = d;
		menu = m;
	}
	
	//private variables
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private int drivers;
	private ArrayList<String> menu;
	
	//getters
	public String getRName() {return name;}
	public String getAddress() {return address;}
	public double getLat() {return latitude;}
	public double getLong() {return longitude;}
	public int getDrivers() {return drivers;}
	public ArrayList<String> getMenu() {return menu;}
	
}
