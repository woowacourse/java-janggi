package team.janggi.domain.movestrategy;

import java.util.List;
import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class ElephantMoveStrategy implements MoveStrategy {
    private static final ElephantMoveStrategy INSTANCE = new ElephantMoveStrategy();

    private ElephantMoveStrategy() {
    }

    public static ElephantMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean calculateMove(Position from, Position to, Map<Position, Piece> mapStatus) {
        return validateDirection(from, to) && isPathBlock(from, to, mapStatus) && canKill(from, to, mapStatus);
    }

    private boolean validateDirection(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        return (dx + dy) == 5;
    }

    private boolean isPathBlock(Position from, Position to, Map<Position, Piece> mapStatus) {
        List<Position> obstaclePositions = getObstaclePosition(from, to);
        return !isOccupied(obstaclePositions, mapStatus);
    }

    private List<Position> getObstaclePosition(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        int obstacleX = from.x();
        int obstacleY = from.y();

        if (dx > dy) {
            obstacleX += (to.x() - from.x()) / 3;
        }
        if (dy > dx) {
            obstacleY += (to.y() - from.y()) / 3;
        }

        int obstacle2X = Integer.signum(to.x() - from.x());
        int obstacle2Y = Integer.signum(to.y() - from.y());

        Position obstacle = new Position(obstacleX, obstacleY);
        Position obstacle2 = new Position(obstacleX + obstacle2X, obstacleY + obstacle2Y);
        return List.of(obstacle, obstacle2);
    }

    private boolean isOccupied(List<Position> obstaclePositions, Map<Position, Piece> mapStatus) {
        return obstaclePositions.stream()
                .anyMatch(obstacle -> !mapStatus.get(obstacle).isSamePieceType(PieceType.EMPTY));
    }

    private boolean canKill(Position from, Position to, Map<Position, Piece> mapStatus) {
        Piece currentPiece = mapStatus.get(from);
        Piece definationPiece = mapStatus.get(to);
        return !currentPiece.isSameTeam(definationPiece);
    }

}
