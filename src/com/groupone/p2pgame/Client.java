package com.groupone.p2pgame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.groupone.serverClient.ServerInfo;

/**
 * The client is used to send messages to the server based on the command given
 * from the server these messages will then be passed to the second player
 *
 * @author Logan
 *
 */
public class Client {

	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private Player player;

	/**
	 * Constructor to open a socket based on input server address Also prepares
	 * to read from and write to the socket
	 *
	 * @throws Exception
	 */
	public Client() throws Exception {
		//Opens a socket

    socket = new Socket(ServerInfo.ADDRESS, ServerInfo.PORT);
    //socket = new Socket(InetAddress.getByName(""), PORT);

    //Opens a BufferedReader to read from the socket
    fromServer = new ObjectInputStream(socket.getInputStream());
    //Opens a PrintWriter to write to the socket
    toServer = new ObjectOutputStream(socket.getOutputStream());

    System.out.println("waiting for player");
    this.player = (Player) fromServer.readObject();

	}

  /*
   * Clean up sockets.
   */
  public void close() throws Exception {
    toServer.close();
    fromServer.close();
  }

  /**
	 * This sends a move to the other player.
	 * @param out Checker move to write to the socket
	 * @throws Exception
	 */
  public void sendBoard(CheckerBoardState out) throws Exception {

      toServer.writeObject(out);

  }

  /**
   * This simulates a game loop where we 1. Look wait for other
   * player's move 2. Make sure the move is valid 3. Return that move
   *
   * @throws Exception
   * @returns The move sent from the other server
   */
  public CheckerBoardState waitForMove() throws Exception {
    // Object to be used to read from the socket
    CheckerBoardState in;

    // read the directions from the server
    in = (CheckerBoardState) fromServer.readObject();

    // TODO: check for valid move

    // exit the wait loop
    return in;

	}

  /**
   * Get the current board state.
   * @return current board state
   */
  public CheckerBoardState getInitialState() {

    return CheckerBoardState.getStartingBoard();
  }


  /**
   * Get the player that this client is.
   */
  public Player getPlayer() {
    return this.player;
  }

}
