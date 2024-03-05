package Food_Delivery_Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;


public class assignment2 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		File file;
		Classes.Data data;
		String rName;
		String sName;
		
		ArrayList<Classes.Schedule> sched = new ArrayList<Classes.Schedule>();
		
		//prompts user for restaurant file, makes sure it exists
		while(true) {
			System.out.println("What is the name of the file containing the restaurant information? ");
			rName = in.nextLine();
			
			try {
				file = new File(rName);
				Scanner sc = new Scanner(file);
				String temp = "";
				while(sc.hasNextLine()) {
					temp += sc.nextLine();
				}
				sc.close();
				
				//adds it to gson
				Gson gson = new Gson();
				data = gson.fromJson(temp,  Classes.Data.class);
				
				//checks to make sure file is formatted correctly / not missing any data
				if(data.getPlaces() == null) {throw new Exceptions.incorrectFormatException("bad format");}
				for(int i = 0; i < data.getPlaces().size(); i++) {
					Restaurant curr = data.getPlaces().get(i);
					
					if(curr.getRName() == null || curr.getAddress() == null || curr.getMenu() == null) {
						throw new Exceptions.missingDataException("missing data");
					}
				    if(curr.getDrivers() == 0 || curr.getLat() == 0 || curr.getLong() == 0) {
				    	throw new Exceptions.missingDataException("missing data");
				    }
				  	
				}
				
				break;
			} catch (FileNotFoundException fnfe) {
				System.out.println("The file " + rName + " could not be found.\n");
			} catch (Exceptions.incorrectFormatException ife) {
				System.out.println("The file " + rName + " is not formatted correctly.\n");
			} catch (Exceptions.missingDataException mde) {
				System.out.println("The file " + rName + " is missing information.\n");
			}
		}
		
		//prompts user for schedule file, makes sure it exists and adds to restaurant's sched
		System.out.println();
		while(true) {
			System.out.println("What is the name of the file containing the schedule information?");
			sName = in.nextLine();
			
			try {
				file = new File(sName);
				Scanner sc = new Scanner(file);
				
				while(sc.hasNextLine()) {
					String[] temp = sc.nextLine().split(", ");
					sched.add(new Classes.Schedule(Integer.parseInt(temp[0]), temp[1].trim(), temp[2].trim()));
					
				}
				sc.close();
				
				break;
			} catch (FileNotFoundException fnfe) {
				System.out.println("The file " + sName + " could not be dount.\n");
			}
		}
		
		System.out.println();
		//gets user longitude and latitude
		double uLat, uLong;
		while(true){
			System.out.print("What is the latitude? ");
			try {
				uLat = Double.parseDouble(in.nextLine());
				
				if(uLat < -90 || uLat > 90) {throw new Exceptions.outOfBoundsException("bad latitude");}
				
				break;
			} catch(NumberFormatException nfe) {
				System.out.println("Please input a valid latitude.\n");
			} catch(Exceptions.outOfBoundsException oobe) {
				System.out.println("Please input a valid latitude.\n");
			}
		}
		System.out.println();
		while(true) {
			System.out.print("What is the longitute? ");
			try {
				uLong = Double.parseDouble(in.nextLine());
				
				if(uLong < -180 || uLong > 180) {throw new Exceptions.outOfBoundsException("bad longitude");}
				break;
			} catch(NumberFormatException nfe) {
				System.out.println("Please input a valid longitude.\n");
			} catch(Exceptions.outOfBoundsException oobe) {
				System.out.println("Please input a valid longitude.\n");
			}
		}
		
		
		// starting to send out drivers
		System.out.println("\nStarting execution of program...");
		Driver[] drivers = new Driver[sched.size()];
		for(int i = 0; i < sched.size(); i++) {
			Restaurant tempR = data.findRestaurant(sched.get(i).getRName());
			Driver curr = new Driver(tempR.getDrivers(), tempR, sched.get(i), uLat, uLong, System.currentTimeMillis());
			drivers[i] = curr;
			
			curr.start();
		}
		
		// joins all threads, makes sure all are done before continuing
		try {
			for(Driver driver : drivers) {driver.join();}
		} catch(InterruptedException ie) {
			ie.printStackTrace();
		}
		
		System.out.println("All orders complete");
		
		
		in.close();
	}
	
	
}
