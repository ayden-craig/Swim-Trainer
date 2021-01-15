//*************************************************
// Class: Sprinter 
// Author: Ayden Craig
// Date Created: Mar 03, 2020
// Date Modified: 
//
// Purpose: Sprinter Swimmer          
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






public class Sprinter extends GenericSwimmer {

	
	//******** Methods ************************************
	
	public Sprinter() {
	}
	@Override
	public void calculateMaximumDistance() {
		double weight = 0.405465108;
		
		setMaxDistance(Math.round(45*Math.exp( -weight*getPercentile() ) )*100);
		
	}
	public String generatePullSet(int workoutNumber,int randomNumber[], int reps){
		String pull = "";
		randomNumber[1]%=3;
		randomNumber[1]%=5;
		switch (randomNumber[1]){
				case 0:
				reps +=1;
				break;
				case 1:
				reps -= 1;
				break;
				case 2:
				reps -= 2;
				break;
				default:
				break;
		}
		if(randomNumber[0]<10){
			pull = reps + " 100s Pulling Freestyle: Breathing pattern 3 strokes, 5 strokes, 7 strokes, 9 strokes by 25";
		}
		else if (randomNumber[0] < 30){
			pull = reps + " 100s Pulling Freestyle: Negative Split";
		}
		else if(randomNumber[0] < 40){
			pull = reps + " 100s Pulling Freestyle";
		}
		else if(randomNumber[0] < 50){
			pull = reps + " 100s Pulling freestyle, sprint 3rd 25";
		}
		else if(randomNumber[0] < 60){
			pull = reps + " 100s pulling freestyle, sprint middle 50";
		}
		else if(randomNumber[0] < 80){
			pull = reps + " 75s pulling freestyle, sprint middle 25";
		}
		else{
			pull = "9 100s Pulling Freestyle: 3 sets of 3 100s descending";
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
		else if(randomNumber[1]<50){
			mainSet = reps + pickASetOf50s(randomNumber, stroke);
		}
		else if(randomNumber[1]<60){
			mainSet = reps + pickASetOf75s(randomNumber[2], stroke);
		}
		else if(randomNumber[1]<90){
			mainSet = reps + pickASetOf100s(randomNumber[2],stroke);
		}
		else{
			mainSet = Integer.toString(reps*2) + " 25s odd ones sprint first half, even ones sprint second half";
		}
		return mainSet;
	}
	@Override
	public String generateWarmDown(int randomNumber){
		String returnString = "";
		if(randomNumber<30){
			returnString = "200 easy warmdown choice of stroke";
		}
		else if(randomNumber<60){
			returnString = "16 25s in sets of four:\n1. Sprint the first half\n2. Sprint the second half\n3. Distance per stroke\n4. Underwater";
		}
		else{
			returnString = "8 25s distance per stroke";
		}
		return returnString;
	}
	

	
}