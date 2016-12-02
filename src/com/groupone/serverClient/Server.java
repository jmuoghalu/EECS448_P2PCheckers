package com.groupone.serverClient;

import java.net.ServerSocket;
import java.net.InetAddress;

/**
 * The server handles creating "Games" that connect two "Players"
 * The players then send messages to the server in order of turns
 * and the messages are passed along to the other player
 *
 * REFERENCE http://cs.lmu.edu/~ray/notes/javanetexamples/
 *
 * @author Logan
 *
 */
public class Server
{

	/**
	 * Begins listening to a port (chosen from tutorial)
	 * Creates a game and waits for two players to connect
	 * Once two players connect, we start each player thread
	 *
	 * If more than two players connect, we create another game for the excess players
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{

		ServerSocket listener = new ServerSocket(8901);
		//ServerSocket listener = new ServerSocket(8901, 50, InetAddress.getByName(""));

		System.out.println("Server is Running");
		//System.out.println("Server: " + listener.getInetAddress().toString());

		try
		{

			//Handles creation of games
			while (true)
			{
				//Creates a game to house our players
				Game game = new Game();

				//Accepts our player1
				Player player1 = new Player(listener.accept(), "player1");
				System.out.println(player1.name + " Connected");

				//Accepts our player2
				Player player2 = new Player(listener.accept(), "player2");
				System.out.println(player2.name + " Connected");

				//Sets the opponents for each player
				player1.setOpponent(player2);
				player2.setOpponent(player1);

				//makes sure the game knows which two players are together
				game.currentPlayer = player1;
				game.opponent = player2;

				//begins the player thread
				player1.start();
				player2.start();
			}

		}

		catch (Exception e)
		{
			System.out.println(e);
		}

		finally
		{
			//housekeeping
			listener.close();
		}

	}
}
