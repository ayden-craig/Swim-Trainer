import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



public class NewProfilePane extends StackPane {
	
	private Text
	//Declare all text needed below:
	title, namePrompt, chooseGenderText, noNameText,
	invalidEntryText, noGenderText,
	selectEventText, notThreeEvents,
	enterTimesPrompt, enterTimesText[], minutesText[], secondsText[],
	noTimeText, invalidTimeText,
	startDateText, endDateText, noDateText, periodPrompt;
	private HBox
	genderBox, eventBox1, eventBox2, eventBox3;
	private GridPane
	enterTimesPane[],enterDatePane;
	private Button
	maleBt, femaleBt, continueBt, continueBt2,
	eventBt[], noTimes[], continueBt3,
	backBt1,backBt2, backBt3,backBt4,
	continueBt4;
	private boolean 
	holdGender, genderPressed, eventPressed[], timesPressed[],backBtPressed, dateBackPressed, editProfile;
	private String 
	holdName, buttonNames[],eventNames[], holdEvents[];
	private double
	timesHold[];
	private TextField 
	nameTf, enterMinutesTf[], enterSecondsTf[];
	private Pane pane;
	private StartPane startPane;
	private DatePicker
	pickStartDate, pickEndDate;
	private LocalDate
	holdStart, holdEnd;
	private int profileNumber, daysBetween;
	
	public NewProfilePane(StartPane prePane, Pane parentPane, int inputNumber ) {
		editProfile = false;
		eventPressed = new boolean[8];
		timesHold = new double[3];
		timesPressed = new boolean[3];
		holdEvents = new String[3];
		startPane = prePane;
		pane = parentPane;
		profileNumber = inputNumber;
		buttonNames = new String[]{"200 Yard Freestyle", "200 Yard Individual Medley", "50 Yard Freestyle","100 Yard Butterfly", "100 Yard Freestyle", "500 Yard Freestyle", "100 Yard Backstroke", "100 Yard Breaststroke"};
		eventNames = new String[]{"200fr","200im","50fr","100fl", "100fr", "500fr", "100bk", "100br"};
		daysBetween = 90;
		addTitle();
		firstPage();
	}
	public NewProfilePane(StartPane prePane, Pane parentPane, int inputNumber,GenericSwimmer profile ) {
		editProfile = true;
		eventPressed = new boolean[8];
		timesHold = new double[3];
		timesPressed = new boolean[3];
		holdEvents = new String[3];
		startPane = prePane;
		pane = parentPane;
		profileNumber = inputNumber;
		buttonNames = new String[]{"200 Yard Freestyle", "200 Yard Individual Medley", "50 Yard Freestyle","100 Yard Butterfly", "100 Yard Freestyle", "500 Yard Freestyle", "100 Yard Backstroke", "100 Yard Breaststroke"};
		eventNames = new String[]{"200fr","200im","50fr","100fl", "100fr", "500fr", "100bk", "100br"};

		
		daysBetween = profile.getPeriod();
		daysBetween = (int) Math.floor(daysBetween*7/5);
		
		holdStart = profile.getStartDate();
		holdEnd = profile.getEndDate();
		dateBackPressed = true;
		
		Event[] events= profile.getEventInventory();
		for(int i = 0; i<3;i++){
			for(int j = 0; j<8;j++){
				if(events[i].getEventName().equals(eventNames[j])){
					eventPressed[j] = true;
					j=8;
				}
			}
			timesHold[i] = events[i].getEventTime();
		}
		addTitle();
		firstPage();
		nameTf.setText(profile.getName());
		genderButtonPressed(profile.getMale());
		
		
		
		
	}
	public void addTitle() {
		setStyle("-fx-background-color:  #2A3439");
		title = new Text("Swimming Workout Planner");
		title.setFont(Font.font("arial", FontWeight.BOLD, 40));
		title.setFill(Color.AQUAMARINE);
		setAlignment(title, Pos.TOP_CENTER);
		setMargin(title, new Insets(20,8,8,8));
		getChildren().add(title);
	}
	
	public void firstPage() {
		noNameText  = new Text("No name entered, please enter your name.");
		invalidEntryText  = new Text("Invalid Entry, Please enter another name.");
		noGenderText = new Text("No gender selected, please select gender.");
		
		addTitle();
		askGender();	
		askName();
		getChildren().add(noNameText);
		noNameText.setVisible(false);
		getChildren().add(noGenderText);
		noGenderText.setVisible(false);
		
		getChildren().add(invalidEntryText);
		invalidEntryText.setVisible(false);
		
		
		
		continueBt = new Button("Next");
		continueBt.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(continueBt, Pos.TOP_RIGHT);
		setMargin(continueBt, new Insets(550,20,8,8));
		getChildren().add(continueBt);
		
		initializeBackButton();
		
		
		continueBt.setOnAction(e -> {
			noNameText.setVisible(false);
			invalidEntryText.setVisible(false);
			noGenderText.setVisible(false);
			if (nameTf.getText() != null 
			&& !nameTf.getText().isEmpty()
			&& !nameTf.getText().equals("New Profile")
			&& genderPressed){
				clearFirstScreen();
				holdName = nameTf.getText();
				chooseEventsPage();
			}
			else if(nameTf.getText().equals("New Profile") ) {
				invalidEntry();
			}
			
			else{
				noNameEntered();
			}
			if( !genderPressed ){
				noGenderSelected();
			}
			
		});
	}
	
	public void chooseEventsPage() {
		
		selectEventText = new Text("Select your 3 best events:");
		selectEventText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 30));
		selectEventText.setFill(Color.AQUAMARINE);
		setAlignment(selectEventText,Pos.TOP_CENTER);
		setMargin(selectEventText, new Insets(150,8,8,8));
		getChildren().add(selectEventText);
		
		
		
		
		
		
		eventBt = new Button[8];
		eventBox1 = new HBox(20);
		getChildren().add(eventBox1);
		eventBox1.setAlignment(Pos.TOP_CENTER);
		setMargin(eventBox1, new Insets(200,8,8,8));
		
		
		
		
		eventBox2 = new HBox(20);
		getChildren().add(eventBox2);
		eventBox2.setAlignment(Pos.TOP_CENTER);
		setMargin(eventBox2, new Insets(250,8,8,8));
		eventBox3 = new HBox(20);
		getChildren().add(eventBox3);
		eventBox3.setAlignment(Pos.TOP_CENTER);
		setMargin(eventBox3, new Insets(300,8,8,8));
		
		for(int i = 0; i<8;i++) {
			eventBt[i] = new Button(buttonNames[i]);
			eventBt[i].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
			eventBt[i].setMinWidth(150);
			if(i<3){
				eventBox1.getChildren().add(eventBt[i]);
			}
			else if((i>3)&&(i<7)){
				eventBox2.getChildren().add(eventBt[i]);
			}
			else{
				eventBox3.getChildren().add(eventBt[i]);
			}			
			
		}
		for(int i = 0; i<8; i++) {
				if(eventPressed[i]){
					eventBt[i].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			}
		defineEventButtonActions();
		
		continueBt2 = new Button("Next");
		continueBt2.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(continueBt2, Pos.TOP_RIGHT);
		setMargin(continueBt2, new Insets(550,20,8,8));
		getChildren().add(continueBt2);
		
		notThreeEvents = new Text();
		notThreeEvents.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		notThreeEvents.setFill(Color.AQUAMARINE);
		setAlignment(notThreeEvents,Pos.TOP_CENTER);
		setMargin(notThreeEvents, new Insets(510,8,20,8));
		getChildren().add(notThreeEvents);
		
		
		
		continueBt2.setOnAction(e -> {
			notThreeEvents.setVisible(false);
			int count =0;
			for(int i = 0; i<8;i++) {
				if(eventPressed[i]){
					count+=1;
				}	
			}
			if(count==3){
				for(int i = 0; i<8;i++) {
					eventBt[i].setVisible(false);
					
					
				}
				continueBt2.setVisible(false);
				selectEventText.setVisible(false);
				eventTimesPage();
				
			}
			else{
				notThreeEvents.setText("You selected " + count + " events, please select 3 events.");
				notThreeEvents.setVisible(true);
			}
		});
		
		backBt2 = new Button("Previous");
		backBt2.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(backBt2, Pos.TOP_LEFT);
		setMargin(backBt2, new Insets(550,8,8,20));
		getChildren().add(backBt2);
		backBt2.setOnAction(e -> {
			for(int i = 0; i<8;i++) {
				eventBt[i].setVisible(false);
					
					
				}
			notThreeEvents.setVisible(false);
			continueBt2.setVisible(false);
			selectEventText.setVisible(false);
			backBt2.setVisible(false);
			firstPage();
			genderButtonPressed(holdGender);
			nameTf.setText(holdName);
			backBtPressed = true;
		});
		
	}
	
	public void eventTimesPage() {
		enterTimesPrompt = new Text("Enter your times for each event");
		enterTimesPrompt.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 30));
		enterTimesPrompt.setFill(Color.AQUAMARINE);
		setAlignment(enterTimesPrompt,Pos.TOP_CENTER);
		setMargin(enterTimesPrompt, new Insets(150,8,8,8));
		getChildren().add(enterTimesPrompt);
		noTimeText  = new Text("No time entered for at least one event, please enter your time or click \"None Available\".");
		getChildren().add(noTimeText);
		noTimeText.setVisible(false);
		invalidTimeText = new Text("Invalid time entered for at least one event.");
		getChildren().add(invalidTimeText);
		invalidTimeText.setVisible(false);
		
		
		
		int count = 0;
		enterTimesText = new Text[3];
		enterTimesPane = new GridPane[3];
		minutesText = new Text[3];
		secondsText = new Text[3];
		enterMinutesTf = new TextField[3];
		enterSecondsTf = new TextField[3];
		noTimes = new Button[3];
		
		
		
		
		
		
		
		for( int i = 0; i < 8; i++ ) {
			if(eventPressed[i]){
				
				
				holdEvents[count] = eventNames[i];
				minutesText[count] = new Text("Minutes");
				minutesText[count].setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
				minutesText[count].setFill(Color.AQUAMARINE);
				
				secondsText[count] = new Text("Seconds");
				secondsText[count].setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
				secondsText[count].setFill(Color.AQUAMARINE);
				
				enterMinutesTf[count] = new TextField();
				enterMinutesTf[count].setMaxWidth(80);
				
				enterSecondsTf[count] = new TextField();
				enterSecondsTf[count].setMaxWidth(80);
				
				noTimes[count] = new Button("Not Available");
				noTimes[count].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
				
				
				enterTimesText[count] = new Text(buttonNames[i]);
				enterTimesText[count].setFont(Font.font("arial", FontWeight.SEMI_BOLD, 30));
				enterTimesText[count].setFill(Color.AQUAMARINE);
				setAlignment(enterTimesText[count],Pos.TOP_CENTER);
				setMargin(enterTimesText[count], new Insets(200 + 100*count,8,8,8));
				getChildren().add(enterTimesText[count]);
				
				enterTimesPane[count] = new GridPane();
				enterTimesPane[count].setHgap(20);
				enterTimesPane[count].add(enterSecondsTf[count],1,0);
				enterTimesPane[count].add(secondsText[count],1,1);
				enterTimesPane[count].add(noTimes[count],2,0);
				
				if(!buttonNames[i].equals("50 Yard Freestyle") ) {
					enterTimesPane[count].add(enterMinutesTf[count],0,0);
					enterTimesPane[count].add(minutesText[count],0,1);
				}
				
				enterTimesPane[count].setAlignment(Pos.TOP_CENTER);
				setMargin(enterTimesPane[count], new Insets(240 + 110*count,8,8,8));
				getChildren().add(enterTimesPane[count]);
				count +=1;
				
			}
		}
		defineTimesButtons();
		backBt3 = new Button("Previous");
		backBt3.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(backBt3, Pos.TOP_LEFT);
		setMargin(backBt3, new Insets(550,8,8,20));
		getChildren().add(backBt3);
		
		backBt3.setOnAction(e -> {
			clearTimesScreen();
			chooseEventsPage();
			for(int i = 0; i<8; i++) {
				if(eventPressed[i]){
					eventBt[i].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			}
			
			for(int i = 0; i<3;i++){
				double minutes =0;
				double seconds =0;
				try{
					minutes = Double.parseDouble(enterMinutesTf[i].getText());
				}catch(Exception f){}
				try{
					seconds = Double.parseDouble(enterSecondsTf[i].getText());
				}catch(Exception g){}
				if((minutes+seconds)!=0){
					timesHold[i] = 60*minutes;
					timesHold[i]+=seconds;
				}
			}
			
		});
		
		for(int i = 0; i<3;i++){
				if(timesHold[i] != 1000&&timesHold[i]!=0){
				int minutes = (int) Math.floor(timesHold[i]/60);
				double seconds = timesHold[i]-60*minutes;
				seconds = Math.round(seconds*100);
				seconds/=100;
				if(timesHold[i]>=60){
				enterMinutesTf[i].setText(Integer.toString(minutes));
				}
				enterSecondsTf[i].setText(Double.toString(seconds));
				
				}
				else if(timesHold[i]==1000){
					timesPressed[i] = true;
					noTimes[i].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
				}
			}
		
		
		continueBt3 = new Button("Next");
		continueBt3.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(continueBt3, Pos.TOP_RIGHT);
		setMargin(continueBt3, new Insets(550,20,8,8));
		getChildren().add(continueBt3);
		
		continueBt3.setOnAction( e -> {
			noTimeText.setVisible(false);
			invalidTimeText.setVisible(false);
			boolean shouldContinue=true;
			for(int i = 0; i<3;i++) {
				double minutes=0;
				double seconds=0;
				if(!timesPressed[i]) {
					try{
						minutes = Double.parseDouble(enterMinutesTf[i].getText());
					}
					catch(Exception f){
						
						if (enterMinutesTf[i].getText() != null 
							&& !enterMinutesTf[i].getText().isEmpty()
							&& !enterTimesText[i].equals("50 Yard Freestyle")){
							invalidTimeEntered();
							shouldContinue = false;
							System.out.println("worked");
							minutes = -71238897;
							}
						else{
						minutes = 0;
						}
					}
					try{
						seconds = Double.parseDouble(enterSecondsTf[i].getText());
					}
					catch(Exception g){
						if (enterSecondsTf[i].getText() != null 
							&& !enterSecondsTf[i].getText().isEmpty()){
							invalidTimeEntered();
							shouldContinue = false;
							seconds = -718242;
							
							}
						else{
						seconds = 0;
						}
					}
					timesHold[i] = 60*minutes + seconds;
					if(timesHold[i]==0){
						noTimeEntered();
						shouldContinue = false;
		
					}
						
					
				}
			}
			if(shouldContinue){
				clearTimesScreen();
				workoutCycle();
			}
			
		});
		
		
	}
	
	
	/**   THIS IS WHERE YOU'RE WORKING**/
	public void workoutCycle() {
		periodPrompt = new Text("Please pick your starting and ending date:");
		periodPrompt.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		periodPrompt.setFill(Color.AQUAMARINE);
		setAlignment(periodPrompt, Pos.TOP_CENTER);
		setMargin(periodPrompt, new Insets(200,8,8,8));
		getChildren().add(periodPrompt);
		pickStartDate = new DatePicker();
		pickEndDate = new DatePicker();
		startDateText = new Text("Start Date");
		endDateText = new Text("End Date");
		enterDatePane = new GridPane();
		startDateText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		startDateText.setFill(Color.AQUAMARINE);
		
		endDateText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		endDateText.setFill(Color.AQUAMARINE);
		
		if(dateBackPressed){
			pickStartDate.setValue(holdStart);
			pickEndDate.setValue(holdEnd);	
		}
		else{
			pickStartDate.setValue(LocalDate.now());
			pickEndDate.setValue(pickStartDate.getValue().plusDays(daysBetween));	
		}
		
		
		pickStartDate.setShowWeekNumbers(true);
		pickEndDate.setShowWeekNumbers(true);
		
		
		
		enterDatePane.setHgap(20);
		enterDatePane.add(startDateText, 0, 0);
		enterDatePane.add(pickStartDate, 0, 1);
		enterDatePane.add(endDateText, 1, 0);
		enterDatePane.add(pickEndDate, 1, 1);
		enterDatePane.setAlignment(Pos.TOP_CENTER);
		setMargin(enterDatePane, new Insets(240,8,8,8));
		getChildren().add(enterDatePane);
		
		continueBt4 = new Button("Save");
		continueBt4.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(continueBt4, Pos.TOP_RIGHT);
		setMargin(continueBt4, new Insets(550,20,8,8));
		getChildren().add(continueBt4);
		continueBt4.setOnAction(e -> {
			noDateText = new Text();
			noDateText.setVisible(true);
			noDateText.setFont(Font.font("arial", FontWeight.BOLD, 20));
		noDateText.setFill(Color.AQUAMARINE);
		setAlignment(noDateText, Pos.TOP_CENTER);
		setMargin(noDateText, new Insets(530,8,8,20));
		getChildren().add(noDateText);
		noDateText.setVisible(false);
			long p = 0;
			try{
				p = ChronoUnit.DAYS.between(pickStartDate.getValue(), pickEndDate.getValue() );
				if(p<30){
					noDateText.setText("Period must be at least 30 days");
					noDateText.setVisible(true);
				}
				else if(p>365) {
					noDateText.setText("Period must be no more than 365 days");
					noDateText.setVisible(true);
				}
				
				
			}catch(Exception f){
				System.out.println("Exception");
			}
			if((p>=30)&&(p<=365)){
					Manager mgr = new Manager();
					mgr.saveNewProfile(holdName,holdGender,holdEvents,timesHold,p,profileNumber, pickStartDate.getValue(),pickEndDate.getValue() );
					setVisible(false);
					startPane.setVisible(false);
					
					Button[] profileBt;
					profileBt = startPane.getProfileButtons();
					profileBt[profileNumber-1].setText(holdName);
					ProfilePane profilePane;
					profilePane = new ProfilePane(profileNumber,pane,startPane);
					profilePane.setMinWidth(611);
					profilePane.setMinHeight(611);
					pane.getChildren().add(profilePane);	
				}
		});
		backBt4 = new Button("Previous");
		backBt4.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(backBt4, Pos.TOP_LEFT);
		setMargin(backBt4, new Insets(550,8,8,20));
		getChildren().add(backBt4);
		backBt4.setOnAction(e -> {
			holdStart = pickStartDate.getValue();
			holdEnd = pickEndDate.getValue();
			dateBackPressed= true;
			enterDatePane.setVisible(false);
			periodPrompt.setVisible(false);
			backBt4.setVisible(false);
			continueBt4.setVisible(false);
			eventTimesPage();
			
			
		});
	}
	
	
	
	
	
	public void initializeBackButton() {
		backBt1 = new Button("Previous");
		getChildren().add(backBt1);
		backBt1.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(backBt1, Pos.TOP_LEFT);
		setMargin(backBt1, new Insets(550,8,8,20));
		
		backBt1.setOnAction(e -> {
			clearErrors();
			setVisible(false);
			if(editProfile){
				ProfilePane profilePane = new ProfilePane(profileNumber,pane,startPane);
				profilePane.setMinWidth(611);
				profilePane.setMinHeight(611);
				pane.getChildren().add(profilePane);
			}
		});
	}
	
	public void askName() {
		namePrompt = new Text("Enter your name below:");
		namePrompt.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		namePrompt.setFill(Color.AQUAMARINE);
		
		setAlignment(namePrompt, Pos.TOP_CENTER);
		setMargin(namePrompt, new Insets(100,8,8,8));
		getChildren().add(namePrompt);
		
		nameTf = new TextField ();
		nameTf.setMaxWidth(100);
		nameTf.setPromptText("Enter your name here");
		setAlignment(nameTf, Pos.TOP_CENTER);
		setMargin(nameTf, new Insets(130,8,8,8));
		getChildren().add(nameTf);
	}
	public void askGender() {
		genderBox = new HBox(20);
		maleBt = new Button("Male");
		femaleBt = new Button("Female");

		chooseGenderText = new Text("Select gender:");
		chooseGenderText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		chooseGenderText.setFill(Color.AQUAMARINE);
		setAlignment(chooseGenderText,Pos.TOP_CENTER);
		setMargin(chooseGenderText, new Insets(170,8,8,8));
		getChildren().add(chooseGenderText);
		
		
		maleBt.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(maleBt, Pos.TOP_CENTER);
		
		genderBox.getChildren().add(maleBt);
		maleBt.setMinWidth(80);
		femaleBt.setMaxWidth(80);
		
		femaleBt.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		setAlignment(femaleBt, Pos.TOP_CENTER);
		genderBox.getChildren().add(femaleBt);
		genderBox.setAlignment(Pos.TOP_CENTER);
		
		setMargin(genderBox, new Insets(200,8,8,8));
		getChildren().add(genderBox);
		maleBt.setOnAction(e -> {genderButtonPressed(true);});
		femaleBt.setOnAction(e -> {genderButtonPressed(false);});
	}
	public void genderButtonPressed(boolean male) {
		if(male) {
			maleBt.setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			femaleBt.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		}
		else{
			femaleBt.setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			maleBt.setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
		}
		genderPressed = true;
		holdGender = male;
	}
	
	
	
	
	public void noNameEntered() {
		noNameText.setVisible(true);
		noNameText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		noNameText.setFill(Color.AQUAMARINE);
		setAlignment(noNameText, Pos.TOP_LEFT);
		setMargin(noNameText, new Insets(530,8,8,20));
		
	}
	
	public void invalidEntry() {
		invalidEntryText.setVisible(true);
		invalidEntryText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		invalidEntryText.setFill(Color.AQUAMARINE);
		setAlignment(invalidEntryText, Pos.TOP_LEFT);
		setMargin(invalidEntryText, new Insets(530,8,8,20));
		
	}
	
	public void noGenderSelected() {
		noGenderText.setVisible(true);
		noGenderText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, 20));
		noGenderText.setFill(Color.AQUAMARINE);
		setAlignment(noGenderText, Pos.TOP_LEFT);
		setMargin(noGenderText, new Insets(510,8,8,20));
	}
	public void clearFirstScreen() {
		nameTf.setVisible(false);
		genderBox.setVisible(false);
		chooseGenderText.setVisible(false);
		namePrompt.setVisible(false);
		maleBt.setVisible(false);
		femaleBt.setVisible(false);
		continueBt.setVisible(false);
		backBt1.setVisible(false);
	}
	public void clearErrors() {
		noNameText.setVisible(false);
		noGenderText.setVisible(false);
	}
	
	
	public void defineEventButtonActions() {
		eventBt[0].setOnAction(e -> {
				if(eventPressed[0]){
					eventPressed[0]=false;
					eventBt[0].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[0] = true;
					eventBt[0].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
		eventBt[1].setOnAction(e -> {
				if(eventPressed[1]){
					eventPressed[1]=false;
					eventBt[1].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[1] = true;
					eventBt[1].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
		eventBt[2].setOnAction(e -> {
				if(eventPressed[2]){
					eventPressed[2]=false;
					eventBt[2].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[2] = true;
					eventBt[2].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
			
		eventBt[3].setOnAction(e -> {
				if(eventPressed[3]){
					eventPressed[3]=false;
					eventBt[3].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[3] = true;
					eventBt[3].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
		eventBt[4].setOnAction(e -> {
				if(eventPressed[4]){
					eventPressed[4]=false;
					eventBt[4].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[4] = true;
					eventBt[4].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
		eventBt[5].setOnAction(e -> {
				if(eventPressed[5]){
					eventPressed[5]=false;
					eventBt[5].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[5] = true;
					eventBt[5].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
		eventBt[6].setOnAction(e -> {
				if(eventPressed[6]){
					eventPressed[6]=false;
					eventBt[6].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[6] = true;
					eventBt[6].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
		eventBt[7].setOnAction(e -> {
				if(eventPressed[7]){
					eventPressed[7]=false;
					eventBt[7].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
				else{
					eventPressed[7] = true;
					eventBt[7].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 10 ; -fx-font-family: 'Arial'");
				}
			});
		
	}
	
	
	
	public void clearTimesScreen() {
		for(int i = 0; i < 3; i++) {
			enterTimesPane[i].setVisible(false);
			enterTimesText[i].setVisible(false);
			minutesText[i].setVisible(false);
			secondsText[i].setVisible(false);
			enterMinutesTf[i].setVisible(false);
			enterSecondsTf[i].setVisible(false);
			noTimeText.setVisible(false);
			invalidTimeText.setVisible(false);
			backBt3.setVisible(false);
			
			
			
			
			
		}
		enterTimesPrompt.setVisible(false);
		continueBt3.setVisible(false);
	}
	
	public void defineTimesButtons() {
		noTimes[0].setOnAction(e -> {
			if(!timesPressed[0]){
				timesHold[0] = 1000;
				timesPressed[0] = true;
				noTimes[0].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			}
			else{
				timesHold[0]=0;
				timesPressed[0] = false;
				noTimes[0].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			}
		});
		noTimes[1].setOnAction(e -> {
			if(!timesPressed[1]){
				timesHold[1] = 1000;
				timesPressed[1] = true;
				noTimes[1].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			}
			else{
				timesHold[1]=0;
				timesPressed[1] = false;
				noTimes[1].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			}
		});
		noTimes[2].setOnAction(e -> {
			if(!timesPressed[2]){
				timesHold[2] = 1000;
				timesPressed[2] = true;
				noTimes[2].setStyle("-fx-background-color: #E0FFFF ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			}
			else{
				timesHold[2]=0;
				timesPressed[2] = false;
				noTimes[2].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
			}
		});
	}
	
	public void noTimeEntered() {
		noTimeText.setVisible(true);
		noTimeText.setFont(Font.font("arial", FontWeight.BOLD, 12));
		noTimeText.setFill(Color.AQUAMARINE);
		setAlignment(noTimeText, Pos.TOP_LEFT);
		setMargin(noTimeText, new Insets(530,8,8,20));
	}
	
	public void invalidTimeEntered() {
		invalidTimeText.setVisible(true);
		invalidTimeText.setFont(Font.font("arial", FontWeight.BOLD, 12));
		invalidTimeText.setFill(Color.AQUAMARINE);
		setAlignment(invalidTimeText, Pos.TOP_LEFT);
		setMargin(invalidTimeText, new Insets(510,8,8,20));
	}
	
	
	
	
	
	
}


