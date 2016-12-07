package serverTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import clientTest.CheckerMove;

/**
 * The player class is a thread created by the server to handle inputs and
 * outputs to the clients
 * 
 * @author Logan
 *
 */
public class GamePlayer extends Thread {
	
	String name;
	GamePlayer opponent;
	Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;

	/**
	 * Constructor that manages creating the connections for players based on
	 * socket and name
	 * 
	 * @param socket
	 * @param name
	 */
	public GamePlayer(Socket socket, String name) {
		
		System.out.println("Begin player creation");
		this.socket = socket;
		this.name = name;
		
		try {
			System.out.println("Try");
			output = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Output Stream created");
			input = new ObjectInputStream(socket.getInputStream());
			System.out.println("Input Stream created");
			System.out.println("player created");
			
		} catch (IOException e) {
			
			System.out.println("Player died: " + e);
			
		}
	}

	/**
	 * The beginning of the thread, this will handle initial messages to players
	 * as well as inputs from the players afterward
	 * 
	 */
	public void run() {
		try {
			
			// Send player1 a command for input
			if (this.name.equals("player1")) {
				output.writeObject("your turn");
				System.out.println("player1 turn");
			}
			
			// Send player2 a command to wait and read
			if (this.name.equals("player2")) {
				output.writeObject("wait");
				System.out.println("player2 wait");
			}
			
			// We will now loop through turns telling
			// respective players to send moves and wait
			CheckerMove message;

			while (true) {
				
				// read message from who's turn it is
				System.out.println("expecting move");
				message = (CheckerMove)input.readObject();
				
				// propagates the message to opponent
				opponent.output.writeObject(message);
				// now asks for a message from opponent
				opponent.output.writeObject("your turn");
				// and asks player to wait for message
				output.writeObject("wait");
				
			}
		} catch (Exception e) {
			
			System.out.println(e);
			
		} finally {
			
			//housekeeping
			try {
				socket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
	}
	
	/**
	 * Used to pair players up
	 * 
	 * @param opponent
	 */
	public void setOpponent(GamePlayer opponent) {
		this.opponent = opponent;
	}
}