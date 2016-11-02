package serverTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The player class is a thread created by the server to handle inputs and
 * outputs to the clients
 * 
 * @author Logan
 *
 */
public class Player extends Thread {
	String name;
	Player opponent;
	Socket socket;
	BufferedReader input;
	PrintWriter output;

	/**
	 * Constructor that manages creating the connections for players based on
	 * socket and name
	 * 
	 * @param socket
	 * @param name
	 */
	public Player(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		}
	}

	/**
	 * Used to pair players up
	 * 
	 * @param opponent
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
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
				output.println("your turn");
			}
			// Send player2 a command to wait and read
			if (this.name.equals("player2")) {
				output.println("wait");
			}
			// We will now loop through turns telling
			// repective players to send messages and wait
			String message;

			while (true) {
				// read message from whos turn it is
				message = input.readLine();
				// logs on the console
				System.out.println(message);
				// propagates the message to opponent
				opponent.output.println(message);
				// now asks for a message from opponent
				opponent.output.println("your turn");
				// and asks player to wait for message
				output.println("wait");
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
}