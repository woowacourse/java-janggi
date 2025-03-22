package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Map;

public class Board {

    private final Map<Point, Piece> pieceByPoint;
    private final PointNodeMapper pointNodeMapper;
    private boolean isEnd;

    public Board(Map<Point, Piece> pieceByPoint, Map<Point, Node> nodeByPoint) {
        this.pieceByPoint = pieceByPoint;
        this.pointNodeMapper = new PointNodeMapper(nodeByPoint);
        this.isEnd = false;
    }

    public boolean canMove(Point source, Point destination) {
        if (!existsPiece(source)) {
            return false;
        }
        Piece piece = findPieceByPoint(source);
        Node sourceNode = findNodeByPoint(source);
        Node destinationNode = findNodeByPoint(destination);
        return piece.canMove(sourceNode, destinationNode, this);
    }

    public boolean isRunning() {
        return !isEnd;
    }

    public void movePiece(Point source, Point destination) {
        Piece sourcePiece = findPieceByPoint(source);
        Node sourceNode = findNodeByPoint(source);
        Node destinationNode = findNodeByPoint(destination);
        if (!sourcePiece.canMove(sourceNode, destinationNode, this)) {
            throw new IllegalArgumentException(source + " -> " + destination + " [ERROR] 이동할 수 없는 경로입니다.");
        }

        if (existsWang(destination)) {
            isEnd = true;
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

    public boolean existsPiece(Point point) {
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

    public boolean existsWang(Point point) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = findPieceByPoint(point);
        return piece.type() == PieceType.WANG;
    }

    public boolean existsPo(Node node) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Point point = pointNodeMapper.getPointByNode(node);
        Piece piece = findPieceByPoint(point);
        return piece.type() == PieceType.PO;
    }

    public void removePiece(Point point) {
        if (!existsPiece(point)) {
            return;
        }
        Piece piece = findPieceByPoint(point);
        pieceByPoint.remove(point, piece);
    }

    public boolean hasPieceInTeam(Point point, Team team) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = findPieceByPoint(point);
        return piece.hasTeam(team);
    }

    public boolean hasPieceInTeam(Node node, Team team) {
        if (!existsPieceByNode(node)) {
            return false;
        }
        Point point = pointNodeMapper.getPointByNode(node);
        Piece piece = findPieceByPoint(point);
        return piece.hasTeam(team);
    }

    public boolean hasPieceType(Point point, PieceType pieceType) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = findPieceByPoint(point);
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
