package team.janggi.domain.movestrategy;

import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;

public class KingMoveStrategy implements MoveStrategy {
    private static final int MAX_MOVE_DISTANCE = 1;

    private static final KingMoveStrategy INSTANCE = new KingMoveStrategy();

    private KingMoveStrategy() {
    }

    public static KingMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean calculateMove(Position from, Position to, Map<Position, Piece> mapStatus) {
        return isPalace(to) && (validatePalaceDirection(from, to) || validateDirection(from, to)) && !validateObstacle(
                from, to, mapStatus);
    }

    private boolean isPalace(Position to) {
        if (!isPalaceX(to)) {
            return false;
        }
        return isChoPalace(to) || isHanPalace(to);
    }

    private boolean isHanPalace(Position to) {
        return to.y() >= 0 && to.y() <= 2;
    }

    private boolean isChoPalace(Position to) {
        return to.y() >= 7 && to.y() <= 9;
    }

    private boolean isPalaceX(Position to) {
        return to.x() >= 3 && to.x() <= 5;
    }

    private boolean isPalaceCenter(Position center) {
        return center.x() == 4 && (center.y() == 8 || center.y() == 1);
    }

    private boolean validatePalaceDirection(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        if (dx == MAX_MOVE_DISTANCE && dy == MAX_MOVE_DISTANCE) {
            return isPalaceCenter(from) || isPalaceCenter(to);
        }
        return false;
    }

    private boolean validateDirection(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        return (dx + dy) == MAX_MOVE_DISTANCE;
    }

    private boolean validateObstacle(Position from, Position to, Map<Position, Piece> mapStatus) {
        Piece toPiece = mapStatus.get(to);
        Piece fromPiece = mapStatus.get(from);
        return toPiece.isSameTeam(fromPiece);
    }

}
