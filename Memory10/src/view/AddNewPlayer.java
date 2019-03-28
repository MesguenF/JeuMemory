package view;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;  
         
public class AddNewPlayer extends Application { 
	
	//creating label title
	Text textTitle = new Text("AJOUTER UN JOUEUR:");    
	
	//creating label Nom, Prénom, Pseudo
    Text text1 = new Text("Nom");       
    Text text2 = new Text("Prénom"); 
    Text text3 = new Text("Pseudo"); 
     
    //Creating Text Field for Nom, Prénom, Pseudo        
    TextField playerFirstNameField = new TextField();
    TextField playerLastNameField = new TextField();  
    TextField playerHandleNameField = new TextField();  
    
    //Creating Buttons 
    Button button1 = new Button("Enregister"); 
    Button button2 = new Button("Annuler");
		
	@Override 
	public void start(Stage stage) {
		  //Creating a Grid Pane 
	      GridPane gridPane = new GridPane();    
	      //Setting size for the pane 
	      gridPane.setMinSize(300, 250); 
	      //Setting the padding  
	      gridPane.setPadding(new Insets(10, 10, 10, 10)); 
	      //Setting the vertical and horizontal gaps between the columns 
	      gridPane.setVgap(5); 
	      gridPane.setHgap(5);       
	      //Setting the Grid alignment 
	      gridPane.setAlignment(Pos.CENTER); 
	      
	      //Arranging all the nodes in the grid 
	      gridPane.add(textTitle, 1, 0); 
	      gridPane.add(text1, 0, 1); 
	      gridPane.add(playerFirstNameField, 1, 1); 
	      gridPane.add(text2, 0, 2);       
	      gridPane.add(playerLastNameField, 1, 2); 
	      gridPane.add(text3, 0, 3);       
	      gridPane.add(playerHandleNameField, 1, 3); 
	      gridPane.add(button1, 4, 2); 
	      gridPane.add(button2, 4, 3); 
	      
	      //Styling nodes  
	      button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
	      button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
	      text1.setStyle("-fx-font: normal bold 20px 'serif' "); 
	      text2.setStyle("-fx-font: normal bold 20px 'serif' ");
	      text3.setStyle("-fx-font: normal bold 20px 'serif' ");
	      textTitle.setStyle("-fx-font: normal bold 20px 'serif' ");    
	      
	      //Creating a scene object 
	      Scene scene = new Scene(gridPane); 
	      //Setting title to the Stage 
	      stage.setTitle("NOUVELLE PARTIE"); 
	      //Adding scene to the stage 
	      stage.setScene(scene);
	      //Displaying the contents of the stage 
	      stage.show();
	      
	      //Events actions
	      button1.setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override
	    	    public void handle(ActionEvent event) {
	    	    	playerFirstNameField.setText("Essai1");
	    	    	playerLastNameField.setText("Essai2");
	    	    	playerHandleNameField.setText("Essai3");
	    	    	
	    	    }
	    	});
	      
	      button2.setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override
	    	    public void handle(ActionEvent event) {
	    	    	playerLastNameField.setText("Essai2");
	    	    	
	    	    }
	    	});
}
	/**
	 * @return the playerFirstNameField
	 */
	public TextField getPlayerFirstNameField() {
		return playerFirstNameField;
	}

	/**
	 * @return the playerLastNameField
	 */
	public TextField getPlayerLastNameField() {
		return playerLastNameField;
	}

	/**
	 * @return the playerHandleNameField
	 */
	public TextField getPlayerHandleNameField() {
		return playerHandleNameField;
	}
	
public static void main(String args[]){ 
      launch(args);
      
   } 
}
