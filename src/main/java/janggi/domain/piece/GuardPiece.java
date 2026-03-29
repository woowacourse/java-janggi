package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;
import java.util.List;
import java.util.Map;

public class GuardPiece extends Piece {

    public GuardPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.GUARD, moveStrategy);
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
}
