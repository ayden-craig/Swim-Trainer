import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


public class SwimPlannerMainGUI extends Application {
	private Button[] profileBt;
	
	
	
	public void start(Stage primaryStage) {
	
		Pane pane = new Pane();
		StartPane startPane = new StartPane();
		
		
		
		setUpPane(pane, startPane, true);
			
		
		
		
		Scene scene = new Scene(pane, 600, 600);
		profileBt = startPane.getProfileButtons();
		
		initializeProfileButtons(pane,startPane);
		
		
		// Create a scene and place it in the stage
		
		    
		primaryStage.setTitle("Swim Planner"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.setResizable(false);
		primaryStage.show(); // Display the stage
		
	

	}
	
	public static void main(String[] args) {
		    launch(args);
		  }	
	public void setUpPane(Pane parent, Pane pane, boolean visible){
		pane.setVisible(visible);
		pane.setMinWidth(611);
		pane.setMinHeight(611);
		parent.getChildren().add(pane);
	}
	public void initializeProfileButtons(Pane pane, StartPane startPane){
		
		profileBt[0].setOnAction(e -> {
			if( profileBt[0].getText().equals("New Profile") ) {
				NewProfilePane newProfPane = new NewProfilePane(startPane,pane,1);
				setUpPane(pane, newProfPane, true);
			}
			else{
				ProfilePane profilePane = new ProfilePane(1,pane,startPane);
				setUpPane(pane, profilePane, true);
				profilePane.setVisible(true);
			}	
			});
		profileBt[1].setOnAction(e -> {
			if( profileBt[1].getText().equals("New Profile") ) {
				NewProfilePane newProfPane = new NewProfilePane(startPane, pane,2);
				setUpPane(pane, newProfPane, true);
			}
			else{
				ProfilePane profilePane = new ProfilePane(2,pane,startPane);
				setUpPane(pane, profilePane, true);
			}	
			});
		profileBt[2].setOnAction(e -> {
			if( profileBt[2].getText().equals("New Profile") ) {
				NewProfilePane newProfPane = new NewProfilePane(startPane, pane,3);
				setUpPane(pane, newProfPane, true);
			}
			else{
				ProfilePane profilePane = new ProfilePane(3,pane,startPane);
				setUpPane(pane, profilePane, true);
			}	
			});
	}
		
		
	
}
