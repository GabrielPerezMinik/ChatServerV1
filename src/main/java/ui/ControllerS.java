package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import server.Servidor;
import server.cliente.ClienteF;

public class ControllerS implements Initializable {

	final int PUERTO = 50555;

	ClienteF cliente;
	
	Servidor servidor;

	String mensajeF = "";

	@FXML
	private TextArea chatArea;

	@FXML
	private Button enviarButton;

	@FXML
	private ListView<String> listaAmigos;

	@FXML
	private TextField mensajeText;

	@FXML
	private Label userLabel;

	@FXML
	private VBox vboxMessage;

    @FXML
    private ScrollPane vistaMain;
	
	@FXML
	private BorderPane view;

	public ControllerS() throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			servidor = new Servidor(PUERTO);
			servidor.conectar();
		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		if(servidor.estaConectado()) {
			try {
				servidor.aceptarConexion();
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		

		vboxMessage.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				vistaMain.setVvalue((Double) newValue);
			}
		});

		servidor.getConexionCliente().recibirMensajeDelCliente(vboxMessage);
		
		
	}

//	@FXML
//	void onEnviar(ActionEvent event) {
//
//		String mensajeF2 = "";
//		mensajeF2 = "Yo: \n" + mensajeText.getText();
//		mensajeF2=Servidor.toString();
//		mensajeF += mensajeF2 + "\n";
//		chatArea.setText(mensajeF);
//	}

	@FXML
	void onEnviar(ActionEvent event) {
		
		String mensajeEnviar = mensajeText.getText();
		if(!mensajeEnviar.isEmpty()) {
			HBox hbox=new HBox();
			hbox.setAlignment(Pos.CENTER_RIGHT);
			hbox.setPadding(new Insets(5,5,5,10));
			
			Text text = new Text(mensajeEnviar);
			TextFlow textFlow = new TextFlow(text);
			
			textFlow.setStyle("-fx-color: rgb(239,242,255); "+ "-fx-background-color:rgb(15,125,242); " + "-fx-background-radius: 20px;");
		
			textFlow.setPadding(new Insets(5,10,5,10));
			text.setFill(Color.color(0.934, 0.945, 0.996));
			
			hbox.getChildren().add(textFlow);
			vboxMessage.getChildren().add(hbox);
			
			servidor.enviarATodos(mensajeEnviar);
			mensajeText.clear();
		}
		
	}
	
	public static void addLebel(String mensajeDelServer, VBox vbox) {
		
		HBox hbox=new HBox();
		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.setPadding(new Insets(5,5,5,10));
		
		Text text = new Text(mensajeDelServer);
		TextFlow textFlow = new TextFlow(text);
		
		textFlow.setStyle("-fx-background-color:rgb(233,233,235); " + "-fx-background-radius: 20px;");
	
		textFlow.setPadding(new Insets(5,10,5,10));
			
		hbox.getChildren().add(textFlow);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				vbox.getChildren().add(hbox);
				
				
			}
		});
		
	}
	
	
	public BorderPane getView() {
		return view;
	}

}
