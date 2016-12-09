import com.groupone.p2pgame.*;

public class CheckerBoardStateTest {

    public static boolean test1() {
        CheckerBoardState board = new CheckerBoardState();
        for (CheckerSquare square : board.getSquares()) {
            if (square.getPiece().getType() != PieceType.EMPTY) {
                return false;
            }
        }
        return true;
    }

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

    }
}
