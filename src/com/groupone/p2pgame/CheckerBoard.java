package com.groupone.p2pgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

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

        private JPanel buttonsPanel;
        private JButton cancelExtraJump;

        private CheckerBoardSpace[] boardSpaces;
        private GamePiece[] drawnPieces;

                // array of the player pieces
        private int[] playerPiecesLeft;

        private List<CheckerBoardState> states;

        private CheckerSquare selectedSquare;

        public JLabel whoseTurn;

        private Client client;
        private GameDriver gameDriver;

        // The player who owns the board
        private Player boardOwner;
        private Player boardOpponent;
        private int activePlayerInt;


        private String frameTitle;
        private String playerColor; // for clarification of the title display


        private boolean isGameOver;






        /**
           Setup a checkerboard from an "CheckerBoardState".
           @see CheckerBoardState
           @param client The client to send messages to.
        */
        public CheckerBoard(Client client, GameDriver gameDriver)
        {

                super();

                this.client = client;
                this.gameDriver = gameDriver;


                this.boardOwner = this.client.getPlayer();
                if(this.boardOwner == Player.TWO)
                {
                        this.playerColor = "Gray";
                        this.boardOpponent = Player.ONE;
                }
                else
                {
                        this.playerColor = "Blue";
                        this.boardOpponent = Player.TWO;
                }
                this.activePlayerInt = 2; // Player Two will have the first move




                this.states = new ArrayList<CheckerBoardState>();
                this.states.add(this.client.getInitialState());

                /****************************************************************/

                this.frame = new JFrame(); // for the first initializeSettings call
                this.initializeSettings();

                this.playerPiecesLeft = new int[3];
                playerPiecesLeft[0] = 0; // represents Player.NONE
                playerPiecesLeft[1] = 12; // represents Player.ONE
                playerPiecesLeft[2] = 12; // represents Player.TWO



                // draws the board at its initial state
                this.redrawAll();

                final CheckerBoard thisBoard = this;

                Thread loop = new Thread() {
                                public void run() {
                                        while (true) {
                                                // get the next move from the other side
                                                try {

                                                        CheckerBoardState state = thisBoard.client.waitForMove();
                                                        thisBoard.states.add(state);

                                                        thisBoard.redrawAll();

                                                        thisBoard.endGame();
                                                } catch (Exception e) {
                                                        System.out.println("Fatal error: " + e);
                                                }

                                        }
                                }
                        };

                loop.start();

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
                this.frame.setSize(645, 700);

                // prevent from resizing
                this.frame.setResizable(false);



                if(this.boardOwner == Player.TWO)
                {
                        this.frameTitle = "Begin Game (You have the first move.)";
                }
                else
                {
                        this.frameTitle = "Begin Game (Your opponent has the first move.)";
                }

                this.frame.setTitle(this.frameTitle);
                this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



                // layered so we can also use the gameBoardWithPieces
                // on top of it
                this.gameBoard = new JLayeredPane();
                this.gameBoard = frame.getLayeredPane();
                this.gameBoard.setPreferredSize(new Dimension(640, 640));


                this.gameBoardWithPieces = new Container();
                this.gameBoardWithPieces.setSize(640, 640);

                // prevent from resizing
                //this.gameBoardWithPieces.setMaximumSize( new Dimension(640, 640) );

                // 8x8 for checkboard
                this.gameBoardWithPieces.setLayout( new GridLayout(8,8) );

                // set black as default background
                this.gameBoardWithPieces.setBackground(Color.BLACK);


                this.buttonsPanel = new JPanel();
                buttonsPanel.setLayout( new BorderLayout(1,1) );

                this.cancelExtraJump = new JButton("Cancel Extra Jump");
                this.cancelExtraJump.addActionListener(cancelExtraJumpListener());

                buttonsPanel.add(cancelExtraJump);
                this.frame.getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);
                buttonsPanel.setVisible(false);

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
                //this.gameBoardWithPieces.setMaximumSize( new Dimension(640, 640) );
                this.gameBoardWithPieces.setLayout( new GridLayout(8,8) );
                this.gameBoardWithPieces.setBackground(Color.BLACK);

                // re init even more stuff
                boardSpaces = new CheckerBoardSpace[64];
                drawnPieces = new GamePiece[24]; // all of player one's pieces will

                // re init more stuff
                this.buttonsPanel = new JPanel();
                buttonsPanel.setLayout( new BorderLayout(1,1) );

                this.cancelExtraJump = new JButton("Cancel Extra Jump");
                this.cancelExtraJump.addActionListener(cancelExtraJumpListener());

                buttonsPanel.add(cancelExtraJump);
                this.frame.getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);
                buttonsPanel.setVisible(false);


                // redraw background
                this.drawGameBackground();

                this.drawGameBoard(this.getState()); // draws the board at its new state

                this.updateBoardTitle();

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

                this.makeGamePieces(state); // adds the game pieces to the board


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
           Recreates the GamePieces that are remaining on the board and resets the playerPiecesLeft counts
        */
        private void makeGamePieces(CheckerBoardState state)
        {

                        // resets the playerPiecesLeft indices
                this.playerPiecesLeft[1] = 0;
                this.playerPiecesLeft[2] = 0;


                // creates the 24 game pieces --> player one's pieces are the first 12, and player two's pieces are second
                int n = 0;
                for( CheckerSquare square : state.getSquares())
                {

                        if (square.getPiece().getType() != PieceType.EMPTY)
                        {

                                if (square.getPiece().getPlayer() == Player.ONE)
                                {
                                        drawnPieces[n++] = new GamePiece(this, Color.BLUE, square.getIndex(), square.getPiece());
                                        this.playerPiecesLeft[1]++;
                                }

                                else if (square.getPiece().getPlayer() == Player.TWO)
                                {
                                        drawnPieces[n++] = new GamePiece(this, Color.LIGHT_GRAY, square.getIndex(), square.getPiece());
                                        this.playerPiecesLeft[2]++;
                                }


                        }

                }

        }






        /**
           Clear the highlighted pieces of the board. This method
           should restore the board to how it was initially. All blue
           spaces and golden piece borders are removed.
        */
        public void clearHighlights()
        {

                // disregard in extra jump mode so we don't cheat
                if (this.getState().isInExtraJumpMode())
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
                // get valid moves from CheckerBoardState
                List<CheckerMove> moves = this.getState().getValidMoves(square);

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
                CheckerSquare square = this.getState().getSquare(index);

                // only let active player select
                if (square.getPiece().getPlayer() == this.getState().getActivePlayer())
                {
                        this.selectedSquare = square;

                        // auto show allowed moves
                        this.showAllowedMoves(this.selectedSquare);
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
                this.getState().setExtraJumpMode(false);

                // make sure the cancelExtraJump button is not visible
                this.buttonsPanel.setVisible(false);


                // calculate targetSquare and create the new "move" in
                // board Data Types.
                CheckerSquare targetSquare = this.getState().getSquare(index);
                CheckerMove move = new CheckerMove(this.selectedSquare, targetSquare);


                // actually modify the board
                CheckerBoardState newState = new CheckerBoardState(this.getState());
                newState.executeMove(move);
                this.states.add(newState);


                // tell board to redraw everything
                this.redrawAll();


                // handle the case when an opposing player loses a piece
                if( move.isDoubleJump() )
                {
                        this.pieceRemovedDuringTurn();
                }


                // handle extra jumps
                List<CheckerMove> extraJumps = this.getState().getValidDoubleJumps(move.getEnd());



                // check if the last move was a double jump and that
                // there is at least one available additional double
                // jump to use.
                if (move.isDoubleJump() && extraJumps.size() > 0)
                {
                        // make the cancelExtraJump button visible
                        this.buttonsPanel.setVisible(true);

                        this.executeExtraJump(move, extraJumps);

                } else {

                    // switch off to the other player
                    this.switchPlayer();

                    this.redrawAll();


                    // send info to other client
                    try
                    {
                          this.client.sendBoard(this.getState());
                    } catch (Exception e)
                    {
                          System.out.println("Fatal error: " + e);
                    }


                }



        }


        /*
          Setup the extra jump moves.
          @param move The last move made.
          @param extraJumps Possible extra jumps to use.
         */
        public void executeExtraJump(CheckerMove move, List<CheckerMove> extraJumps)
        {
                // lock user out of other moves
                this.getState().setExtraJumpMode(true);


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




        /**
           Button listener for the cancelExtraJump button
        */
        public ActionListener cancelExtraJumpListener()
        {

                final CheckerBoard thisBoard = this;

                ActionListener listener = new ActionListener()
                {

                        public void actionPerformed(ActionEvent e)
                        {

                                // remove button from sight when it is pressed
                                thisBoard.buttonsPanel.setVisible(false);

                                // when cancelExtraJump button is pressed
                                thisBoard.getState().setExtraJumpMode(false);


                                // deselect all pieces
                                for (GamePiece piece : thisBoard.drawnPieces)
                                {
                                        if (piece != null)
                                        {
                                                // use end index because the
                                                // board data type has already
                                                // executed the move.
                                                piece.deselect();
                                                // select the piece
                                        }
                                }

                                // unhighlight everything
                                for (CheckerBoardSpace space : thisBoard.boardSpaces)
                                {
                                        if (space != null)
                                        { // make sure it isn't null
                                                space.dehighlight(); // un highlight it
                                        }
                                }

                                thisBoard.switchPlayer();

                                try
                                {
                                      thisBoard.client.sendBoard(thisBoard.getState());
                                }
                                catch (Exception exc)
                                {
                                      System.out.println("Fatal error: " + exc);
                                }
                        }
                };

                return listener;

        }





        /**
           The board acknowledges that at least one of the opposing player's
           pieces was removed during a double jump.
        */
        public void pieceRemovedDuringTurn()
        {

                if(this.getState().getActivePlayer() == Player.TWO)
                {
                        this.playerPiecesLeft[1]--;
                }
                else
                {
                        this.playerPiecesLeft[2]--;
                }

        }




        /**
           Enable the other player to move a piece. This is called
           immediately after the other player has finished with one
           move.
        */
        public void switchPlayer()
        {

                // ordinal returns "1" for Player.ONE and "2" for Player.TWO; zero otherwise

                if (this.getState().getActivePlayer() == this.boardOwner && this.playerPiecesLeft[this.boardOwner.ordinal()] > 0 )
                {
                        this.getState().setActivePlayer(this.boardOpponent);
                }

                else if (this.playerPiecesLeft[this.boardOpponent.ordinal()] > 0 )
                {
                        this.getState().setActivePlayer(this.boardOwner);
                }

                else
                {
                        this.getState().setActivePlayer(Player.NONE);
                }

        }


        /**
           Undo a previous move.
         */
        public void undo()
        {
                this.states.remove(this.states.size() - 1);
        }






        /**
           Get the state of the checker board.
           @return the state
        */
        public CheckerBoardState getState() {
              // just use the last state
              return this.states.get(this.states.size() - 1);
        }


        /**
           Get the owner of this board.
           @return the board owner
        */
        public Player getBoardOwner() {
                return this.boardOwner;
        }




        /**
                Update the board's title to acknowledge each player's move
        */
        public void updateBoardTitle()
        {

                // ordinal returns "1" for Player.ONE and "2" for Player.TWO; zero otherwise

                if (this.getState().getActivePlayer() == this.boardOwner && this.playerPiecesLeft[this.boardOwner.ordinal()] > 0 )
                {
                        this.frame.setTitle("Your Turn (" + this.playerColor + ") (Your Pieces Left: " + this.playerPiecesLeft[this.boardOwner.ordinal()] + ")");
                }

                else if( this.getState().getActivePlayer() == this.boardOpponent && this.playerPiecesLeft[this.boardOpponent.ordinal()] > 0 )
                {
                        this.frame.setTitle("Opponent's Turn (Your Pieces Left: " + this.playerPiecesLeft[this.boardOwner.ordinal()] + ")");
                }

                else // the game is over
                {

                        if( this.playerPiecesLeft[ this.boardOwner.ordinal() ] == 0 )
                        {
                                this.frame.setTitle("You Lose!");
                        }
                        else
                        {
                                this.frame.setTitle("You Win!");
                        }
                        this.isGameOver = true;
                        this.endGame();

                }

        }







        /**
           Check if one player has won, and close the game windows if so.
        */
        public void endGame()
        {

                if( this.isGameOver )
                {
                                // will close both checkerboards
                        this.frame.dispose();


                        String endText;

                                // ensures that the ending screen will only be opened once
                        if(this.boardOwner == Player.ONE)
                        {

                                if(this.playerPiecesLeft[1] == 0)
                                {
                                        endText = "Player Two Wins!";
                                }
                                else
                                {
                                        endText = "Player One Wins!";
                                }

                                this.gameDriver.getFrame().dispose();
                                this.gameDriver.gameEndingScreen(endText); //returns control back to the GameDriver


                        }
                }

        }



}
