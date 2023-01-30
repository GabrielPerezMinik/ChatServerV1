package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

import javafx.scene.layout.VBox;
import ui.ControllerC;

public class ConexionCliente extends Thread {

	private Socket socket;
	private PrintWriter salida;
	private BufferedReader entrada;
	private Servidor servidor;
	
//	private BufferedWriter bufferEscritor;
	

	public ConexionCliente(Servidor servidor, Socket socket) throws IOException {
		this.servidor = servidor;
		this.socket = socket;
		salida = new PrintWriter(socket.getOutputStream(), true);
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		bufferEscritor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	@Override
	public void run() {

		salida.println("Bienvenido al Chat");
		salida.println("Puedes enviar mensajes de texto");

		String mensaje = null;
		try {
			while (!((mensaje = entrada.readLine()) != null && !mensaje.equals(":bye"))) {
//				servidor.enviarATodos(mensaje);
//				enviar(mensaje);
				servidor.cortarCliente(this);
				salida.println("** ¡¡ Hasta Pronto !! **");
				entrada.close();
				salida.close();
				socket.close();
			}
//			if((mensaje = entrada.readLine()) != null && !mensaje.equals(":bye")){
//			if(!mensaje.equals(":bye")){
			
//			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void enviar(String mensaje) {
		
		try {
			salida.println(mensaje);
//			bufferEscritor.write(mensaje);
//			bufferEscritor.newLine();
//			bufferEscritor.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		salida.println(mensaje);
	}

	public void recibirMensajeDelCliente(VBox vboxMessage) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
		
				while(socket.isConnected()) {
					try {
						String mensajeDelServidor = entrada.readLine();
						ControllerC.addLebel(mensajeDelServidor, vboxMessage);
//						if(!mensajeDelServidor.equals(":bye")) {
//							salida.println("** ¡¡ Hasta Pronto !! **");
//							entrada.close();
//							salida.close();
//							socket.close();
//						}
					} catch (Exception e) {

					e.printStackTrace();
					System.out.println("Error al recibir un mensaje del Cliente");
					break;
					}
				}				
			}
		}).start();
		
	}
	
//	public void enviar(String mensaje) {
//		salida.println(mensaje);
//	}
	
	@Override
	public int hashCode() {
		return Objects.hash(socket);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConexionCliente other = (ConexionCliente) obj;
		return Objects.equals(socket.getLocalPort(), other.socket.getLocalPort());
	}
	
}
