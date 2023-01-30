package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppS extends Application{


	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		AppS.primaryStage=primaryStage;
		
		ControllerS control= new ControllerS();
		primaryStage.setTitle("Chat Server (Servidor)");
		primaryStage.setScene(new Scene(control.getView()));
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
