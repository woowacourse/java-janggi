package domain.board;

import domain.piece.Piece;
import java.util.Map;

public class Board {

    public static int START_ROW_INDEX = 1;
    public static int END_ROW_INDEX = 10;
    public static int START_COLUMN_INDEX = 1;
    public static int END_COLUMN_INDEX = 9;

    private final Map<Node, Piece> board;
    private final Map<Point, Node> nodeByPoint;

    public Board(Map<Node, Piece> board, Map<Point, Node> nodeByPoint) {
        this.board = board;
        this.nodeByPoint = nodeByPoint;
    }

    public static boolean isInRange(int row, int column) {
        return !(row < START_ROW_INDEX || row > END_ROW_INDEX)
                || (column < START_COLUMN_INDEX || column > END_COLUMN_INDEX);
    }
}
