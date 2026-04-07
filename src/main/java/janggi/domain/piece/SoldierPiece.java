package janggi.domain.piece;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public class SoldierPiece extends Piece {
    private static final Palace PALACE = new Palace();

    public SoldierPiece(Team team) {
        super(team, Name.SOLDIER);
    }

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        if (getTeam() == Team.HAN) {
            return canMoveHanSoldier(from, to) || canMoveHanSoldierDiagonallyInPalace(from, to);
        }
        return canMoveChoSoldier(from, to) || canMoveChoSoldierDiagonallyInPalace(from, to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {

        for (Piece piece : positionPieces.values()) {
            if (piece.isSameTeam(this)) {
                return false;
            }
        }
        return true;
    }

    private boolean canMoveHanSoldier(Position from, Position to) {
        if (from.deltaY(to) == 1 && from.isSameX(to)) {
            return true;
        }
        return from.distanceX(to) == 1 && from.isSameY(to);
    }

    private boolean canMoveChoSoldier(Position from, Position to) {
        if (from.deltaY(to) == -1 && from.isSameX(to)) {
            return true;
        }
        return from.distanceX(to) == 1 && from.isSameY(to);
    }

    private boolean canMoveHanSoldierDiagonallyInPalace(Position from, Position to) {
        if (!PALACE.contains(Team.CHO, from) || !PALACE.contains(Team.CHO, to)) {
            return false;
        }
        if (!PALACE.canMoveOneStepDiagonally(Team.CHO, from, to)) {
            return false;
        }
        return from.deltaY(to) == 1;
    }

    private boolean canMoveChoSoldierDiagonallyInPalace(Position from, Position to) {
        if (!PALACE.contains(Team.HAN, from) || !PALACE.contains(Team.HAN, to)) {
            return false;
        }
        if (!PALACE.canMoveOneStepDiagonally(Team.HAN, from, to)) {
            return false;
        }
        return from.deltaY(to) == -1;
    }
}
