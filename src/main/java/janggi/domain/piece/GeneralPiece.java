package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.GeneralStrategy;
import java.util.Map;

public class GeneralPiece extends Piece {

    public GeneralPiece(Team team) {
        super(team, Name.GENERAL, new GeneralStrategy());
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
