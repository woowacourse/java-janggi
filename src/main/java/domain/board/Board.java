package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {

    private final Map<Point, Piece> pieceByPoint;
    private final PointNodeMapper pointNodeMapper;

    public Board(final Map<Point, Piece> pieceByPoint, final PointNodeMapper pointNodeMapper) {
        this.pieceByPoint = pieceByPoint;
        this.pointNodeMapper = pointNodeMapper;
    }

    public boolean isEnd() {
        return !isTwoWangsAlive();
    }

    private boolean isTwoWangsAlive() {
        return findTeamsOfWang().containsAll(List.of(Team.CHO, Team.HAN));
    }

    public Team findWinTeam() {
        if (!isEnd()) {
            throw new IllegalStateException("아직 게임이 끝나지 않았습니다.");
        }
        Set<Team> foundTeam = findTeamsOfWang();
        if (foundTeam.contains(Team.CHO)) {
            return Team.CHO;
        }
        return Team.HAN;
    }

    private Set<Team> findTeamsOfWang() {
        return pieceByPoint.values().stream()
                .filter(piece -> piece.type() == PieceType.WANG)
                .map(Piece::team)
                .collect(Collectors.toSet());
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

        pieceByPoint.put(destination, sourcePiece);
        removePiece(source);
    }

    public boolean existsPiece(final Point point) {
        if (!existsPoint(point)) {
            return false;
        }
        return pieceByPoint.containsKey(point);
    }

    private boolean existsPoint(final Point point) {
        return pointNodeMapper.existsPoint(point);
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
        return piece.team() == team;
    }

    public boolean hasPieceType(final Point point, final PieceType pieceType) {
        if (!existsPiece(point)) {
            return false;
        }
        Piece piece = getPieceByPoint(point);
        return piece.type() == pieceType;
    }

    private Piece getPieceByPoint(final Point point) {
        if (!existsPiece(point)) {
            throw new IllegalArgumentException(point + ": [ERROR] 해당 좌표에 기물이 존재하지 않습니다.");
        }
        return pieceByPoint.get(point);
    }

    public boolean existsNextPoint(final Point point, final Direction direction) {
        if (!existsPoint(point)) {
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
        if (!existsPoint(point)) {
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
        if (!existsPoint(point)) {
            throw new IllegalArgumentException(point.row() + ", " + point.column() + ": 존재하지 않는 좌표입니다.");
        }
    }

    public Map<Point, Piece> getPieceByPoint() {
        return pieceByPoint;
    }
}
