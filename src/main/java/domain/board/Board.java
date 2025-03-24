package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

import java.util.Map;

public class Board {

    public static final int START_ROW_INDEX = 1;
    public static final int END_ROW_INDEX = 10;
    public static final int START_COLUMN_INDEX = 1;
    public static final int END_COLUMN_INDEX = 9;

    private final Map<Node, Piece> board;
    private final Map<Point, Node> nodeByPoint;

    public Board(final Map<Node, Piece> board, final Map<Point, Node> nodeByPoint) {
        this.board = board;
        this.nodeByPoint = nodeByPoint;
    }

    public Node findNodeByPoint(final Point point) {
        if (!nodeByPoint.containsKey(point)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 노드가 존재하지 않습니다.");
        }
        return nodeByPoint.get(point);
    }

    public boolean hasPieceTeamByNode(final Node node, final Team team) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Piece piece = findPieceByNode(node);
        return piece.hasTeam(team);
    }

    public boolean existsPieceByNode(final Node node) {
        return board.containsKey(node);
    }

    public void movePiece(final Node sourceNode, final Node destinationNode, final Board board) {
        Piece sourcePiece = findPieceByNode(sourceNode);
        if (!sourcePiece.canMove(sourceNode, destinationNode, board)) {
            throw new IllegalArgumentException(sourceNode + " -> " + destinationNode + " [ERROR] 이동할 수 없는 경로입니다.");
        }
        putPiece(destinationNode, sourcePiece);
        removePieceByNode(sourceNode);
    }

    public Piece findPieceByNode(final Node node) {
        if (!existsPieceByNode(node)) {
            throw new IllegalArgumentException("[ERROR] 해당 노드에 기물이 존재하지 않습니다.");
        }
        return board.get(node);
    }

    public void putPiece(final Node node, final Piece piece) {
        board.put(node, piece);
    }

    public void removePieceByNode(final Node node) {
        if (!existsPieceByNode(node)) {
            return;
        }
        Piece piece = board.get(node);
        board.remove(node, piece);
    }

    public boolean isOpponentWangDead(final Team team) {
        return board.keySet().stream()
                .filter(node -> hasPieceTeamByNode(node, team.inverse()))
                .noneMatch(node -> hasPieceTypeByNode(node, PieceType.WANG));
    }

    public boolean hasPieceTypeByNode(final Node node, final PieceType pieceType) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Piece piece = board.get(node);
        return piece.type() == pieceType;
    }
}
