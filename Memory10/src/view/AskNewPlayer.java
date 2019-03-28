package view;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;  
         
public class AskNewPlayer extends Application { 
	//creating label Text
    Text text1 = new Text("Voulez-vous ajouetr un autre joueur?");
    Text text2 = new Text("(Nombre de joueurs max = 4)");       
          
    //Creating Buttons 
    Button button1 = new Button("OUI"); 
    Button button2 = new Button("NON");
		
	@Override 
	public void start(Stage stage) {
		  //Creating a Grid Pane 
	      GridPane gridPane = new GridPane();    
	      //Setting size for the pane 
	      gridPane.setMinSize(300, 250); 
	      //Setting the padding  
	      gridPane.setPadding(new Insets(5, 5, 5, 5)); 
	      //Setting the vertical and horizontal gaps between the columns 
	      gridPane.setVgap(5); 
	      gridPane.setHgap(5);       
	      //Setting the Grid alignment 
	      gridPane.setAlignment(Pos.CENTER); 
	      //Arranging all the nodes in the grid 
	      gridPane.add(text1, 0, 0); 
	      gridPane.add(text2, 0, 1); 
	      gridPane.add(button1, 0, 2); 
	      gridPane.add(button2, 1, 2); 
	      //Styling nodes  
	      button1.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
	      button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;"); 
	      text1.setStyle("-fx-font: normal bold 20px 'serif' "); 
	      text2.setStyle("-fx-font: normal bold 20px 'serif' "); 
	      
	      
	      //Creating a scene object 
	      Scene scene = new Scene(gridPane); 
	      //Setting title to the Stage 
	      stage.setTitle("Ajouter un joueur"); 
	      //Adding scene to the stage 
	      stage.setScene(scene);
	      //Displaying the contents of the stage 
	      stage.show();
	      
	      button1.setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override
	    	    public void handle(ActionEvent event) {
	    	    	System.out.println("OUI");
	    	    }
	    	});
	      
	      button2.setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override
	    	    public void handle(ActionEvent event) {
	    	    	System.out.println("NON");
	    	    }
	    	});
}
	   
public static void main(String args[]){ 
      launch(args);
      
   } 
}
