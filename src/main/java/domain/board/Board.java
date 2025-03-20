package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
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

    public void putPiece(Node node, Piece piece) {
        board.put(node, piece);
    }

    public Node findNodeByPoint(Point point) {
        if (!nodeByPoint.containsKey(point)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 노드가 존재하지 않습니다.");
        }
        return nodeByPoint.get(point);
    }

    public Piece findPieceByNode(Node node) {
        if (!board.containsKey(node)) {
            throw new IllegalArgumentException("[ERROR] 해당 노드에 기물이 존재하지 않습니다.");
        }
        return board.get(node);
    }

    public boolean existsPieceByNode(Node node) {
        return board.containsKey(node);
    }

    public boolean existsPoByNode(Node node) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Piece piece = board.get(node);
        return piece.type() == PieceType.PO;
    }

    public void removePieceByNode(Node node) {
        if (!existsPieceByNode(node)) {
            return;
        }
        Piece piece = board.get(node);
        board.remove(node, piece);
    }

    public boolean hasPieceTeamByNode(Node node, Team team) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Piece piece = board.get(node);
        return piece.hasTeam(team);
    }

    public boolean hasPieceTypeByNode(Node node, PieceType pieceType) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Piece piece = board.get(node);
        return piece.type() == pieceType;
    }
}
