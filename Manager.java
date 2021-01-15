//*************************************************
// Class: Manager 
// Author: Ayden Craig
// Date Created: Mar 03, 2020
// Date Modified: 
//
// Purpose: Run all processes in swim planner
//          
//          
// Attributes:
// GenericSwimmer profile
// String swimmerType
// Event eventInventory[]
// double distance[]
// Methods:
//
//*******************************************************
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;



public class Manager {
	private GenericSwimmer profile[];
	private String swimmerType;
	private Event eventInventory[];
	public Manager() {
		profile = new GenericSwimmer[3];
	}
	//******** Methods ************************************
	public static void main(String args[] ) {
		
		Manager mgr = new Manager();
		mgr.readProfile(1);
		
   
	}
	public String determineLongMiddleShort( Event events[] ) {
		String swimmer = "";
		for(int i = 0; i < 3;i++) {
			switch(events[i].getEventName() ) {
				
				case "500fr":
					swimmer = "long";
					i=3;
					break;
				case "50fr":
					if(swimmer!="long"){
					swimmer = "short";
					}
					break;
				default:
					if(swimmer == ""&& i==2) {
					swimmer = "middle";
					}
					break;

				
			}
		}
		return swimmer;
	}
	public String determineStrokes(Event events[]) {
		String[] strokes = new String[3];
		for(int i = 0; i<3;i++) {
			strokes[i] = "";
		}
		for(int i = 0; i<3;i++) {
			String[] hold = new String[2];
			switch( events[i].getEventName() ) {
				case "200im":
				strokes[0] = "all";
				strokes[1] = "";
				strokes[2] = "";
				i=3;
				break;
				case "100fl":
				hold[0] = strokes[0];
				hold[1] = strokes[1];
				strokes[0] = "fl";
				strokes[1] = hold[0];
				strokes[2] = hold[1];
				break;
				case "100br":
				strokes[i] = "br";
				break;
				case "100bk":
				hold[0] = strokes[0];
				hold[1] = strokes[1];
				if(strokes[0]== "fl") {
					strokes[1] = "bk";
					strokes[2] = hold[1];
				}
				else{
				strokes[0] = "bk";
				strokes[1] = hold[0];
				strokes[2] = hold[1];	
				}
				
				break;
				default:
				strokes[i] = "";
			}
		}
		String swimmer = strokes[0] + strokes[1] +strokes[2];
		if(swimmer.equals("flbkbr")) {
			swimmer = "all";
		}
		if(swimmer.equals("")){
			swimmer ="fr";
		}
		return swimmer;	
		}
		
		
		
	
	
	public void readProfile(int profileNumber){
		String filePath = "C:\\Swim_Planner\\profile\\profile" + Integer.toString(profileNumber) +".txt";
		File file = new File(filePath);
		eventInventory = new Event[3];
		boolean holdGender = false;
		String holdName = "";
		double holdTime = 0;
		double holdPeriod = 0;
		String profileName = "";
		LocalDate holdStart = LocalDate.now();
		LocalDate holdEnd = LocalDate.now();
		try{

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			String line;
			while( ( line = br.readLine() ) != null   ) {
				if(line.equals("Profile")){
					profileName = br.readLine();
				}
				if(line.equals("Male")) {
					holdGender = Boolean.parseBoolean(br.readLine());
				}
				for(int i = 0; i<3;i++){
					if(line.equals("Event " + Integer.toString(i))){
						
						holdName = br.readLine();
						holdTime = Double.parseDouble(br.readLine());
						eventInventory[i] = new Event(holdName, holdTime, holdGender);
					}
				}
				if(line.equals("Period")){
					holdPeriod = Double.parseDouble(br.readLine());
				}
				if(line.equals("Start Date")){
					holdStart = LocalDate.parse(br.readLine(),formatter);
				}
				if(line.equals("End Date")){
					holdEnd = LocalDate.parse(br.readLine(),formatter);
				}
			}
			swimmerType = determineLongMiddleShort(eventInventory);
			if(swimmerType.equals("long")){
				profile[profileNumber-1] = new LongDistance();
			}
			if(swimmerType.equals("middle")){
				profile[profileNumber-1] = new MiddleDistance();
			}
			if(swimmerType.equals("short")){
				profile[profileNumber-1] = new Sprinter();
			}
			double percentile = 0;
			for(int i =0; i<3;i++){
				percentile += eventInventory[i].calculatePercentile(holdGender);
			}
			percentile/=3;
			
			profile[profileNumber-1].setMale(holdGender);
			profile[profileNumber-1].setEventInventory(eventInventory);
			profile[profileNumber-1].setName(profileName);
			profile[profileNumber-1].setStrokes( determineStrokes(eventInventory) );
			profile[profileNumber-1].setPeriod( (int) holdPeriod);
			profile[profileNumber-1].setPercentile(percentile);
			profile[profileNumber-1].setStartDate(holdStart);
			profile[profileNumber-1].setEndDate(holdEnd);
			profile[profileNumber-1].calculateMaximumDistance();
			
			
			br.close();
			
			
			
			
		}catch(Exception e){
			System.out.print("readProfile didnt work");
		}
	}
	
	public void saveNewProfile(String name, boolean male, String[] eventNames, double[] eventTimes, long period, int profileNumber, LocalDate startDate, LocalDate endDate){
		String filePath = "C:\\Swim_Planner\\profile\\profile" + Integer.toString(profileNumber) +".txt";
		File file = new File(filePath);
		//System.out.println("worked");
					
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Profile");
			bw.newLine();
			bw.write(name);
			bw.newLine();
			bw.write("Male");
			bw.newLine();
			bw.write(Boolean.toString(male));
			
			for(int j = 0; j <3; j++) {
				bw.newLine();
				bw.write("Event " + Integer.toString(j) );
				bw.newLine();
				bw.write(eventNames[j] );
				bw.newLine();
				bw.write(Double.toString(eventTimes[j]));
			}
			bw.newLine();
			bw.write("Period");
			bw.newLine();
			bw.write(Long.toString(period));
			bw.newLine();
			bw.write("Start Date");
			bw.newLine();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			String date =(startDate).format(formatter);
			bw.write(date);
			bw.newLine();
			bw.write("End Date");
			bw.newLine();
			date =(endDate).format(formatter);
			bw.write(date);
			
			bw.close();
		}
		catch (Exception e) {

			e.printStackTrace();
		}
	
	}
	public String pickADrill(int randomNumber, String stroke){
		String returnString = "";
		String filePath = "C:\\Swim_Planner\\drills\\" + stroke +".txt";
		File file = new File(filePath);
		if(stroke.equals("fr") || stroke.equals("bk") ) {
			randomNumber %= 9;
		}	
		else{
			randomNumber %= 8;
		}
		try{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int i = 0;
			while( ( line = br.readLine() ) != null   ) {
				if(i == randomNumber){
					returnString = br.readLine();
				}
				i+=1;				
			}
			br.close();
		}catch(Exception e){
			System.out.print("readProfile didnt work");
		}
		
		return returnString;
	}
	//******** Setters & Getters **************************
	public void setSwimmerType(String type) {
		System.out.println("You ran the setSwimmerType method of class Manager... it does nothing");
	}
	public String getSwimmerType() {
		return swimmerType;
	}
	public GenericSwimmer getProfile(int profileNumber) {
		return profile[profileNumber-1];
	}
}