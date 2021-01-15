//*************************************************
// Class: GenericSwimmer 
// Author: Ayden Craig
// Date Created: Mar 03, 2020
// Date Modified: 
//
// Purpose: Generic Swimmer to be overridden
//          
//          
// Attributes:
// int gradeLevel
// boolean male
// double percentile
// double distance[]
//
// Methods:
//
//*******************************************************
import java.util.Random;
import java.time.LocalDate;
public class GenericSwimmer {
	private boolean male;
	private double percentile;
	private double maxDistance;
	private Event[] eventInventory;
	private int period;
	private String name;
	private LocalDate startDate,endDate;
	private String strokes;
	
	//******** Methods ************************************
	public GenericSwimmer() {
		
			
	}
	public String generateWorkout(int workoutNumber) {
		int randomStrokes[] = new int[2];
		Random generator = new Random(workoutNumber);
		int warmup[] = new int[2];
		int kick[] = new int[2];
		int pull[] = new int[2];
		int mainSet1[] = new int[3];
		int mainSet2[] = new int[3];
		
		
		String warmUpStroke = "";
		String mainSetStroke[] = new String[2];
		
		
		
		

		for(int i = 0; i<2;i++){
			randomStrokes[i] =Math.abs(generator.nextInt() );
			warmup[i] =Math.abs(generator.nextInt() );
			warmup[i] %=100;
			kick[i] = Math.abs(generator.nextInt() );
			kick[i] %=100;
			pull[i] = Math.abs(generator.nextInt() );
			pull[i] %=100;
			mainSet1[i] = Math.abs(generator.nextInt() );
			mainSet1[i] %=100;
			mainSet1[i+1] = Math.abs(generator.nextInt() );
			mainSet1[i+1] %=100;
			mainSet2[i] = Math.abs(generator.nextInt() );
			mainSet2[i] %=100;
			mainSet2[i+1] = Math.abs(generator.nextInt() );
			mainSet2[i+1] %=100;
		}
		
		int warmDown = Math.abs(generator.nextInt() ) %100;
		
		
		int averageDistance=0;
		double w = workoutNumber;
		w/=getPeriod();
		
		if(w<0.26){
			averageDistance = (int) Math.round(getMaxDistance()*(0.75+w)/400);
		}
		else if(w>=0.75){
			averageDistance = (int) Math.round(getMaxDistance()*(0.75+1-w)/400);
		}
		else{
			averageDistance = (int) Math.round(getMaxDistance()/400);
		}
		
		
		
		
		for(int i = 0; i<2;i++){
			randomStrokes[i]%=3;
			switch(strokes){
				case "all":
				switch(randomStrokes[i]){
					case 0:
					mainSetStroke[i] = "fl";
					warmUpStroke = "bk";
					break;
					case 1:
					mainSetStroke[i] = "bk";
					warmUpStroke = "br";
					break;
					case 2:
					mainSetStroke[i] = "br";
					warmUpStroke = "fl";
					break;
				}
				break;
				case "flbk":
				randomStrokes[i]%=2;
				switch(randomStrokes[i]){
					case 0:
					mainSetStroke[i] = "fl";
					warmUpStroke = "bk";
					break;
					case 1:
					mainSetStroke[i] = "bk";
					warmUpStroke = "fl";
					break;
				}
				break;
				case "flbr":
				randomStrokes[i]%=2;
				switch(randomStrokes[i]){
					case 0:
					mainSetStroke[i] = "fl";
					warmUpStroke = "br";
					break;
					case 1:
					mainSetStroke[i] = "br";
					warmUpStroke = "fl";
					break;
				}
				break;
				case "bkbr":
				randomStrokes[i]%=2;
				switch(randomStrokes[i]){
					case 0:
					mainSetStroke[i] = "bk";
					warmUpStroke = "br";
					break;
					case 1:
					mainSetStroke[i] = "br";
					warmUpStroke = "bk";
					break;
				}
				break;
				default:
				mainSetStroke[i] = strokes;
				warmUpStroke = strokes;
			}
		}



		
		
		
		String part1 = "Warm Up:\n" + generateWarmup(workoutNumber, warmup,averageDistance*100,warmUpStroke);
		String part2 = "\n\nKick Set:\n" + generateKickSet(kick, mainSetStroke[0]);
		String part3 = "\n\nPull Set:\n" + generatePullSet(workoutNumber,pull,averageDistance);
		String part4 = "\n\nMain Set 1:\n" + generateMainSet(workoutNumber, mainSet1,averageDistance,mainSetStroke[0]);
		String part5 = "\n\nMain Set 2:\n" + generateMainSet(workoutNumber, mainSet2,averageDistance,mainSetStroke[1]);
		String part6 = "\n\nWarm Down:\n" + generateWarmDown(warmDown);
		String workout = part1 + "\n" + part2 + "\n" + part3 + "\n" + part4 + "\n" + part5+ "\n" + part6;
		return workout;
	}
	
	public String generateWarmup(int workoutNumber,int randomNumber[], int averageDistance, String warmUpStroke){
		String warmUp="";
		int randomDistance=0;
		
		randomNumber[0]%=100;
		if(randomNumber[0]<65){
			
			randomNumber[1]%=5;
			switch (randomNumber[1]){
				case 0:
				randomDistance = averageDistance-200;
				break;
				case 1:
				randomDistance = averageDistance-100;
				break;
				case 2:
				randomDistance = averageDistance;
				break;
				case 3:
				randomDistance = averageDistance+100;
				break;
				case 4:
				randomDistance = averageDistance+200;
				break;
			}
			if(randomNumber[0]<40){
			warmUp = randomDistance+" Freestyle";
			}
			else{
				int free = randomDistance - 200;
				warmUp = free + " Freestyle\n200 " + returnStrokeName(warmUpStroke);
			}
		}
		else if(randomNumber[0]<70){
			warmUp = "400 Freestyle\n300 Breaststroke\n200 Backstroke\n100 Butterfly";
		}
		else{
			randomNumber[1]%=2;
			randomDistance= (randomNumber[1]+2)*100;
			warmUp = randomDistance + " Kick\n" + randomDistance + " Pull\n" + randomDistance + " Swim";
		}
		
	return warmUp;	
	}
	public String generateKickSet(int randomNumber[], String stroke){
		String kick = "";
		int fifties = 0;
		randomNumber[0]%=100;
		randomNumber[1]%=5;
		if(randomNumber[0]<90){
			switch (randomNumber[1]){
				case 0:
				fifties = 8;
				break;
				case 1:
				fifties = 9;
				break;
				case 2:
				fifties = 10;
				break;
				case 3:
				fifties = 11;
				break;
				case 4:
				fifties = 12;
				break;
			}
			if(randomNumber[0]<30){
				kick = fifties + " 50s Kicking: 25 Sprint, 25 Regular";
			}
			else if(randomNumber[0]<50){
				kick = fifties + " 50s Kicking: 25 Tombstone, 25 Regular";
			}
			else if (randomNumber[0]<80){
				kick = fifties + " 50s Kicking: Odd ones " + returnStrokeName(stroke) + ", even ones Freestyle";
			}
			else{
			kick = fifties + " 25s Kicking: Odd ones Shooters, even ones Streamline";
			}
		}
		else{
			kick = "16 Minute Kick: IM order 4 minutes each stroke";
		}
		return kick;
	}
	public void calculateMaximumDistance() {
	}
	
	public String pickASetOf100s(int randomNumber, String stroke){
		randomNumber%=7;
		String returnString = "";
		switch(randomNumber){
			case 0:
			returnString = " 100s; Odd: Swim, Kick, Pull, Sprint\n       Even: Kick, Left Arm, Right Arm, Swim";
			break;
			case 1:
			returnString = " Broken 100s, sprint middle 50";
			break;
			case 2:
			returnString = " Broken 100s, sprint 3rd 25";
			break;
			case 3:
			returnString = " Broken 100s, sprint 4th 25";
			break;
			case 4:
			returnString = " 100s IM";
			break;
			case 5:
			if(strokes.equals("fr") ) {
				returnString = " 100s freestyle";
			}
			else{
				returnString = " 100s; even ones freestyle odd ones ";
				returnString = returnString + returnStrokeName(stroke);
				}
			
			break;
			case 6:
			returnString = " 100s freestyle negative split";
			break;
		}
		
		return returnString;
	}
	public String pickASetOf75s(int randomNumber, String stroke){
		String returnString = "";
		if(randomNumber<25){
			returnString = " 75s IM order without freestyle drill, your choice of drill";
		}
		else if(randomNumber<60){
			returnString = " 75s Kick pull swim";
		}
		else{
			returnString = " 75s sprint the second 25";
		}
		return returnString;
	}
	
	public String pickASetOf50s(int randomNumber[], String stroke){
		String returnString = "";
		if(randomNumber[0]<40){
			Manager mgr = new Manager();
			returnString = " 50s drill swim, using drill " +mgr.pickADrill(randomNumber[1],stroke);
		}
		else if(randomNumber[0]<50){
			if(!stroke.equals("fr")){
				returnString = " 50s 25 " + returnStrokeName(stroke) + " 25 freestyle";
			}
			else{
				returnString = " 50s freestyle";
			}
		}
		else if(randomNumber[0]<60){
			returnString = " 50s odd ones " + returnStrokeName(stroke) + " even ones freestyle";
		}
		else if(randomNumber[0]<70){
			returnString = " 50s 25 sprint 25 swim";
		}
		else if(randomNumber[0]<80){
			returnString = " 50s 25 swim 25 sprint";
		}
		else if(randomNumber[0]<90){
			returnString = " 50s odd ones sprint, even ones swim";
		}
		else{
			returnString = " 50s starting in the middle of the pool";
		}
		return returnString;
	}
	
	public String returnStrokeName(String inputStroke){
		String returnString = "";
		switch(inputStroke){
			case "fl":
			returnString = "butterfly";
			break;
			case "bk":
			returnString = "backstroke";
			break;
			case "br":
			returnString = "breaststroke";
		}
		return returnString;
	}
	public String generatePullSet(int workoutNumber, int[] randomNumber, int reps){
		return "Generic Method";
	}
	public String generateMainSet(int workoutNumber, int[] randomNumber, int reps, String stroke){
		return "Generic Method";
	}
	public String generateWarmDown(int randomNumber){
		return "Generic Method";
	}
	//******** Setters & Getters **************************
	public void setPeriod(int inputPeriod){
		period = (int) Math.floor(inputPeriod*5/7);
	}
	public int getPeriod(){
		return period;
	}
	public void setEventInventory(Event eventInput[]){
		eventInventory = eventInput;
	}
	public Event[] getEventInventory(){
		return eventInventory;
	}
	public void setMale(boolean inputMale){
		male = inputMale;
	}
	public boolean getMale(){
		return male;
	}
	public void setPercentile(double inputPercentile){
		percentile = inputPercentile;
	}
	public double getPercentile(){
		return percentile;
	}
	public void setName(String inputName){
		name = inputName;
	}
	public String getName(){
		return name;
	}
	public void setStrokes(String inputStrokes){
		strokes = inputStrokes;
		if(inputStrokes==""){
			strokes = "fr";
		}
	}
	public String getStrokes(){
		return strokes;
	}
	public void setMaxDistance(double inputDistance){
		maxDistance = inputDistance;
	}
	public double getMaxDistance(){
		return maxDistance;
	}
	public void setStartDate (LocalDate start){
		startDate = start;
	}
	public LocalDate getStartDate(){
		return startDate;
	}
	public void setEndDate(LocalDate end){
		endDate = end;
	}
	public LocalDate getEndDate(){
		return endDate;
	}
}