package serverTest;

import java.net.ServerSocket;

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
public class Server {

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
	public static void main(String[] args) throws Exception {
		
		//Open up our server socket
		ServerSocket listener = new ServerSocket(1777);
		System.out.println("Server is Running");
		
		try {
			
			//Handles creation of games
			while (true) {
				
				//Create a game to house our players
				Game game = new Game();
				System.out.println("Game Created");
				
				//Accepts our player1
				System.out.println("Waiting for player1");
				GamePlayer player1 = new GamePlayer(listener.accept(), "player1");
				System.out.println(player1.name + " Connected");
				
				//Accepts our player2
				System.out.println("Waiting for player2");
				GamePlayer player2 = new GamePlayer(listener.accept(), "player2");
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
			
		} catch (Exception e) {
			
			System.out.println(e);
			
		} finally {
			
			//housekeeping
			listener.close();
			
		}
	}
}