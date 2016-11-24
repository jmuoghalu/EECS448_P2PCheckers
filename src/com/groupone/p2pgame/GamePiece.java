package com.groupone.p2pgame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.awt.event.*;

/**
   A game piece UI element that is represented on-screen as an oval.
*/
public class GamePiece extends JPanel implements MouseListener
{


        private Color pieceColor;
        private boolean isPressed;
        private int gameBoardIndex;
        private Player player;
        private boolean kingMe;
        private String kingString;
        private CheckerBoard board;

        /**
           Create a new game piece.
           @param board The board that created this GamePiece, needed
           to send back details once it has been selected.
           @param color The color that this game piece should be.
           @param index The index that this piece occupies.
           @param player The player that owns this piece.
        */
        public GamePiece(CheckerBoard board, Color color, int index, Player player)
        {
                this.addMouseListener(this); // setup mouse listener
                this.pieceColor = color;
		this.board = board;
                this.gameBoardIndex = index;
                this.player = player;

                this.isPressed = false;

                this.kingMe = false;
                this.kingString = "K";
        }




        /**
           Update the graphics of the oval.
           @param graphics The graphics to draw to.
         */
	@Override
        public void paintComponent(Graphics graphics)
        {
                // make sure we let others do stuff
                super.paintComponent(graphics);

                // check if we should draw the yellow halo around it
                // this must be done first
                if (isPressed)
                {
                        graphics.setColor(Color.YELLOW);
                        graphics.drawOval(8, 8, 64, 64);
                        graphics.fillOval(8, 8, 64, 64);
                }

                if(this.kingMe)
                {
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(kingString, 30 , 30);
                }


                // draw the ordinary piece
                graphics.setColor(this.pieceColor);
                graphics.drawOval(10, 10, 60, 60);
                graphics.fillOval(10, 10, 60, 60);


        }




        /**
           Select the game piece, show the yellow halo if we are an
           active player.
         */
        public void select()
        {
                // check if the board's active player is us
                if (this.board.getActivePlayer() == this.player)
                {
                        // setup the halo
                        isPressed=true;
                        repaint(); // make sure we repaint

                        // tell the board we are chosen
                        this.board.setSelected(gameBoardIndex);
                }
        }




        /**
           Deselect the game piece, remove the yellow halo.
        */
        public void deselect()
        {
                isPressed = false; // get rid of yellow halo
                repaint(); // make sure we repaint
        }




        /**
           Get the index of the piece in the game board.
         */
        public int getIndex()
        {
                return this.gameBoardIndex;
        }


        /**
           The piece in question is now designated as a king.
         */
        public void setKing()
        {
                this.kingMe = true;
        }












        /**
           Clear the selected pieces on the board, then Select this
           piece.
        */
	@Override
        public void mousePressed(MouseEvent e)
        {
                if (!this.board.isInExtraJumpMode())
                {
                        this.board.clearHighlights();
                        CheckerSquare square = this.board.state.getSquare(gameBoardIndex);

                        if(this.board.state.getValidMoves(square).size() != 0)
                        {
                                select();
                        }
                }
        }




	@Override
        public void mouseReleased(MouseEvent e) {
        }




	@Override
        public void mouseEntered(MouseEvent e)
        {


          CheckerSquare square = this.board.state.getSquare(gameBoardIndex);
          if(this.board.state.getValidMoves(square).size() != 0)
          {
                  isPressed=true;
                  repaint();
          }


        }




	@Override
        public void mouseExited(MouseEvent e)
        {
          CheckerSquare square = this.board.state.getSquare(gameBoardIndex);
          if(this.board.state.getValidMoves(square).size() != 0)
          {

                  isPressed=false;
                  repaint();
          }
        }

	@Override
        public void mouseClicked(MouseEvent e) {
        }




}
