package com.groupone.p2pgame;

/**
   CheckerMove stores an arbitrary game move that may or may not be
   valid. Each CheckerMove is made up of a start and end square
   position.
 */
public class CheckerMove
{
    private CheckerSquare start;
    private CheckerSquare end;




    /**
       Initialize a new CheckerMove.
       @param start The place where a piece was originally.
       @param end The place where a piece will end up.
     */
    public CheckerMove (CheckerSquare start, CheckerSquare end)
    {
	this.start = start;
	this.end = end;
    }




    /**
       Is the move a double jump? That is does the piece move two
       spaces diagonally. (Note: does not check if there is anything
       between to make it a valid move; this is done in
       CheckerBoardState).
       @see CheckerBoardState
       @return The answer to the above question.
    */
    public boolean isDoubleJump()
    {
	// get change in x and y
	int dx = Math.abs(this.getEnd().getX() - getStart().getX());
	int dy = Math.abs(this.getEnd().getY() - getStart().getY());

	// make sure both equal 2
	return dx == 2 && dy == 2;
    }




    /**
       Is the move a single jump? That is does the piece move one space diagonally. (Note: only checks for locations, no other checking is done.)
       @see CheckerBoardState
       @return The answer to the above question.
    */
    public boolean isSingleJump()
    {
	// get change in x and y
	int dx = Math.abs(this.getEnd().getX() - getStart().getX());
	int dy = Math.abs(this.getEnd().getY() - getStart().getY());

	// make sure both equal 1
	return dx == 1 && dy == 1;
    }





    /**
       Get the start square of the move.
       @return The start square.
    */
    public CheckerSquare getStart ()
    {
	return this.start;
    }

    /**
       Get the end square of the move.
       @return The end square.
    */
    public CheckerSquare getEnd ()
    {
	return this.end;
    }





    /**
       Convert the move into a human readable string. Used in debugging.
       @return The move in string form, with coords.
     */
    @Override
    public String toString()
    {
        return "From " + this.getStart().getX() + ", " + this.getStart().getY() + " to " + this.getEnd().getX() + ", " + this.getEnd().getY();
    }

    
}
