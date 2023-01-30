package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppC extends Application{

	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		AppS.primaryStage=primaryStage;
		
		ControllerC control= new ControllerC();
		primaryStage.setTitle("Chat Server (Cliente)");
		primaryStage.setScene(new Scene(control.getView()));
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
