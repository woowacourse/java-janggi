package team.janggi.domain.movestrategy;

import java.util.Map;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.piece.Piece;

public class SoldierMoveStrategy implements MoveStrategy {
    private static final SoldierMoveStrategy INSTANCE = new SoldierMoveStrategy();

    private SoldierMoveStrategy() {
    }

    public static SoldierMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean calculateMove(Position from, Position to, Map<Position, Piece> mapStatus) {
        Piece soldier = mapStatus.get(from);
        if (soldier.isSameTeam(Team.CHO)) {
            return (canChoForward(from, to) || canSideMove(from, to) || canChoDiagonalMove(from, to))
                    && !validateObstacle(from, to, mapStatus);
        }

        if (soldier.isSameTeam(Team.HAN)) {
            return (canHanForward(from, to) || canSideMove(from, to) || canHanDiagonalMove(from, to))
                    && !validateObstacle(
                    from, to, mapStatus);
        }

        return false;
    }

    private boolean canChoForward(Position from, Position to) {
        return from.y() - to.y() == 1 && from.x() == to.x();
    }

    private boolean canHanForward(Position from, Position to) {
        return to.y() - from.y() == 1 && from.x() == to.x();
    }

    private boolean canSideMove(Position from, Position to) {
        return from.y() == to.y() && Math.abs(from.x() - to.x()) == 1;
    }

    private boolean isDiagonalMove(Position from, Position to) {
        return Math.abs(from.x() - to.x()) == 1 && Math.abs(from.y() - to.y()) == 1;
    }

    private boolean isChoPalaceCenter(Position center) {
        return center.x() == 4 && center.y() == 8;
    }

    private boolean isHanPalaceCenter(Position center) {
        return center.x() == 4 && center.y() == 1;
    }

    private boolean canChoDiagonalMove(Position from, Position to) {
        return isDiagonalMove(from, to) && (isHanPalaceCenter(from) || isHanPalaceCenter(to)) && to.y() < from.y();
    }

    private boolean canHanDiagonalMove(Position from, Position to) {
        return isDiagonalMove(from, to) && (isChoPalaceCenter(from) || isChoPalaceCenter(to)) && to.y() > from.y();
    }

    private boolean validateObstacle(Position from, Position to, Map<Position, Piece> mapStatus) {
        Piece toPiece = mapStatus.get(to);
        Piece fromPiece = mapStatus.get(from);
        return toPiece.isSameTeam(fromPiece);
    }
}
