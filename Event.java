//*************************************************
// Class: Event 
// Author: Ayden Craig
// Date Created: Mar 06, 2020
// Date Modified: Mar 27, 2020
//
// Purpose: Handle the swimmer's event and times, calculate event percentile
//          
//          
// Attributes:
// String eventName
// eventName is to be the location of the file containing the data we need
// double eventTime
//
// Methods:
// calculatePercentile: takes the file containing data and the event time and outputs the percentile of the swimmer compared to the
// other swimmers of the same gender in the same event
//*******************************************************
import java.util.Scanner;




 
public class Event {
	private String eventName;
	private double eventTime;
	
	//******** Methods ************************************
	public Event(String name, double time, boolean inputGender) {
		eventName = name;
		eventTime = time;
	}
	public Event() {
	}
	public double calculatePercentile(boolean male) {
		String gender;
		if(male) {
			gender = "boys\\";
		}
		else {
			gender = "girls\\";
		}
		String filePath = "C:\\Swim_Planner\\"+gender+eventName+".txt";
		java.io.File file = new java.io.File(filePath);
		double place= 0;
		double total = 0;
		double timePlaceHolder = eventTime;
		try {
		Scanner input = new Scanner(file);
		while(input.hasNext()) {
			double hold = input.nextDouble();
			
			if(timePlaceHolder>hold) {
				place = total+1;
				
			}
			
			total+=1;
		}
		input.close();
		}
		catch (java.io.FileNotFoundException ex){
			System.out.println("File not found");
			place = -1;
		}
		
		
		
		return place/total;
	}
	
	
	
	//******** Setters & Getters **************************
	public void setEventName(String name) {
		eventName = name;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventTime(double time) {
		eventTime = time;
	}
	public double getEventTime() {
		return eventTime;
	}
	
	
}