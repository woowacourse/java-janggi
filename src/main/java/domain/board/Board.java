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

    private final Map<Point, Piece> pieceByPoint;
    private final PointNodeMapper pointNodeMapper;

    public Board(Map<Point, Piece> pieceByPoint, Map<Point, Node> nodeByPoint) {
        this.pieceByPoint = pieceByPoint;
        this.pointNodeMapper = new PointNodeMapper(nodeByPoint);
    }

    public boolean canMove(Point source, Point destination) {
        if (!existsPieceByPoint(source)) {
            return false;
        }
        Piece piece = pieceByPoint.get(source);
        Node sourceNode = findNodeByPoint(source);
        Node destinationNode = findNodeByPoint(destination);
        return piece.canMove(sourceNode, destinationNode, this);
    }

    public void movePiece(Point source, Point destination) {
        Piece sourcePiece = findPieceByPoint(source);
        Node sourceNode = findNodeByPoint(source);
        Node destinationNode = findNodeByPoint(destination);
        if (!sourcePiece.canMove(sourceNode, destinationNode, this)) {
            throw new IllegalArgumentException(source + " -> " + destination + " [ERROR] 이동할 수 없는 경로입니다.");
        }

        pieceByPoint.put(destination, sourcePiece);
        removePiece(source);
    }

    public Node findNodeByPoint(Point point) {
        if (!pointNodeMapper.existsPoint(point)) {
            throw new IllegalArgumentException(point + ": [ERROR] 해당 위치에 노드가 존재하지 않습니다.");
        }
        return pointNodeMapper.getNodeByPoint(point);
    }

    public boolean existsPieceByPoint(Point point) {
        if (!pointNodeMapper.existsPoint(point)) {
            return false;
        }
        return pieceByPoint.containsKey(point);
    }

    public boolean existsPieceByNode(Node node) {
        if (!pointNodeMapper.existsNode(node)) {
            return false;
        }
        Point point = pointNodeMapper.getPointByNode(node);
        return pieceByPoint.containsKey(point);
    }

    public boolean existsWangByPoint(Point point) {
        if (!existsPieceByPoint(point)) {
            return false;
        }
        Piece piece = pieceByPoint.get(point);
        return piece.type() == PieceType.WANG;
    }

    public boolean existsPo(Node node) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Point point = pointNodeMapper.getPointByNode(node);
        Piece piece = pieceByPoint.get(point);
        return piece.type() == PieceType.PO;
    }

    public void removePiece(Point point) {
        if (!existsPieceByPoint(point)) {
            return;
        }
        Piece piece = pieceByPoint.get(point);
        pieceByPoint.remove(point, piece);
    }

    public boolean hasTeamOfPiece(Point point, Team team) {
        if (!existsPieceByPoint(point)) {
            return false;
        }
        Piece piece = pieceByPoint.get(point);
        return piece.hasTeam(team);
    }

    public boolean hasTeamOfPiece(Node node, Team team) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Point point = pointNodeMapper.getPointByNode(node);
        Piece piece = pieceByPoint.get(point);
        return piece.hasTeam(team);
    }

    public boolean hasPieceType(Point point, PieceType pieceType) {
        if (!existsPieceByPoint(point)) {
            return false;
        }
        Piece piece = pieceByPoint.get(point);
        return piece.type() == pieceType;
    }

    private Piece findPieceByPoint(Point point) {
        if (!pieceByPoint.containsKey(point)) {
            throw new IllegalArgumentException(point + ": [ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
        }
        return pieceByPoint.get(point);
    }

    public Map<Point, Piece> getPieceByPoint() {
        return pieceByPoint;
    }
}
