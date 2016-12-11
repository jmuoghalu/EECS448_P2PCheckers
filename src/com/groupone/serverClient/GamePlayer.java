package com.groupone.serverClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.groupone.p2pgame.*;

/**
 * The player class is a thread created by the server to handle inputs and
 * outputs to the clients
 *
 * @author Logan
 *
 */
public class GamePlayer extends Thread {

	GamePlayer opponent;
	Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;

	Player name;

	/**
	 * Constructor that manages creating the connections for players based on
	 * socket and name
	 *
	 * @param socket
	 * @param name
	 */
	public GamePlayer(Socket socket, Player name) {

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

			// Send what player this is
			output.writeObject(this.name);

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

			// We will now loop through turns telling
			// respective players to send moves and wait
			CheckerBoardState state;

			while (true) {

				// read message from who's turn it is
				System.out.println("expecting move");
				state = (CheckerBoardState)input.readObject();

				// propagates the message to opponent
				opponent.output.writeObject(state);

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
