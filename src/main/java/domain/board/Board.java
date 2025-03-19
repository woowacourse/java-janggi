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
        return !((row < START_ROW_INDEX || row > END_ROW_INDEX)
                || (column < START_COLUMN_INDEX || column > END_COLUMN_INDEX));
    }

    public boolean existsPiece(Point point) {
        if (!nodeByPoint.containsKey(point)) {
            return false;
        }
        return board.containsKey(nodeByPoint.get(point));
    }

    public Piece findPieceByPoint(Point point) {
        Node node = findNodeByPoint(point);
        if (!board.containsKey(node)) {
            throw new IllegalArgumentException("해당 노드에 기물이 존재하지 않습니다.");
        }
        return board.get(node);
    }

    private Node findNodeByPoint(Point point) {
        if (!nodeByPoint.containsKey(point)) {
            throw new IllegalArgumentException("해당 위치에 노드가 존재하지 않습니다.");
        }
        return nodeByPoint.get(point);
    }
}
