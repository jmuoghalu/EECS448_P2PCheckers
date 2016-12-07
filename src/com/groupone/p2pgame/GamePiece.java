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
        private Color kingColor;
        private boolean isPressed;
        private int gameBoardIndex;
        private String kingString;
        private CheckerBoard board;
        private Piece piece;

        /**
           Create a new game piece.
           @param board The board that created this GamePiece, needed
           to send back details once it has been selected.
           @param color The color that this game piece should be.
           @param index The index that this piece occupies.
           @param piece The piece containing the player and type
        */
        public GamePiece(CheckerBoard board, Color color, int index, Piece piece)
        {
                this.addMouseListener(this); // setup mouse listener
                this.pieceColor = color;
                this.board = board;
                this.gameBoardIndex = index;
                this.piece = piece;

                this.isPressed = false;

                this.kingString = "K";

                if(this.pieceColor == Color.BLUE)
                {
                        this.kingColor = Color.CYAN;
                }
                else
                {
                        this.kingColor = Color.DARK_GRAY;
                }
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

                if(this.piece.getType() == PieceType.KING)
                {
                        // draw the king piece
                        graphics.setColor(this.kingColor);
                        graphics.drawOval(10, 10, 60, 60);
                        graphics.fillOval(10, 10, 60, 60);

                        // from: http://stackoverflow.com/questions/18249592/how-to-change-font-size-in-drawstring-java
                        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 36));
                        graphics.setColor(Color.BLACK);
                        graphics.drawString(kingString, 30 , 50);

                }
                else
                {
                        // draw the ordinary piece
                        graphics.setColor(this.pieceColor);
                        graphics.drawOval(10, 10, 60, 60);
                        graphics.fillOval(10, 10, 60, 60);
                }



        }




        /**
           Select the game piece, show the yellow halo if we are an
           active player.
         */
        public void select()
        {
                // check if the board's active player is us
                if (this.board.getState().getActivePlayer() == this.getPlayer()
                    && this.board.getBoardOwner() == this.board.getState().getActivePlayer())
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
          Get the player who controls this piece.
         */
        public Player getPlayer()
        {
                return this.piece.getPlayer();
        }












        /**
           Clear the selected pieces on the board, then Select this
           piece.
        */
	@Override
        public void mousePressed(MouseEvent e)
        {
                if (!this.board.getState().isInExtraJumpMode())
                {
                        this.board.clearHighlights();
                        CheckerSquare square = this.board.getState().getSquare(gameBoardIndex);

                        if(this.board.getState().getValidMoves(square).size() != 0)
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


          CheckerSquare square = this.board.getState().getSquare(gameBoardIndex);
          if(this.board.getState().getValidMoves(square).size() > 0
             && this.board.getState().getActivePlayer() == square.getPiece().getPlayer()
             && this.board.getBoardOwner() == this.board.getState().getActivePlayer())
          {
                  isPressed=true;
                  repaint();
          }


        }




	@Override
        public void mouseExited(MouseEvent e)
        {
          CheckerSquare square = this.board.getState().getSquare(gameBoardIndex);
          isPressed=false;
          repaint();
        }

	@Override
        public void mouseClicked(MouseEvent e) {
        }




}
