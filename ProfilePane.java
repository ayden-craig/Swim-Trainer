import javafx.scene.layout.StackPane;
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


public class ProfilePane extends StackPane{
	private int profileNumber;
	private String 
	profileName, showProfile;
	private GenericSwimmer profile;
	private StartPane startPane;
	private Pane pane;
	private NewProfilePane npp;
	
	
	
	public ProfilePane(){
		setStyle("-fx-background-color:  #2A3439");
	}
	
	public ProfilePane(int inputNumber, Pane parent,StartPane inputPane) {
		profileNumber = inputNumber;
		startPane = inputPane;
		setStyle("-fx-background-color:  #2A3439");
		Manager mgr = new Manager();
		mgr.readProfile(profileNumber);
		profile = mgr.getProfile(profileNumber);
		WorkoutPane workoutPane = new WorkoutPane(profile);
		getChildren().add(workoutPane);
		setMargin(workoutPane, new Insets(100,8,8,8));
		
		
		MenuItem miOpenProfile = new MenuItem("Open Profile");
		MenuItem miEditProfile = new MenuItem("Edit Profile");
		MenuItem miAbout = new MenuItem("About...");
		MenuItem miDeleteProfile = new MenuItem("Delete Profile");
		MenuItem miNewProfile = new MenuItem("New Profile");
		// Create Menus
		Menu menuFile = new Menu("File");
		Menu menuHelp = new Menu("Help");
		// Create MenuBar
		MenuBar menuBar = new MenuBar();
		// Add menu items to respective menus
		menuFile.getItems().addAll(miNewProfile,miOpenProfile,miEditProfile, miDeleteProfile);
		menuHelp.getItems().add(miAbout);
		// Add menus to menuBar
		menuBar.getMenus().addAll(menuFile, menuHelp);
		getChildren().add(menuBar);
		setAlignment(menuBar, Pos.TOP_CENTER);
		
		 
		
		
		
		miAbout.setOnAction( e -> {showAbout(); } );
		miOpenProfile.setOnAction(e -> {
			startPane.setVisible(true);
			setVisible(false);
		});
		miEditProfile.setOnAction(e -> {
			setVisible(false);
			npp = new NewProfilePane(startPane,parent, profileNumber,profile);
			npp.setMinWidth(611);
			npp.setMinHeight(611);
			parent.getChildren().add(npp);
			
			
		});
		miNewProfile.setOnAction(e -> {
			String[] holdName = new String[3];
			GenericSwimmer prof;
			
			int profNum=0;
			boolean doIt = false;
			Button testBts[] = startPane.getProfileButtons();
			for(int i = 0; i<3; i++){
				if(testBts[i].getText() .equals("New Profile")){
					doIt = true;
					profNum = i+1;
					i = 3;
				}
			}
			if(doIt){
				
				npp = new NewProfilePane(startPane,parent, profNum);
				npp.setMinWidth(611);
				npp.setMinHeight(611);
				parent.getChildren().add(npp);
			}
			else{
				final String aboutText = "You are trying to make a new profile but all three profiles are full, please delete a profile before making a new one.  ";

				Alert popup = new Alert(Alert.AlertType.INFORMATION, aboutText, ButtonType.OK);
		
				popup.setHeaderText("Error");
				popup.setTitle("Error");
				popup.showAndWait();
			}
		});
		miDeleteProfile.setOnAction(e -> {
			deleteProfilePopup();
		});
		
		
		
		Text title = new Text("Swimming Workout Planner");
		title.setFont(Font.font("arial", FontWeight.BOLD, 40));
		title.setFill(Color.AQUAMARINE);
		setAlignment(title, Pos.TOP_CENTER);
		setMargin(title, new Insets(20,8,8,8));
		getChildren().add(title);
		
	}
	
	private void showAbout(){

		final String aboutText = "This program was written by Ayden Craig for the final project in the class Computer Science II at Embry-Riddle Aeronautical University.  ";

		Alert popup = new Alert(Alert.AlertType.INFORMATION, aboutText, ButtonType.OK);
		
		popup.setHeaderText("About This Program");
		popup.setTitle("About");
		popup.showAndWait();
		
	}
	private void deleteProfilePopup(){

		final String aboutText = "You're about to delete a profile, this cannot be undone. Are you sure you want to continue?  ";

		Alert popup = new Alert(Alert.AlertType.CONFIRMATION, aboutText);
	
		popup.setHeaderText("Delete a profile");
		popup.setTitle("Delete");
		popup.showAndWait().ifPresent(response -> {
     if (response == ButtonType.OK) {
		String filePath = "C:\\Swim_Planner\\profile\\profile" + Integer.toString(profileNumber) +".txt";
		File file = new File(filePath);
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();
			Button profileBt[] = startPane.getProfileButtons();
			profileBt[profileNumber-1].setText("New Profile");
			setVisible(false);
			startPane.setVisible(true);
     }catch(Exception e){
		 System.out.println("Exception in ProfilePane, you were trying to delete a profile");
	 }
	 }
 });
		
	}
}