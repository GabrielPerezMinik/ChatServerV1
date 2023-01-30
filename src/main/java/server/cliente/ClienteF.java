package server.cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.layout.VBox;
import ui.ControllerC;

public class ClienteF {

	private Socket socket;
	private BufferedReader bufferLector;
	private BufferedWriter bufferEscritor;
	
	public ClienteF(Socket socket) {
	
		try {
		this.socket = socket;
		this.bufferLector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.bufferEscritor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}
		catch (Exception e) {
			System.out.println("Error creando el Cliente");
			e.printStackTrace();
			closeSocket();
		}
	
	}
	
	public void enviarMensajealServer(String mensageAlServer) {
		
		try {
			
			bufferEscritor.write(mensageAlServer);
			bufferEscritor.newLine();
			bufferEscritor.flush();
			if(mensageAlServer.equals(":bye")) {
//				closeSocket();
			}
			
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Error al mandar el mensaje al servidor");
			closeSocket();
		
		}
		
	}
	
	public void recibirMensajealServer(VBox vboxMessage) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
		
				while(socket.isConnected()) {
					try {
						String mensajeDelServidor = bufferLector.readLine();
						ControllerC.addLebel(mensajeDelServidor, vboxMessage);
					} catch (Exception e) {

					e.printStackTrace();
					System.out.println("Error al recibir un mensaje del Servidor");
					closeSocket();
					break;
					}
				}				
			}
		}).start();
		
	}
	
	
	public void closeSocket() {
		
		try {
			if (bufferLector!=null) {
				bufferLector.close();
			}
			if (bufferEscritor!=null) {
				bufferEscritor.close();
			}
			if (socket!=null) {
				socket.close();
			}
		}catch (Exception e) {

			e.printStackTrace();
		}
		
	}


	
}
