import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class StartPane extends StackPane {
	
	
	private Text
	//Declare all text needed below:
	title, profilePrompt;
	private Button[] profileBt = new Button[3];
	
	
	
	
	
	public StartPane() {
		//setStyle("-fx-background-color:  #F5FFFA");
		setStyle("-fx-background-color:  #2A3439");
		
		
		
		
		title = new Text("Swimming Workout Planner");
		title.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 40));
		title.setFill(Color.AQUAMARINE);
		setAlignment(title, Pos.TOP_CENTER);
		setMargin(title, new Insets(20,8,8,8));
		getChildren().add(title);
		
		profilePrompt = new Text("Choose Your Profile:");
		profilePrompt.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		profilePrompt.setFill(Color.AQUAMARINE);
		setAlignment(profilePrompt, Pos.TOP_CENTER);
		setMargin(profilePrompt, new Insets(200,8,8,8));
		getChildren().add(profilePrompt);
		
		makeProfileButtons();
		
		
		
		
		
			
			
		
	}
	
	
	public void makeProfileButtons() {
			String[] name = new String[3];
		
			for(int i = 0; i<3;i++) {
				profileBt[i] = new Button("");
				profileBt[i].setText("New Profile");
				name[i] = "";
				
				try{
					
					
						String filePath = "C:\\Swim_Planner\\profile\\profile" + Integer.toString(i+1) +".txt";
						File file = new File(filePath);
						FileReader fr = new FileReader(file);
						BufferedReader br = new BufferedReader(fr);
						
						String line;
						while( ( line = br.readLine() ) != null   ) {
							boolean same = line.equals("Profile");
							if(same) {
								name[i] = br.readLine();
							}					
						}
						
						if(name[i]==null || name[i] == ""){
							name[i] = "New Profile";
						}
					br.close();
					}
				catch(Exception e) {
					name[i] = "New Profile";
				}
				
				
				profileBt[i].setText(name[i]);
				profileBt[i].setStyle("-fx-background-color: #7FFFD4 ; -fx-text-fill: #2A3439; -fx-font-size: 16 ; -fx-font-family: 'Arial'");
				setAlignment(profileBt[i], Pos.TOP_CENTER);
				setMargin(profileBt[i], new Insets(270+60*i,8,8,8));
				getChildren().add(profileBt[i]);
				
				
				
				
			}	
		}
		public Button[] getProfileButtons() {
				return profileBt;
			}
}