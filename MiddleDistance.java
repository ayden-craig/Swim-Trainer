//*************************************************
// Class: MiddleDistance 
// Author: Ayden Craig
// Date Created: Mar 03, 2020
// Date Modified: 
//
// Purpose: Middle distance swimmer
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



public class MiddleDistance extends GenericSwimmer {
	
	
	//******** Methods ************************************
	
	public MiddleDistance() {
	}
	@Override
	public void calculateMaximumDistance() {
		double weight = 0.356674944;
		setMaxDistance(Math.round(50*Math.exp( -weight*getPercentile() ) )*100);
		
	}
	
	@Override
	public String generatePullSet(int workoutNumber,int randomNumber[],int reps){
		String pull = "";
		
		if(randomNumber[0]<30){
			pull = reps + " 100s Pulling Freestyle: Breathing pattern 3 strokes, 5 strokes, 7 strokes, 9 strokes by 25";
		}
		else if (randomNumber[0] < 50){
			pull = reps + " 100s Pulling Freestyle: Negative Split";
		}
		else if(randomNumber[0] < 70){
			pull = reps + " 100s Pulling Freestyle";
		}
		else if(randomNumber[0] < 80){
			pull = " 5 200s Negative Split";
		}
		else{
			pull = " 9 100s Pulling Freestyle: 3 sets of 3 100s descending";
		}
		return pull;
	}
	
	@Override
	public String generateMainSet(int workoutNumber, int[] randomNumber, int reps, String stroke){
		
		String mainSet = "";
		randomNumber[0]%=5;
		switch (randomNumber[0]){
				case 0:
				reps +=2;
				break;
				case 1:
				reps +=1;
				break;
				case 2:
				reps -= 1;
				break;
				case 3:
				reps -= 2;
				break;
				default:
				break;
			}
		if(randomNumber[1]<10){
			mainSet = "16 100s circuits";
		}
		else if(randomNumber[1]<20){
			mainSet = reps + pickASetOf50s(randomNumber, stroke);
		}
		else if(randomNumber[1]<30){
			mainSet = reps + pickASetOf75s(randomNumber[2], stroke);
		}
		else if(randomNumber[1]<80){
			mainSet = reps + pickASetOf100s(randomNumber[2],stroke);
		}
		else{
			mainSet = Integer.toString(reps/2) + " 200s negative split";
		}
		return mainSet;
	}
	@Override
	public String generateWarmDown(int randomNumber){
		String returnString = "";
		if(randomNumber<40){
			returnString = "200 easy warmdown choice of stroke";
		}
		else if(randomNumber<90){
			returnString = "16 25s in sets of four:\n1. Sprint the first half\n2. Sprint the second half\n3. Distance per stroke\n4. Underwater";
		}
		else{
			returnString = "8 25s distance per stroke";
		}
		return returnString;
	}

	
}