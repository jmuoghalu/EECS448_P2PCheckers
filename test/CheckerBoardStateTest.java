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

    public static void main(String args[]) {
        System.out.println("starting tests...");
        if (test1()) {
            System.out.println("test 1 succeeded");
        } else {
            System.out.println("test 1 failed");
        }
    }
}
