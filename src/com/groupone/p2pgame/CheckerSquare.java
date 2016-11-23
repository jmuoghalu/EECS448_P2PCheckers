package com.groupone.p2pgame;

/**
   CheckerSquare holds the data of one square in a checkers game.
*/
public class CheckerSquare
{
    private int index;
    private Piece piece;



    /**
       Create a new checkers square at give index.
       @param index The 0-63 number that all squares are assigned
       based on the left to right top down order.
    */
    public CheckerSquare (int index)
    {
	this.index = index;
	this.piece = new Piece();
        }




    /**
       Calculate the x coordinate of this square.
       @return The x coordinate.
    */
    public int getX ()
    {
	// use modulo to wrap around for each (column-wise)
	return this.index % 8;
    }




    /**
       Calculate the y coordinate of this square.
       @return The y coordinate.
    */
    public int getY ()
    {
	// get which row we are in
	return this.index / 8;
    }




    /**
       Get the index of square on the checkers board.
       @return The index, a number from 0 to 63.
    */
    public int getIndex ()
    {
	return this.index;
    }




    /**
       Get the piece contained within this square.
       @return The piece that happens to be in that square.
    */
    public Piece getPiece ()
    {
	return this.piece;
    }




    /**
       Set the piece of the square, forgetting the old one.
    */
    public void setPiece(Piece piece)
    {
	this.piece = piece;
    }

    
}
