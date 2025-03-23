package domain.board;

import domain.Path;
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

    public boolean isRunning() {
        return !isEnd;
    }

    public boolean canMove(final Point source, final Point destination) {
        if (!existsPiece(source)) {
            return false;
        }
        Piece piece = getPieceByPoint(source);
        return piece.canMove(source, destination, this);
    }

    public void movePiece(final Point source, final Point destination) {
        Piece sourcePiece = getPieceByPoint(source);
        if (!sourcePiece.canMove(source, destination, this)) {
            throw new IllegalArgumentException(source + " -> " + destination + " [ERROR] 이동할 수 없는 경로입니다.");
        }

        if (existsWang(destination)) {
            isEnd = true;
        }
        pieceByPoint.put(destination, sourcePiece);
        removePiece(source);
    }

    public Node getNodeByPoint(final Point point) {
        if (!pointNodeMapper.existsPoint(point)) {
            throw new IllegalArgumentException(point + ": [ERROR] 해당 위치에 노드가 존재하지 않습니다.");
        }
        return pointNodeMapper.getNodeByPoint(point);
    }

    public boolean existsPiece(final Point point) {
        if (!pointNodeMapper.existsPoint(point)) {
            return false;
        }
        return pieceByPoint.containsKey(point);
    }

    public boolean existsPiece(final Node node) {
        if (!pointNodeMapper.existsNode(node)) {
            return false;
        }
        Point point = pointNodeMapper.getPointByNode(node);
        return pieceByPoint.containsKey(point);
    }

    public boolean existsWang(final Point point) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = getPieceByPoint(point);
        return piece.type() == PieceType.WANG;
    }

    public boolean existsPo(final Point point) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = getPieceByPoint(point);
        return piece.type() == PieceType.PO;
    }

    public void removePiece(final Point point) {
        if (!existsPiece(point)) {
            return;
        }
        Piece piece = getPieceByPoint(point);
        pieceByPoint.remove(point, piece);
    }

    public boolean matchTeam(final Point point, final Team team) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = getPieceByPoint(point);
        return piece.hasTeam(team);
    }

    public boolean matchTeam(final Node node, final Team team) {
        if (!existsPiece(node)) {
            return false;
        }
        Point point = pointNodeMapper.getPointByNode(node);
        Piece piece = getPieceByPoint(point);
        return piece.hasTeam(team);
    }

    public boolean hasPieceType(final Point point, final PieceType pieceType) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = getPieceByPoint(point);
        return piece.type() == pieceType;
    }

    private Piece getPieceByPoint(final Point point) {
        if (!pieceByPoint.containsKey(point)) {
            throw new IllegalArgumentException(point + ": [ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
        }
        return pieceByPoint.get(point);
    }

    public boolean existNextPoint(final Point point, final Direction direction) {
        if (!pointNodeMapper.existsPoint(point)) {
            return false;
        }
        Node node = pointNodeMapper.getNodeByPoint(point);
        return node.hasNextNode(direction);
    }

    public Point getNextPoint(final Point point, final Direction direction) {
        validateExistPoint(point);
        Node node = pointNodeMapper.getNodeByPoint(point);
        Node nextNode = node.getNextNodeByDirection(direction);
        return pointNodeMapper.getPointByNode(nextNode);
    }

    public boolean canMoveByPath(final Point point, final Path path) {
        if (!pointNodeMapper.existsPoint(point)) {
            return false;
        }
        Node node = pointNodeMapper.getNodeByPoint(point);
        return node.canMoveByPath(path);
    }

    public Point getPointMovedByPath(final Point point, final Path path) {
        validateExistPoint(point);
        Node node = pointNodeMapper.getNodeByPoint(point);
        return pointNodeMapper.getPointByNode(node.moveByPath(path));
    }

    private void validateExistPoint(final Point point) {
        if (!pointNodeMapper.existsPoint(point)) {
            throw new IllegalArgumentException(point.row() + ", " + point.column() + ": 존재하지 않는 좌표입니다.");
        }
    }

    public Map<Point, Piece> getPieceByPoint() {
        return pieceByPoint;
    }
}
