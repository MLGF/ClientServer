
// Fig. 27.5: Server.java
// Server portion of a client/server stream-socket connection. 
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Server extends JFrame {
	/**
	 * 
	 */
	
	A ClientA = new A();
	B ClientB = new B();
	C ClientC = new C();
	private static final long serialVersionUID = 1L;
	private JTextArea displayArea; // display information to user
	private ObjectOutputStream output; // output stream to client
	private ObjectInputStream input; // input stream from client
	private ServerSocket server; // server socket
	private Socket connection, connection2, connection3; // connection to client	
	int clientcounter = 0; // counter of number of connections
	int number = 0;
	
	private String[] commands;
	
	
	public Server() {
		
		super("Server");
		
		//Client1.start();

		displayArea = new JTextArea(); // create displayArea
		displayArea.setEditable(false);
		add(new JScrollPane(displayArea), BorderLayout.CENTER);

		setSize(150, 50); // set size of window
		setVisible(true); // show window
	} // end Server constructor

	// set up and run server
	public void runServer() {
		try // set up server to receive connections; process connections
		{
			server = new ServerSocket(12345, 12); // create ServerSocket

			while (true) {
				try {
					waitForConnection(); // wait for a connection
					getStreams(); // get input & output streams
					processConnection(); // process connection
				} // end try
				catch (EOFException eofException) {
					displayMessage("\nServer terminated connection");
				} // end catch
				finally {
					closeConnection(); // close connection
					clientcounter++;
				} // end finally
			} // end while
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
	} // end method runServer

	// wait for connection to arrive, then display connection info
	private void waitForConnection() throws IOException {
		displayMessage("Waiting for connection\n");
		
		if(clientcounter == 0) {
		ClientA.start();
		clientcounter++;
		connection = server.accept(); // allow server to accept connection
		displayMessage("Connection " + clientcounter + " received from: " + connection.getInetAddress().getHostName());
		}
		if(clientcounter ==1) {
		ClientB.start();
		clientcounter++;

		
		}
		if(clientcounter ==2) {
			ClientC.start();
			clientcounter++;
			
		
		}
		
	} // end method waitForConnection

	// get streams to send and receive data
	private void getStreams() throws IOException {
		// set up output stream for objects
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush(); // flush output buffer to send header information

		// set up input stream for objects
		input = new ObjectInputStream(connection.getInputStream());

		displayMessage("\nGot I/O streams\n");
	} // end method getStreams

	// process connection with client
	private void processConnection() throws IOException {
		String message = "Connection successful";
		sendData(message); // send connection successful message

		// enable enterField so server user can send messages
		// setTextFieldEditable( true );

		do // process messages sent from client
		{
			try // read message and display it
			{
				// message = ( String ) input.readObject(); // read new message
				message = (String) input.readObject();
				commands = message.split("");
				displayMessage("\n" + message); // display message
				if (message.toUpperCase() == "QUIT") {
					System.exit(0);
				}
				
				// else if (number > 1) {
				// message = "Acknowledged";
				// }
				output.writeObject("SERVER: " + message);
				output.flush(); // flush output to client
				displayMessage("\nSERVER: " + message);
				number++;

			} // end try
			catch (ClassNotFoundException classNotFoundException) {
				displayMessage("\nUnknown object type received");
			} // end catch

		} while (!message.equals("CLIENT: TERMINATE"));
	} // end method processConnection

	// close streams and socket
	private void closeConnection() {
		displayMessage("\nTerminating connection\n");
		// setTextFieldEditable( false ); // disable enterField

		try {
			output.close(); // close output stream
			input.close(); // close input stream
			connection.close(); // close socket
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		} // end catch
	} // end method closeConnection

	// send message to client
	private void sendData(String message) {
		try // send object to client
		{
			if (number == 0) {
				message = message + "Server Up";
				output.writeObject(message);
				output.flush();
				displayMessage("\nUsername is: " + message);

			} else if (number >= 1) {

				// output.writeObject( "SERVER: " + message );
				// output.flush(); // flush output to client
				// displayMessage( "\nSERVER: " + message );
				// FileWriter write = new FileFileWriter;

			}

		} // end try
		catch (IOException ioException) {
			displayArea.append("\nError writing object");
		} // end catch
	} 
		
	private void displayMessage(final String messageToDisplay) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() // updates displayArea
			{
				displayArea.append(messageToDisplay); // append message
			}
		});
		
	}
	
	 class A extends Thread {
		 public void run(){
		try {
			connection = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // allow server to accept connection
		displayMessage("Connection " + clientcounter + " received from: " + connection.getInetAddress().getHostName());}
	}
	 class B extends Thread {
		public void run() {
		try {
			connection = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // allow server to accept connection
		displayMessage("Connection " + clientcounter + " received from: " + connection.getInetAddress().getHostName());}
	}
		class C extends Thread {
		public void run(){
		try {
			connection = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // allow server to accept connection
		displayMessage("Connection " + clientcounter + " received from: " + connection.getInetAddress().getHostName());}
	}
	
} // end class Server