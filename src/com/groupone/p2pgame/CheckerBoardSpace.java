package com.groupone.p2pgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
   CheckerBoardSpace holds the graphical elements of a "space" in a
   checker board. It is simply represented here as a box. It also must
   listen for events from the mouse and hand those back to the board
   UI.
 */
public class CheckerBoardSpace extends JPanel implements MouseListener
{
        private Color spaceColor; // red or black
	private Color oldSpaceColor;
	private CheckerBoard board;
	private boolean isHighlighted;
	private int index;

	/**
	   Create a new CheckerBoardSpace with a given, board, color, and index
	   @param board This is used to tell the board to execute a
	   move once the space is clicked while highlighted.
	   @param color If even, then red. If odd, then black.
	   (creates the checkered pattern)
	   @param index The index of the space in the 0 to 63 range
	   used by the board UI.
	 */
	public CheckerBoardSpace(CheckerBoard board, int color, int index)
        {
		super();

		// listen to mouse event
		this.addMouseListener(this);

		// init local variables
		this.board = board;
		this.index = index;

		// default to unhighlighted
		this.isHighlighted = false;

		// set color based on the in passed in
                if(color % 2 == 0)
                {
                        this.spaceColor = Color.RED;
                }
                else
                {
                        this.spaceColor = Color.BLACK;
                }
        }


	/**
	   Paints the rectangle when swing is ready for it.
	   @param graphics The swing graphics objects used to paint.
	*/
	@Override
        public void paintComponent(Graphics graphics)
        {
                super.paintComponent(graphics);
                graphics.setColor(this.spaceColor);
                graphics.drawRect(0, 0, 80, 80);
                graphics.fillRect(0, 0, 80, 80);
        }

	/**
	   Set the color of the square. Causes a repaint.
	   @param spaceColor The color to set the square to.
	*/
	public void setColor(Color spaceColor)
        {
		this.spaceColor = spaceColor;
		this.repaint();
	}

	/**
	   Get the current space color.
	   @param The color inside this square.
	*/
	public Color getSpaceColor()
        {
		return this.spaceColor;
	}

	/**
	   Highlight the square, indicating it can be clicked. Sets
	   the color to Swing's Blue.
	*/
	public void highlight()
        {
		this.isHighlighted = true;
		this.oldSpaceColor = spaceColor;
		this.setColor(Color.BLUE);
	}

	/**
	   Unhighlight the square. Goes back to the color before
	   highlight was called.
	*/
	public void dehighlight()
        {
		this.isHighlighted = false;
		if (this.oldSpaceColor != null)
                {
			this.setColor(oldSpaceColor);
		}
	}

	/**
	   Called when the mouse is pressed. Performs a move if the
	   square was already highlighted.
	*/
	@Override
        public void mousePressed(MouseEvent e)
        {
                if (this.isHighlighted)
                {
			this.board.moveTo(this.index);
		}
        }




	@Override
        public void mouseReleased(MouseEvent e) {
        }

	@Override
        public void mouseEntered(MouseEvent e) {
        }

	@Override
        public void mouseExited(MouseEvent e) {
        }

	@Override
        public void mouseClicked(MouseEvent e) {
        }



}
