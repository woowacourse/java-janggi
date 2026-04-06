package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.GuardStrategy;
import java.util.Map;

public class GuardPiece extends Piece {

    public GuardPiece(Team team) {
        super(team, Name.GUARD, new GuardStrategy());
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
