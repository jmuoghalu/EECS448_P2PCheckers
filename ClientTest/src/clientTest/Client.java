package clientTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The client is used to send messages to the server based on the command given
 * from the server these messages will then be passed to the second player
 * 
 * @author Logan
 *
 */
public class Client {

	private final static int PORT = 1777;
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;

	/**
	 * Constructor to open a socket based on input server address Also prepares
	 * to read from and write to the socket
	 * 
	 * @param serverAddress
	 *            The address to connect to
	 * @throws Exception
	 */
	public Client(String serverAddress) throws Exception {
		// Opens a socket
		socket = new Socket(serverAddress, PORT);
		System.out.println("Socket opened.");

		// Opens an ObjectInputStream to read from the socket
		fromServer = new ObjectInputStream(socket.getInputStream());

		// Opens an ObjectInputStream to write to the socket
		toServer = new ObjectOutputStream(socket.getOutputStream());
	}

	/**
	 * This simulates a game loop where we 1. Wait for our turn 2. Send a
	 * message 3. Go to 1.
	 * 
	 * @throws Exception
	 */
	public void play() throws Exception {
		// Object to be used for writing to the socket
		CheckerMove out = new CheckerMove();
		// Object to be used to read from the socket
		CheckerMove in = new CheckerMove();

		// This string is used to give the client
		// directions: your turn, wait
		String directions = null;

		// Place holders
		String pos1;
		String pos2;

		// Creates a reader to read console input
		BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));

		try {
			// Our game loop
			while (true) {

				// read the directions from the server
				System.out.println("reading directions");
				directions = (String) fromServer.readObject();
				System.out.println("directions read");

				// Depending on the directions
				if (directions.equals("your turn")) {

					// This is the area we need to put together our intended
					// move

					System.out.println("your turn");

					//THIS IS WHERE WE GET A MOVE
					fromConsole.readLine();
					out = generate();
					

					// send this move through the socket
					System.out.println("writing move");
					toServer.writeObject(out);
					System.out.println("wrote move");

				} else {

					// We were told to wait, so we need to do nothing
					System.out.println("wait");

					// after being told to wait, we would have again read in
					// from the socket
					// this will be the opponents move
					System.out.println("reading in move");
					in = (CheckerMove) fromServer.readObject();
					System.out.println("move read");

				}
			}
		} catch (Exception e) {

			System.out.println(e);

		} finally {

			// housekeeping
			socket.close();

		}
	}

	/**
	 * Always connects to localhost and starts the client game loop
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		while (true) {
			
			String serverAddress = "localhost";
			
			Client client = new Client(serverAddress);
			
			System.out.println("PLAY");
			client.play();

		}
		
	}
	
	//used for testing
	private CheckerMove generate() {
		Player player = Player.ONE;
		PieceType piecetype = PieceType.PAWN;
		Piece piece = new Piece(piecetype, player);
		CheckerSquare checkersquare1 = new CheckerSquare(1);
		CheckerSquare checkersquare2 = new CheckerSquare(2);
		checkersquare1.setPiece(piece);
		checkersquare2.setPiece(piece);
		CheckerMove checkermove = new CheckerMove(checkersquare1, checkersquare2);
		
		return checkermove;
	}
}
