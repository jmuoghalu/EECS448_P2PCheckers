package com.groupone.p2pgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.List;

/**
   <p>
   CheckerBoard is the main entry point into the checkers
   board. It creates the grid that is filled with all 64 spaces and
   the 24 pieces.
   </p>

   <p>
   The checkers board consists of 32 black spaces and 32 red spaces.
   Only the 32 black spaces will have pieces on them.
   </p>

   <p>
   This keeps track of the "active player", that is who's turn it is
   to move. It stops the other pieces from moving when they are not
   one of the active player's pieces. As of now, there is no obvious
   way to know who's turn it is without trial and error.
   </p>

   <p>
   Each time an active piece is clicked, the possible moves are
   highilighted. Clicking on one of the highlighted spaces causes the
   player's turn to end, and for the next player's turn to begin,
   unless there is another "double jump" that can be made. In those
   cases, the checker board enters "extra jump mode".
   </p>

   <p>
   In extra jump mode, all other movement is frozen except for the
   highlighted extraa jump. In this version of checkers, you cannot
   skip the extra jump. The game will proceed only after you have
   chosen to take extra jumps until you can do so no more. This
   mechanism is to prevent the user from sneaking in another unrelated
   move and passing it off as an "extra jump".
   </p>
*/
public class CheckerBoard extends JPanel implements MouseListener
{

        private JFrame frame;
        private JLayeredPane gameBoard;
        private Container gameBoardBackground;
        private Container gameBoardWithPieces;


        private CheckerBoardSpace[] boardSpaces;
        private GamePiece[] drawnPieces;

        public CheckerBoardState state;

        private CheckerSquare selectedSquare;

        private Player activePlayer;
        private boolean extraJumpMode;

        public JLabel whoseTurn;

        /**
           Start a new game board. Without arguments, this will use
           the default starting board with 12 pieces for player one on
           top, and 12 pieces for player two on bottom.
        */
        public CheckerBoard()
        {
                this(CheckerBoardState.getStartingBoard());
        }




        /**
           Setup a checkerboard from an "CheckerBoardState".
           @see CheckerBoardState
           @param state A valid checker board that has pieces on it.
        */
        public CheckerBoard(CheckerBoardState state)
        {

                super();

		this.state = state;

		// player two goes first
		this.activePlayer = Player.TWO;

		// set to true when you want to disable everything but
		// the second jump.
		this.extraJumpMode = false;

                /******************************** Display Characteristics ********************************/

                this.frame = new JFrame(); // for the first initializeSettings call
                this.initializeSettings();

                /********************************  ********************************/








                /******************************** Initializing Member Variables ********************************/

                // all 64 spaces are stored
                // only the black one are interactive
                boardSpaces = new CheckerBoardSpace[64];

                drawnPieces = new GamePiece[24]; // all of player one's pieces will be placed in this array before player two's pieces

                /********************************  ********************************/




                /******************************* Call drawing methods *****************/


                // draw the static background
                this.drawGameBackground();

                this.drawGameBoard(state); // draws the board at its initial state


                /****************************************************************/




        }



        //MOUSE locations THIS DOESNT DO ANYTHING THE REAL CODE IS FAR BELOW

        @Override
        public void mousePressed(MouseEvent e) {}

	@Override
        public void mouseReleased(MouseEvent e) {}

	@Override
        public void mouseEntered(MouseEvent e) {}

	@Override
        public void mouseExited(MouseEvent e) {}

	@Override
        public void mouseClicked(MouseEvent e) {}



        /**
           Initialie window and container settings. This will
           initialize the "frame", "gameBoard", and
           "gameBoardWithPieces" member variables that are necessary
           to setup the Swing window skeleton.
        */
        public void initializeSettings()
        {

                // get rid of old window if it exists
                this.frame.dispose();

                // create new window
                this.frame = new JFrame();

                // make sure it's visible
                this.frame.setVisible(true);

                // set to include toolbar, borders, and container
                this.frame.setSize(645, 675);

                // prevent from resizing
                this.frame.setResizable(false);

                // setup title
                this.frame.setTitle("Begin Game (Player Two Has First Move)");
                this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



                // layered so we can also use the gameBoardWithPieces
                // on top of it
                this.gameBoard = new JLayeredPane();
                this.gameBoard = frame.getLayeredPane();
                this.gameBoard.setPreferredSize(new Dimension(640, 640));


                this.gameBoardWithPieces = new Container();
                this.gameBoardWithPieces.setSize(640, 640);

                // prevent from resizing
                this.gameBoardWithPieces.setMaximumSize( new Dimension(640, 640) );

                // 8x8 for checkboard
                this.gameBoardWithPieces.setLayout( new GridLayout(8,8) );

                // set black as default background
                this.gameBoardWithPieces.setBackground(Color.BLACK);

        }






        /**
           Initialize the static background for each of the 64 spots.
           Each spot alternates between red and black colored.
         */
	public void drawGameBackground()
        {

                /******************************** Initial Board Spaces ********************************/

                int color = 0;
                int x = 0; // row number
                int y = 0; // column number

                // creates CheckerBoardSpace objects
                for(int A = 0 ; A < 64 ; A++)
                {

                        boardSpaces[A] = new CheckerBoardSpace(this, color, A);

                        if(y == 7) // when in last column
                        {
                                x++; // increment row
                                y = 0;
                                color = (color % 2); // makes sure that the next row begins with an alternating color
                        }
                        else
                        {
                                y++;
                                color++; // alternating the colors for the CheckerBoardSpace constructor
                        }

                }

	}







        /**
           Draw the game board.
           @param state The state of the board to be drawn.
           @note Ideally, state should be the only input for the
           method, but it also uses some member variables to set
           things up properly.
           @see CheckerBoardState
         */
        public void drawGameBoard(CheckerBoardState state) // use for updating display of game board
        {


                /******************************** Adding Pieces to Board ********************************/

                // creates the 24 game pieces --> player one's pieces are the first 12, and player two's pieces are second
		int n = 0;
                for( CheckerSquare square : state.getSquares())
                {

			if (square.getPiece().getType() != PieceType.EMPTY)
                        {

				if (square.getPiece().getPlayer() == Player.ONE)
                                {
					drawnPieces[n++] = new GamePiece(this, Color.BLUE, square.getIndex(), square.getPiece().getPlayer());
				}

                                else if (square.getPiece().getPlayer() == Player.TWO)
                                {
					drawnPieces[n++] = new GamePiece(this, Color.LIGHT_GRAY, square.getIndex(), square.getPiece().getPlayer());
				}


                                // set the new piece to King
                                if (square.getPiece().getType() == PieceType.KING)
                                {
                                        // the former value of n
                                        drawnPieces[n-1].setToKing();
                                }

			}

                }



                // hold onto the current piece
                JPanel currentPiece;

                // fill in if there is no real piece
                CheckerBoardSpace currentBoardSpace;

                // holds the piece's number
                JLabel currentPieceNumber;


                // get the list of locations from the CheckerBoardState object
		int[] playerOnePiecesLocations = state.getPlayerOnePieceLocationsInts();
		int[] playerTwoPiecesLocations = state.getPlayerTwoPieceLocationsInts();

		int curLocation = 0;
                while( curLocation < 64 ) // cycles through full board
                {

                        // get the board space that was initialize above
                        currentBoardSpace = boardSpaces[curLocation];

                        // assume nothing was found
			boolean found = false;

                        // cycle through each drawn piece
			for (GamePiece piece : drawnPieces) {
                                // check if this piece matches the index we're looking at
				if (piece != null && piece.getIndex() == curLocation) {
                                        // remember the current piece
					currentPiece = piece;

                                        // set the background to what
                                        // it would have been if it
                                        // was empty.
					currentPiece.setBackground(currentBoardSpace.getSpaceColor());

                                        // add the piece to the container
					gameBoardWithPieces.add(currentPiece);

					found = true; // mark as found
					break;
				}
			}

                        // if nothing was found from the drawnPieces
			if (!found)
                        {
                                gameBoardWithPieces.add(currentBoardSpace); // the current space is empty
			}

                        curLocation++; // go to next square
                }

                // add the whole container
                gameBoard.add(gameBoardWithPieces, 0);

                // make sure verything is visible
                this.frame.setVisible(true);
        }









        /**
           Clear the highlighted pieces of the board. This method
           should restore the board to how it was initially. All blue
           spaces and golden piece borders are removed.
        */
	public void clearHighlights()
        {

                // disregard in extra jump mode so we don't cheat
		if (extraJumpMode)
                {
			return;
		}


                // go through each drawn piece
		for (GamePiece piece : this.drawnPieces)
                {
			if (piece != null)
                        { // verify it isn't null
				piece.deselect(); // deselect it
			}
		}

                // go through each board space
		for (CheckerBoardSpace space : this.boardSpaces)
                {
			if (space != null)
                        { // make sure it isn't null
				space.dehighlight(); // un highlight it
			}
		}
	}






        /**
           Indicate the allowed moves on the board. This is
           accomplished by highlighting the end squares for each move.
           Once the square are highlighted, the user can click on one
           square to perform the move.
           @param square The square to start the move from.
        */
	public void showAllowedMoves(CheckerSquare square)
        {
                // get valid moves from CheckerBoadState
		List<CheckerMove> moves = this.state.getValidMoves(square);

                // iterate through each move
		for (CheckerMove move : moves)
                {
                        // highlight the space corresponding to that index.
			this.boardSpaces[move.getEnd().getIndex()].highlight();
		}
	}






        /**
           Set the game piece as selected. This is called from
           GamePiece to ask the board to show it available moves.
           @param index The index corresponding to the piece that has
           been selected.
         */
        public void setSelected(int index)
        {
		CheckerSquare square = this.state.getSquare(index);

		// only let active player select
		if (square.getPiece().getPlayer() == this.activePlayer)
                {
			this.selectedSquare = square;

                        // auto show allowed moves
                        this.showAllowedMoves(this.selectedSquare);
		}
	}






        /**
           Check if the board is in "extra jump mode".
           See top for more details.
           @return Whether or not board is in extra jump mode.
        */
        public boolean isInExtraJumpMode()
        {
                return this.extraJumpMode;
        }







        /**
           Redraw all of the UI elements. This will remove everything
           from the board, then re init everything, then draw the
           board state again.
         */
        public void redrawAll()
        {
		// cleanup everything
		this.removeAll();

		// re init everything
                this.gameBoard = new JLayeredPane();
                this.gameBoard = frame.getLayeredPane();
                this.gameBoard.setPreferredSize(new Dimension(640, 640));

                // re init more stuff
                this.gameBoardWithPieces = new Container();
                this.gameBoardWithPieces.setSize(640, 640);
                this.gameBoardWithPieces.setMaximumSize( new Dimension(640, 640) );
                this.gameBoardWithPieces.setLayout( new GridLayout(8,8) );
                this.gameBoardWithPieces.setBackground(Color.BLACK);

                // re init even more stuff
                boardSpaces = new CheckerBoardSpace[64];
                drawnPieces = new GamePiece[24]; // all of player one's pieces will

		// redraw background
		this.drawGameBackground();

                this.drawGameBoard(state); // draws the board at its new state
        }








        /**
           Update game pieces with King status where appropriate
         */
        public void setKings()
        {

                for( GamePiece dPiece : drawnPieces )
                {
                        if( dPiece != null && dPiece.getPlayer() == getActivePlayer() )
                        {
                                if ( state.getSquare( dPiece.getIndex() ).getPiece().getType() == PieceType.KING )
                                {
                                        dPiece.setToKing();
                                }
                        }
                }

        }




        /**
           Move the piece selected piece to index. Instead of just
           modifying the current board, this will first modify the
           game board state then redraw everything with the new state.
           This is in place to prevent the board UI from being out of
           sync with the board Data Types.
           @param index The current index of the 64 spaces to move to.
         */
	public void moveTo(int index)
        {
                // disable extra jump mode after movement
		this.extraJumpMode = false;

                // calculate targetSquare and create the new "move" in
                // board Data Types.
		CheckerSquare targetSquare = this.state.getSquare(index);
		CheckerMove move = new CheckerMove(this.selectedSquare, targetSquare);


		// actually modify the board
		this.state.executeMove(move);


                // tell board to redraw everything
                this.redrawAll();



		// handle extra jumps
		List<CheckerMove> extraJumps = this.state.getValidDoubleJumps(move.getEnd());

                // check if the last move was a double jump and that
                // there is at least one available additional double
                // jump to use.
		if (move.isDoubleJump() && extraJumps.size() > 0)
                {
                        // lock user out of other moves
			this.extraJumpMode = true;

                        // find the piece that was last moved
			for (GamePiece piece : this.drawnPieces)
                        {
				if (piece != null && piece.getIndex() == move.getEnd().getIndex())
                                {
                                        // use end index because the
                                        // board data type has already
                                        // executed the move.
					piece.select();
                                        // select the piece
				}
			}

                        // unhighlight everything
                        for (CheckerBoardSpace space : this.boardSpaces)
                        {
                                if (space != null)
                                { // make sure it isn't null
                                        space.dehighlight(); // un highlight it
                                }
                        }

                        // highlight each of the extra jumps
			for (CheckerMove move2 : extraJumps)
                        {
				this.boardSpaces[move2.getEnd().getIndex()].highlight();
			}
		}

                else
                {
                        // switch off to the other player
			this.switchPlayer();
		}



                // set the GamePiece to King when appropriate
                // note: at this point the activePlayer member variable has already been switched
                if( (this.activePlayer == Player.TWO && index > 55) || (this.activePlayer == Player.ONE && index < 8) )
                {
                        for (GamePiece piece : this.drawnPieces)
                        {
                                // find the correct piece
                                if (piece != null && piece.getIndex() == index)
                                {
                                        piece.setToKing();
                                }
                        }
                }


	}







        /**
           Enable the other player to move a piece. This is called
           immediately after the other player has finished with one
           move.
         */
	public void switchPlayer()
        {

		if (this.activePlayer == Player.ONE)
                {
			this.activePlayer = Player.TWO;
                        this.frame.setTitle("CheckerBoard: (Player Two's Turn)");
		}

                else if (this.activePlayer == Player.TWO)
                {
			this.activePlayer = Player.ONE;
                        this.frame.setTitle("CheckerBoard: (Player One's Turn)");
		}
	}




        /**
           Get the currently active player who has control of the
           board.
        */
	public Player getActivePlayer()
        {
		return this.activePlayer;
	}







        /**
           Initialize the checker board as a main program.
         */
        public static void main(String[] args)
        {
                CheckerBoard testBoard = new CheckerBoard();
        }




}
