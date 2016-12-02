package com.groupone.serverClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

/**
 * The client is used to send messages to the server based on the command given from the server
 * these messages will then be passed to the second player
 *
 * @author Logan
 *
 */
public class Client
{

	//Chosen from the tutorial
	private static int PORT = 8901;
	private Socket socket;
	private BufferedReader fromServer;
	private PrintWriter toServer;




	/**
	 * Constructor to open a socket based on input server address
	 * Also prepares to read from and write to the socket
	 *
	 * @param serverAddress The address to connect to
	 * @throws Exception
	 */
	public Client(String serverAddress) throws Exception
	{

		//Opens a socket
		try
		{

			socket = new Socket(serverAddress, PORT);
			//socket = new Socket(InetAddress.getByName(""), PORT);

			//Opens a BufferedReader to read from the socket
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//Opens a PrintWriter to write to the socket
			toServer = new PrintWriter(socket.getOutputStream(), true);

		}

		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("The server is not listening.");
			System.exit(0);
		}
	}






	/**
	 * This simulates a game loop where we
	 * 1. Wait for our turn
	 * 2. Send a message
	 * 3. Go to 1.
	 *
	 * @throws Exception
	 */
	public void play() throws Exception
	{

			//String to be used for writing to the socket
		String message;
			//String to be used to read from the socket
		String response;
			//Creates a reader to read console input
		BufferedReader in1 = new BufferedReader(new InputStreamReader(System.in));

		try
		{
				//Our game loop
			while (true)
			{
				//read the message from the server
				response = fromServer.readLine();

				//Depending on the message
				if (response.equals("your turn"))
				{
					//Our turn so we read in console input
					message = in1.readLine();
					//send this input through the socket
					toServer.println(message);
				}

				else if (response.equals("wait"))
				{
					//We were told to wait, so we need to do nothing
				}

				else
				{
					//after being told to wait, we would have again read in from the socket
					//this will be output
					System.out.println(response);
				}
			}

		}


		catch (Exception e)
		{
			System.out.println(e);
		}


		finally
		{
			//housekeeping
			socket.close();
		}
	}




	/**
	 * Always connects to localhost and starts the client game loop
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{

		while (true)
		{
			String serverAddress = "localhost";
			Client client = new Client(serverAddress);
			client.play();
		}

	}
}
