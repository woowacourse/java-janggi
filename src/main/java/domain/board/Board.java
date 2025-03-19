package domain.board;

import domain.PieceType;
import domain.piece.Piece;
import domain.piece.Team;
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
        return START_ROW_INDEX <= row && row <= END_ROW_INDEX
                && START_COLUMN_INDEX <= column && column <= END_COLUMN_INDEX;
    }

    public void putPiece(Node node, Piece piece) {
        board.put(node, piece);
    }

    public void removePieceByNode(Node node) {
        if (!existsPiece(node)) {
            return;
        }
        Piece piece = board.get(node);
        board.remove(node, piece);
    }

    public boolean existsPiece(Node node) {
        return board.containsKey(node);
    }

    public boolean existsPieceByTeam(Node node, Team team) {
        if (!existsPiece(node)) {
            return false;
        }
        Piece piece = board.get(node);
        return piece.hasTeam(team);
    }

    public boolean existsPoByNode(Node node) {
        if (!existsPiece(node)) {
            return false;
        }
        Piece piece = board.get(node);
        return piece.type() == PieceType.PO;
    }

    public Node findNodeByPoint(Point point) {
        if (!nodeByPoint.containsKey(point)) {
            throw new IllegalArgumentException("해당 위치에 노드가 존재하지 않습니다.");
        }
        return nodeByPoint.get(point);
    }

    public PieceType findPieceTypeByNode(Node node) {
        if (!board.containsKey(node)) {
            throw new IllegalArgumentException("해당 노드에 기물이 존재하지 않습니다.");
        }
        return board.get(node).type();
    }

    public boolean hasTeamByNode(Node node, Team team) {
        if (!board.containsKey(node)) {
            throw new IllegalArgumentException("해당 노드에 기물이 존재하지 않습니다.");
        }
        return board.get(node).hasTeam(team);
    }
}
