//*************************************************
// Class: LongDistance 
// Author: Ayden Craig
// Date Created: Mar 03, 2020
// Date Modified: Apr 16,2020
//
// Purpose: Long Distance swimmer
//          
//          
// Attributes:
// boolean male
// double percentile
// double distance[]
//
// Methods:
//
//*******************************************************




public class LongDistance extends GenericSwimmer {
	
	
	//******** Methods ************************************
	
	public LongDistance() {
			super();
			
	}
	
	
	
	@Override
	public void calculateMaximumDistance() {
		double weight = 0.405465108;
		setMaxDistance(Math.round(60*Math.exp( -weight*getPercentile() ) )*100);
		
	}
	@Override
	public String generatePullSet(int workoutNumber, int[] randomNumber, int reps){
		String pull = "";
		double w = workoutNumber;
		w/=getPeriod();
		randomNumber[1]%=5;
		if(w<0.25||w>0.75){
			switch (randomNumber[1]){
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
			if(randomNumber[0]<40){
				pull = reps + " 100s Pulling Freestyle: \nBreathing pattern 3 strokes, 5 strokes, 7 strokes, 9 strokes by 25";
			}
			else if (randomNumber[0] < 60){
				pull = reps + " 100s Pulling Freestyle: Negative Split";
			}
			else{
				pull = reps + " 100s Pulling Freestyle";
			}
		}
		else{
			switch (randomNumber[1]){
				case 0:
				reps = reps/3+2;
				break;
				case 1:
				reps = reps/3+1;
				break;
				case 2:
				reps /= 3;
				break;
				case 3:
				reps = reps/3 -1;
				break;				
				case 4:
				reps = reps/3 -2;
				break;
			}
			if(randomNumber[0]<30){
				pull = reps + " 200s Freestyle";
			}
			if(randomNumber[0]<60){
				pull = reps + " 200s Descending";
			}
			else{
				pull = reps + " 200s Negative Split";
			}
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
		if(randomNumber[1]<5){
			mainSet = "16 100s circuits";
		}
		else if(randomNumber[1]<10){
			mainSet = reps + pickASetOf50s(randomNumber, stroke);
		}
		else if(randomNumber[1]<20){
			mainSet = reps + pickASetOf75s(randomNumber[2], stroke);
		}
		else if(randomNumber[1]<60){
			mainSet = reps + pickASetOf100s(randomNumber[2],stroke);
		}
		else if(randomNumber[1]<70){
			mainSet = Integer.toString(100*reps) + " Freestyle distance per stroke";
		}
		else{
			mainSet = Integer.toString(reps/2) + " 200s Freestyle distance per stroke";
		}
		return mainSet;
	}
	@Override
	public String generateWarmDown(int randomNumber){
		String returnString = "";
		if(randomNumber<60){
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