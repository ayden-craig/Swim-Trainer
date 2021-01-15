import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar.Builder;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.Month;


public class WorkoutPane extends StackPane{
	private GenericSwimmer profile;
	private LocalDate startDate, endDate, holdDate, firstOfMonth[],currentDate, lastDate;
	private int pages, lastUsedDay;
	private GridPane calendar[], workoutGrid;
	private Text day[], previousPage[], nextPage[], monthText[],
	m[], t[], w[], th[], f[],date,
	previousWorkout, nextWorkout, selectDate; 
	private int currentPage, currentWorkout,currentDay;
	private Button returnToCalendar;
	
	
	public WorkoutPane(GenericSwimmer input){
		profile = input;
		startDate  = profile.getStartDate();
		endDate = profile.getEndDate();
		pages = (int) ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1))+1;
		calendar = new GridPane[pages];
		workoutGrid = new GridPane();
		previousPage = new Text[pages];
		nextPage = new Text[pages];
		m = new Text[pages]; t= new Text[pages];w= new Text[pages];th = new Text[pages]; f = new Text[pages];
		
		if(endDate.getDayOfWeek() == DayOfWeek.SATURDAY ) {
			lastDate = endDate.minusDays(1);
		}
		
		else if(endDate.getDayOfWeek() == DayOfWeek.SUNDAY ) {
			lastDate = endDate.minusDays(2);
		}
		else{
			lastDate = endDate;
		}
		
		
		firstOfMonth = new LocalDate[pages+1];
		monthText = new Text[pages];
		currentPage = 0;
		currentDay = 0;
		
		
		firstOfMonth[0] = startDate.withDayOfMonth(1);
		
		workoutGrid = new GridPane();
		workoutGrid.setAlignment(Pos.TOP_CENTER);
		setMargin(workoutGrid,new Insets(20,8,8,8));
		workoutGrid.setHgap(20);
		workoutGrid.setVgap(20);
		getChildren().add(workoutGrid);
		workoutGrid.setVisible(false);
		
		selectDate = new Text("Please Select a date to view a workout:");
		selectDate.setFont(Font.font("arial", FontWeight.BOLD, 20));
		selectDate.setFill(Color.AQUAMARINE);
		setAlignment(selectDate, Pos.TOP_CENTER);
		getChildren().add(selectDate);
		selectDate.setVisible(false);
		
		
		previousWorkout = new Text("<");
		previousWorkout.setFont(Font.font("arial", FontWeight.BOLD, 40));
		previousWorkout.setFill(Color.PALETURQUOISE);
		workoutGrid.add(previousWorkout, 0, 0);
		
		nextWorkout = new Text(">");
		nextWorkout.setFont(Font.font("arial", FontWeight.BOLD, 40));
		nextWorkout.setFill(Color.PALETURQUOISE);
		workoutGrid.add(nextWorkout, 2, 0);
		
		for(int i = 1; i<pages+1;i++){
			firstOfMonth[i] = firstOfMonth[i-1].plusDays(35);
			firstOfMonth[i] = firstOfMonth[i].withDayOfMonth(1);
			
		}
		
		
		day = new Text[30*pages];
		int i = 0;
		holdDate = startDate;
		while(!holdDate.isEqual(endDate.plusDays(1)) ){
			if( (holdDate.getDayOfWeek()!=DayOfWeek.SATURDAY) && (holdDate.getDayOfWeek() != DayOfWeek.SUNDAY) ){ 
			day[i] = new Text(Integer.toString(holdDate.getDayOfMonth() ) );
			day[i].setFont(Font.font("arial", FontWeight.BOLD, 20));
			day[i].setFill(Color.AQUAMARINE);
			i+=1;
			}
			holdDate =holdDate.plusDays(1);
		}
		date = new Text();
		date.setFont(Font.font("arial", FontWeight.BOLD, 20));
		date.setFill(Color.PALETURQUOISE);
		setAlignment(date,Pos.TOP_CENTER);
		workoutGrid.add(date,1,0);
		
		Text text = new Text();
		text.setFont(Font.font("arial", FontWeight.BOLD, 10));
		text.setFill(Color.AQUAMARINE);
		text.setWrappingWidth(200);
		setAlignment(text,Pos.TOP_CENTER);
		setMargin(text, new Insets(70,8,8,8));
		getChildren().add(text);
		int j = 1;
		holdDate = startDate;
			switch(startDate.getDayOfWeek() ){
				case TUESDAY:
					j = 2;
					break;
				case WEDNESDAY:
					j = 3;
					break;
				case THURSDAY:
					j = 4;
					break;
				case FRIDAY:
					j = 5;
					break;
				case SATURDAY:
					holdDate = holdDate.plusDays(2);
					break;
				case SUNDAY:
					holdDate = holdDate.plusDays(1);
					break;
			}
			
		
		lastUsedDay = 0;
		for(int k = 0; k<pages;k++){
			calendar[k] = new GridPane();
			calendar[k].setAlignment(Pos.TOP_CENTER);
			setMargin(calendar[k],new Insets(40,8,8,8));
			calendar[k].setHgap(20);
			calendar[k].setVgap(20);
			getChildren().add(calendar[k]);
			
			daysOfTheWeekText(calendar, k);
			
			
			monthText[k] = new Text( convertMonthToString( firstOfMonth[k].getMonth() ) );
			monthText[k].setFont(Font.font("arial", FontWeight.BOLD, 20));
			monthText[k].setFill(Color.PALETURQUOISE);
			setAlignment(monthText[k], Pos.TOP_CENTER);
			setMargin(monthText[k], new Insets(20,8,8,8));
			getChildren().add(monthText[k]);
			monthText[k].setVisible(false);
			
			nextPage[k] = new Text(">");
			nextPage[k].setFont(Font.font("arial", FontWeight.BOLD, 40));
			nextPage[k].setFill(Color.PALETURQUOISE);
			setAlignment(nextPage[k],Pos.TOP_CENTER);
			if(k<pages-1){
				calendar[k].add(nextPage[k],6,0);
			}
			
			
			previousPage[k] = new Text("<");
			previousPage[k].setFont(Font.font("arial", FontWeight.BOLD, 40));
			previousPage[k].setFill(Color.PALETURQUOISE);
			setAlignment(previousPage[k],Pos.TOP_CENTER);
			if(k>0){
				calendar[k].add(previousPage[k],0,0);
			}
			initializePreviousButton(calendar, previousPage[k]);
			initializeNextButton(calendar,nextPage[k]);
			
			
			calendar[k].setVisible(false);
			int m = 1;
			while(holdDate.isBefore(firstOfMonth[k+1])&&lastUsedDay<i){
				calendar[k].add(day[lastUsedDay],j,m);
				initializeWorkoutButtons(day,lastUsedDay,profile,text,date,holdDate, workoutGrid);
				lastUsedDay+=1;
				holdDate = holdDate.plusDays(1);
				
				
				j+=1;
				j%=6;
				if(j==0){
					holdDate = holdDate.plusDays(2);
					m+=1;
					j=1;
				}
				
				
			}
		}
		
		
		returnToCalendar = new Button("Return to Calendar");
		returnToCalendar.setFont(Font.font("arial", FontWeight.BOLD, 40));
		returnToCalendar.setStyle("-fx-background-color: #AFEEEE ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(returnToCalendar,Pos.TOP_CENTER);
		setMargin(returnToCalendar, new Insets(450,8,8,8) );
		getChildren().add(returnToCalendar);
		returnToCalendar.setVisible(false);
		
	
		
		previousWorkout.setOnMousePressed(e -> {
			if(currentDate.getMonth() != currentDate.minusDays(1).getMonth() ) {
				currentPage-=1;
			}
			currentWorkout-= 1;
			
			if(currentDate.getDayOfWeek() == DayOfWeek.MONDAY){
				currentDate = currentDate.minusDays(3);
				
			}
			else{
				currentDate = currentDate.minusDays(1);
			}
			date.setText("Workout on " + currentDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			text.setText(profile.generateWorkout(currentWorkout));
			if(currentWorkout == 0){
				previousWorkout.setVisible(false);
			}
			nextWorkout.setVisible(true);
		});
		
		nextWorkout.setOnMousePressed(e -> {
			if(currentDate.getMonth() != currentDate.plusDays(1).getMonth() ) {
				currentPage+=1;
			}
			currentWorkout+= 1;
			if(currentDate.getDayOfWeek() == DayOfWeek.FRIDAY){
				currentDate = currentDate.plusDays(3);
				
			}
			else{
				currentDate = currentDate.plusDays(1);
			}
			date.setText("Workout on " + currentDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			text.setText(profile.generateWorkout(currentWorkout));
			if(currentWorkout >= lastUsedDay-1){
				nextWorkout.setVisible(false);
			}
			
			
			previousWorkout.setVisible(true);
		});
		
		
		
		
		
		
		
		returnToCalendar.setOnMousePressed(e -> {
			text.setVisible(false);
			workoutGrid.setVisible(false);
			calendar[currentPage].setVisible(true);
			monthText[currentPage].setVisible(true);
			returnToCalendar.setVisible(false);
			selectDate.setVisible(true);
			
		});
		
		
		if(LocalDate.now().isBefore(endDate) && LocalDate.now().isAfter(startDate.minusDays(1)) ){
			holdDate = startDate.minusDays(1);
			int todaysWorkout=0;
			while(holdDate.isBefore(LocalDate.now())){
				if(holdDate.getDayOfWeek() != DayOfWeek.SATURDAY &&holdDate.getDayOfWeek() != DayOfWeek.SUNDAY ){
					holdDate = holdDate.plusDays(1);
					todaysWorkout+=1;
				}
				else{
					holdDate = holdDate.plusDays(2);
				}
			}
			if(LocalDate.now().getDayOfWeek() != DayOfWeek.SATURDAY && LocalDate.now().getDayOfWeek() != DayOfWeek.SUNDAY){
				date.setText("Workout on " + LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
				currentDate = LocalDate.now();
			}
			else if(LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY){
				date.setText("Workout on " + LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
				currentDate = LocalDate.now().plusDays(2);
			}
			else{
				date.setText("Workout on " + LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
				currentDate = LocalDate.now().plusDays(1);
			}
			text.setText(profile.generateWorkout(todaysWorkout) );
			currentWorkout = todaysWorkout;
			text.setVisible(true);
			workoutGrid.setVisible(true);
			returnToCalendar.setVisible(true);
			currentPage = (int) ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), LocalDate.now().withDayOfMonth(1));
		}
		
		else{
			calendar[currentPage].setVisible(true);
			monthText[currentPage].setVisible(true);
			selectDate.setVisible(true);
		}
		
		
		
		
		
		
		
		
		
	}
	private String convertMonthToString(Month month){
		String returnText = "month";
		switch(month){
			case JANUARY:
				returnText = "January";
				break;
			case FEBRUARY:
				returnText = "February";
				break;
			case MARCH:
				returnText = "March";
				break;
			case APRIL:
				returnText = "April";
				break;
			case MAY:
				returnText = "May";
				break;
			case JUNE:
				returnText = "June";
				break;
			case JULY:
				returnText = "July";
				break;
			case AUGUST:
				returnText = "August";
				break;
			case SEPTEMBER:
				returnText = "September";
				break;
			case OCTOBER:
				returnText = "October";
				break;
			case NOVEMBER:
				returnText = "November";
				break;
			case DECEMBER:
				returnText = "December";
				break;
		}
		return returnText;
	}
	public void daysOfTheWeekText(GridPane[] calendar, int k){
		m[k] = new Text("M");
		m[k].setFont(Font.font("arial", FontWeight.BOLD, 20));
		m[k].setFill(Color.PALETURQUOISE);
		setAlignment(m[k],Pos.TOP_CENTER);
		calendar[k].add(m[k],1,0);
		
		t[k] = new Text("T");
		t[k].setFont(Font.font("arial", FontWeight.BOLD, 20));
		t[k].setFill(Color.PALETURQUOISE);
		setAlignment(t[k],Pos.TOP_CENTER);
		calendar[k].add(t[k],2,0);
		
		w[k] = new Text("W");
		w[k].setFont(Font.font("arial", FontWeight.BOLD, 20));
		w[k].setFill(Color.PALETURQUOISE);
		setAlignment(w[k],Pos.TOP_CENTER);
		calendar[k].add(w[k],3,0);
		
		th[k] = new Text("TH");
		th[k].setFont(Font.font("arial", FontWeight.BOLD, 20));
		th[k].setFill(Color.PALETURQUOISE);
		setAlignment(th[k],Pos.TOP_CENTER);
		calendar[k].add(th[k],4,0);
		
		f[k] = new Text("F");
		f[k].setFont(Font.font("arial", FontWeight.BOLD, 20));
		f[k].setFill(Color.PALETURQUOISE);
		setAlignment(f[k],Pos.TOP_CENTER);
		calendar[k].add(f[k],5,0);
	}
	
	public void initializeNextButton(GridPane[] calendar, Text previousPage){
		previousPage.setOnMousePressed( e -> {
		calendar[currentPage].setVisible(false);
		monthText[currentPage].setVisible(false);
		currentPage +=1;
		calendar[currentPage].setVisible(true);
		monthText[currentPage].setVisible(true);
		});
	}
	public void initializePreviousButton(GridPane[] calendar, Text previousPage){
		previousPage.setOnMousePressed( e -> {
		calendar[currentPage].setVisible(false);
		monthText[currentPage].setVisible(false);
		currentPage -=1;
		calendar[currentPage].setVisible(true);
		monthText[currentPage].setVisible(true);
		});
	}
	
	
	public void initializeWorkoutButtons(Text day[], int dayNumber, GenericSwimmer profile,Text text, Text date, LocalDate holdDate, GridPane workoutGrid){
		day[dayNumber].setOnMousePressed(e -> {
			calendar[currentPage].setVisible(false);
			monthText[currentPage].setVisible(false);
			text.setText(profile.generateWorkout(dayNumber) );
			text.setVisible(true);
			currentDate = holdDate;
			currentWorkout = dayNumber;
			date.setText("Workout on " + holdDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
			workoutGrid.setVisible(true);
			returnToCalendar.setVisible(true);	
			selectDate.setVisible(false);
			if(dayNumber == 0){
				previousWorkout.setVisible(false);
			}
			else{
				previousWorkout.setVisible(true);
			}
			if(holdDate.plusDays(2).isAfter(lastDate) ) {
				nextWorkout.setVisible(false);
			}
			else{
				nextWorkout.setVisible(true);
			}
		});
	}
}