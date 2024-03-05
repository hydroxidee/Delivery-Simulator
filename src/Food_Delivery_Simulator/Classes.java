package Food_Delivery_Simulator;

import java.util.ArrayList;

public class Classes {
	//stores information about all restaurants
	static class Data {
		ArrayList<Restaurant> data;
		
		public ArrayList<Restaurant> getPlaces() {return data;}
		public Restaurant findRestaurant(String name) {
			for(int i = 0; i < data.size(); i++) {
				if(data.get(i).getRName().equals(name)) {return data.get(i);}
			}
			
			return null;
		}
	}
	
	//stores all schedule information for one order
	static class Schedule {
		public Schedule(int o, String n, String f) {
			order = o;
			restaurant = n;
			food = f;
		}
		
		private int order;
		private String restaurant;
		private String food;
		
		public int getOrder() {return order;}
		public String getRName() {return restaurant;}
		public String getFood() {return food;}
	}
}
