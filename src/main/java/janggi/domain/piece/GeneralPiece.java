package janggi.domain.piece;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public class GeneralPiece extends Piece {
    private static final Palace PALACE = new Palace();

    public GeneralPiece(Team team) {
        super(team, Name.GENERAL);
    }

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        if (!PALACE.contains(getTeam(), from) || !PALACE.contains(getTeam(), to)) {
            return false;
        }
        if (from.distanceX(to) + from.distanceY(to) == 1) {
            return true;
        }
        return PALACE.canMoveOneStepDiagonally(getTeam(), from, to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        return canCaptureDestinationPiece(positionPieces, to);
    }
}
