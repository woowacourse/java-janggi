package team.janggi.domain.movestrategy;

import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;

public class HorseMoveStrategy implements MoveStrategy {
    private static final HorseMoveStrategy INSTANCE = new HorseMoveStrategy();

    private HorseMoveStrategy() {
    }

    public static HorseMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean calculateMove(Position from, Position to, Map<Position, Piece> mapStatus) {
        return validateDirection(from, to) && isPathBlock(from, to, mapStatus) && canKill(from, to, mapStatus);
    }

    private boolean validateDirection(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        return (dx + dy) == 3;
    }

    private boolean isPathBlock(Position from, Position to, Map<Position, Piece> mapStatus) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        int fromX = from.x();
        int fromY = from.y();
        if (dx > dy) {
            fromX += (to.x() - from.x()) / 2;
        }
        if (dy > dx) {
            fromY += (to.y() - from.y()) / 2;
        }

        Position obstaclePosition = new Position(fromX, fromY);
        Piece obstacle = mapStatus.get(obstaclePosition);
        return obstacle.isSamePieceType(PieceType.EMPTY);
    }

    private boolean canKill(Position from, Position to, Map<Position, Piece> mapStatus) {
        Piece currentPiece = mapStatus.get(from);
        Piece definationPiece = mapStatus.get(to);
        return !currentPiece.isSameTeam(definationPiece);
    }
}
