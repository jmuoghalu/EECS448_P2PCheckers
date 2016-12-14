import com.groupone.p2pgame.*;
import java.util.Arrays;


public class CheckerBoardStateTest {



      /**
         Checks if creating a new board will create a board with out any
         peices.
         @return returns whether or not the test succeeded"
      */
    public static boolean test1() {
        CheckerBoardState board = new CheckerBoardState();
        for (CheckerSquare square : board.getSquares()) {
            if (square.getPiece().getType() != PieceType.EMPTY) {
                return false;
            }
        }
        return true;
    }
    /**
       Checks if the pieces for player one are in their starting positions
       at the beginning of a game.
       @return returns whether or not the test succeeded"
    */
    public static boolean test2() {
        CheckerBoardState board = CheckerBoardState.getStartingBoard();
        int i = 0;
        CheckerSquare testPosition[] = board.getSquares();
        boolean check = true;
        while(i<64 && check== true)
        {
        if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12 || i==14 || i==17 || i==19 || i==21 || i==23) //check player ONE starting position
        {
            if (testPosition[i].getPiece().getPlayer() != Player.ONE) {
                check = false;
            }
          }
          i++;
          }
        return check;
    }

    /**
       Checks if the pieces for player two are in their starting positions
       at the beginning of a game.
       @return returns whether or not the test succeeded"
    */
    public static boolean test3() {
        CheckerBoardState board = CheckerBoardState.getStartingBoard();
        int i = 0;
        CheckerSquare testPosition[] = board.getSquares();
        boolean check = true;
        while(i<64 && check== true)
        {
        if(i==40 || i==42 || i==44 || i==46 || i==49 || i==51 || i==53 || i==55 || i==56 || i==58 || i==60 || i==62) //check player ONE starting position
        {
            if (testPosition[i].getPiece().getPlayer() != Player.TWO) {
                check = false;
            }
          }
          i++;
          }
        return check;
    }

    /**
       Checks that spaces that should not have pieces on them at the beggining
       of the game are indeed empty.
       @return returns whether or not the test succeeded"
    */
    public static boolean test4() {
        CheckerBoardState board = CheckerBoardState.getStartingBoard();
        int i = 0;
        CheckerSquare testPosition[] = board.getSquares();
        boolean check = false;
        while(i<64 && check== false)
        {
        if(i==0 || i==2 || i==4 || i==6 || i==9 || i==11 || i==13 || i==15 || i==16 || i==18 || i==20 || i==22 ||
        i==24 || i==25 || i==26 || i==27 || i==28 || i==29 || i==30 || i==31|| i==32|| i==33|| i==34|| i==35|| i==36|| i==37|| i==38|| i==39 ||
        i==41 || i==43 || i==45 || i==47 || i==48 || i==50 || i==52 || i==54 || i==57 || i==59 || i==61 || i==63)
        {
            if (testPosition[i].getPiece().getPlayer() == Player.NONE) {
                check = true;
            }
          }
          i++;
          }
        return check;
    }
    /**
       Checks whether or not a pawn is able to be added to an already
       existing board.
       @return returns whether or not the test succeeded"
    */
    public static boolean test5() {
        CheckerBoardState board = CheckerBoardState.getStartingBoard();
        Piece p1 = new Piece(PieceType.PAWN, Player.ONE);
        int i = 0;
        CheckerSquare testPosition[] = board.getSquares();
        boolean check = false;
        testPosition[0].setPiece(p1);
      //  System.out.println(testPosition[0].getPiece().getPlayer());

        if(testPosition[i].getPiece().getType() == PieceType.PAWN)
        {
          check=true;
        }

        return check;
    }
    /**
       Checks whether or not a king is able to be added to an already
       existing board.
       @return returns whether or not the test succeeded"
    */
        public static boolean test6() {
            CheckerBoardState board = CheckerBoardState.getStartingBoard();
            Piece p1 = new Piece(PieceType.KING, Player.ONE);
            int i = 0;
            CheckerSquare testPosition[] = board.getSquares();
            boolean check = false;
            testPosition[0].setPiece(p1);
          //  System.out.println(testPosition[0].getPiece().getPlayer());

            if(testPosition[i].getPiece().getType() == PieceType.KING)
            {
              check=true;
            }

            return check;
        }
        /**
           Checks whether or not a king is able to be removed from the board.
           @return returns whether or not the test succeeded"
        */
        public static boolean test7() {
            CheckerBoardState board = CheckerBoardState.getStartingBoard();
            Piece p1 = new Piece(PieceType.KING, Player.ONE);
            Piece empty = new Piece(PieceType.EMPTY, Player.NONE);
            int i = 0;
            CheckerSquare testPosition[] = board.getSquares();
            boolean check = false;
            boolean notEmpty = false;
            testPosition[0].setPiece(p1);

            if(testPosition[i].getPiece().getPlayer() == Player.ONE)
            {
              notEmpty=true;
            }
            testPosition[0].setPiece(empty);

            if(testPosition[i].getPiece().getPlayer() == Player.NONE &&  testPosition[i].getPiece().getType() == PieceType.EMPTY && notEmpty)
            {
              notEmpty=false;
              check=true;
            }
            return check;
        }

        /**
           Checks whether or not a pawn is able to be removed from the board.
           @return returns whether or not the test succeeded"
        */
        public static boolean test8() {
            CheckerBoardState board = CheckerBoardState.getStartingBoard();
            Piece p1 = new Piece(PieceType.PAWN, Player.ONE);
            Piece empty = new Piece(PieceType.EMPTY, Player.NONE);
            int i = 0;
            CheckerSquare testPosition[] = board.getSquares();
            boolean check = false;
            boolean notEmpty = false;
            testPosition[0].setPiece(p1);

            if(testPosition[i].getPiece().getPlayer() == Player.ONE)
            {
              notEmpty=true;
            }
            testPosition[0].setPiece(empty);

            if(testPosition[i].getPiece().getPlayer() == Player.NONE &&  testPosition[i].getPiece().getType() == PieceType.EMPTY && notEmpty)
            {
              notEmpty=false;
              check=true;
            }
            return check;
        }

        /**
           Checks if player two's pieces are able to be moved on the board.
           @return returns whether or not the test succeeded"
        */
        public static boolean test9() {
          CheckerBoardState board = CheckerBoardState.getStartingBoard();
          CheckerSquare original = board.getSquare(40);
          CheckerSquare moved =board.getSquare(33);
          CheckerMove testMove = new CheckerMove(original,moved);
          board.executeMove(testMove);
          CheckerSquare testPosition[] = board.getSquares();
          boolean check = false;
        //  System.out.println(testPosition[33].getPiece().getPlayer());
          if(testPosition[33].getPiece().getPlayer() == Player.TWO )
          {
            check = true;
          }
          return check;
        }

        /**
           Checks if player one's pieces are able to be moved on the board.
           @return returns whether or not the test succeeded"
        */
        public static boolean test10() {
          CheckerBoardState board = CheckerBoardState.getStartingBoard();
          CheckerSquare original = board.getSquare(19);
          CheckerSquare moved =board.getSquare(27);
          CheckerMove testMove = new CheckerMove(original,moved);
          board.executeMove(testMove);
          CheckerSquare testPosition[] = board.getSquares();
          boolean check = false;
        //  System.out.println(testPosition[33].getPiece().getPlayer());
          if(testPosition[27].getPiece().getPlayer() == Player.ONE )
          {
            check = true;
          }
          return check;
        }
        /**
           Checks if the isDoubleJump method returns true if a double jump
           is possible
           @return returns whether or not the test succeeded"
        */
        public static boolean test11() {
          boolean check = false;
          CheckerBoardState board = CheckerBoardState.getStartingBoard();


          CheckerSquare testPosition[] = board.getSquares();
          Piece empty = new Piece(PieceType.EMPTY, Player.NONE);

          testPosition[14].setPiece(empty);

          Piece p2 = new Piece(PieceType.PAWN, Player.TWO);
          testPosition[35].setPiece(p2);
          CheckerMove doublejump = new CheckerMove(board.getSquare(42),board.getSquare(28));

        //  System.out.println(testPosition[33].getPiece().getPlayer());
          if(doublejump.isDoubleJump())
          {
            check = true;
          }
          return check;
        }

        /**
           Checks if the isSingleJump method returns true if a single jump
           is possible
           @return returns whether or not the test succeeded"
        */
        public static boolean test12() {
          boolean check = false;
          CheckerBoardState board = CheckerBoardState.getStartingBoard();


          CheckerSquare testPosition[] = board.getSquares();
          Piece empty = new Piece(PieceType.EMPTY, Player.NONE);

          testPosition[14].setPiece(empty);

          Piece p2 = new Piece(PieceType.PAWN, Player.TWO);
          testPosition[35].setPiece(p2);
          CheckerMove doublejump = new CheckerMove(board.getSquare(42),board.getSquare(35));

        //  System.out.println(testPosition[33].getPiece().getPlayer());
          if(doublejump.isSingleJump())
          {
            check = true;
          }
          return check;
        }

        /**
           Checks if the addPieceAtIndex method adds a piece at a specified
           index.
           @return returns whether or not the test succeeded"
        */
        public static boolean test13() {
            boolean check = false;
            CheckerBoardState board = CheckerBoardState.getStartingBoard();
            Piece p1 = new Piece(PieceType.PAWN, Player.ONE);
            board.addPieceAtIndex(p1,35);
            CheckerSquare testPosition[] = board.getSquares();

            testPosition[0].setPiece(p1);
          //  System.out.println(testPosition[0].getPiece().getPlayer());

            if(testPosition[35].getPiece().getType() == PieceType.PAWN && testPosition[35].getPiece().getPlayer() == Player.ONE)
            {
              check=true;
            }

            return check;
        }

        /**
           Checks if getPlayerOnePieceLocationsInts returns the correct
           index of player one's current pieces.
           @return returns whether or not the test succeeded"
        */
        public static boolean test14() {
            boolean check = false;
            CheckerBoardState board = CheckerBoardState.getStartingBoard();
            int[] test = board.getPlayerOnePieceLocationsInts();
            int[] playerOneLocations = new int[] {1, 3, 5, 7, 8, 10, 12, 14, 17, 19, 21, 23};
            CheckerSquare testPosition[] = board.getSquares();
            if(Arrays.equals(test,playerOneLocations))
            {
              check=true;
            }
            return check;
        }

        /**
           Checks if getPlayerTwoPieceLocationsInts returns the correct
           index of player two's current pieces.
           @return returns whether or not the test succeeded"
        */
        public static boolean test15() {
            boolean check = false;
            CheckerBoardState board = CheckerBoardState.getStartingBoard();
            int[] test = board.getPlayerTwoPieceLocationsInts();
            int[] playerTwoLocations = new int[] {40, 42, 44, 46, 49, 51, 53, 55, 56, 58, 60, 62};
            CheckerSquare testPosition[] = board.getSquares();
            if(Arrays.equals(test,playerTwoLocations))
            {
              check=true;
            }
            return check;
        }

        /**
           Checks if isValidMove returns true when a move is valid.
           @return returns whether or not the test succeeded"
        */
        public static boolean test16() {
            boolean check = false;
            CheckerBoardState board = CheckerBoardState.getStartingBoard();
            CheckerMove validMove = new CheckerMove(board.getSquare(21),board.getSquare(28));
            CheckerSquare testPosition[] = board.getSquares();

            if(board.isValidMove(validMove))
            {
              check=true;
            }
            return check;
        }


    public static void main(String args[]) {
        System.out.println("starting tests...");
        if (test1()) {
            System.out.println("test 1: New board is empty.. succeeded");
        } else {
            System.out.println("test 1: New board is empty.. failed");
        }

        if (test2()) {
            System.out.println("test 2: Correct starting position for Player One's checker pieces.. succeeded");
        } else {
            System.out.println("test 2: Correct starting position for Player One's checker pieces.. failed");
        }
        if (test3()) {
            System.out.println("test 3: Correct starting position for Player Two's checker pieces.. succeeded");
        } else {
            System.out.println("test 3: Correct starting position for Player Two's checker pieces.. failed");
        }
        if (test4()) {
            System.out.println("test 4: Blank spaces are empty at start of game.. succeeded");
        } else {
            System.out.println("test 4: Blank spaces are empty at start of game.. failed");
        }
        if (test5()) {
            System.out.println("test 5: Add Pawn to board.. succeeded");
        } else {
            System.out.println("test 5: Add Pawn piece to board.. failed");
        }

        if (test6()) {
            System.out.println("test 6: Add King piece to board.. succeeded");
        } else {
            System.out.println("test 6: Add King piece to board.. failed");
        }

        if (test7()) {
            System.out.println("test 7: Remove King piece from board.. succeeded");
        } else {
            System.out.println("test 7: Remove King piece from board.. failed");
        }

        if (test8()) {
            System.out.println("test 8: Remove Pawn piece from board.. succeeded");
        } else {
            System.out.println("test 8: Remove Pawn piece from board.. failed");
        }
        if (test9()) {
            System.out.println("test 9: Able to move P2 piece on board.. succeeded");
        } else {
            System.out.println("test 9: Able to move P2 piece on board.. failed");
        }
        if (test10()) {
            System.out.println("test 10: Able to move P1 piece on board.. succeeded");
        } else {
            System.out.println("test 10: Able to move P1 piece on board.. failed");
        }
        if (test11()) {
            System.out.println("test 11: isDoubleJump method returns true when double jump is possible.. succeeded");
        } else {
            System.out.println("test 11:  isDoubleJump method returns true when double jump is possible.. failed");
        }

        if (test12()) {
            System.out.println("test 12: isSingleJump method returns true when signle jump is possible.. succeeded");
        } else {
            System.out.println("test 12: isSingleJump method returns true when signle jump is possible.. failed");
        }

        if (test13()) {
            System.out.println("test 13: addPieceAtIndex method adds piece to index.. succeeded");
        } else {
            System.out.println("test 13: addPieceAtIndex method adds piece to index.. failed");
        }
        if (test14()) {
            System.out.println("test 14: getPlayerOnePieceLocationsInts method returns player ones piece locations.. succeeded");
        } else {
            System.out.println("test 14: getPlayerOnePieceLocationsInts method returns player ones piece locations.. failed");
        }
        if (test15()) {
            System.out.println("test 15: getPlayerTwoPieceLocationsInts method returns player ones piece locations.. succeeded");
        } else {
            System.out.println("test 15: getPlayerTwoPieceLocationsInts method returns player ones piece locations.. failed");
        }
        if (test16()) {
            System.out.println("test 16: isValidmove method checks if the move is valid.. succeeded");
        } else {
            System.out.println("test 16: isValidmove method checks if the move is valid.. failed");
        }
        
    }
}
