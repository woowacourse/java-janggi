package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;

import java.util.Map;

public class SoliderPiece extends Piece {
    public SoliderPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.SOLDIER, moveStrategy);
    }

    @Override
    public boolean determineMovingRule(Map<Position, Piece> positionPieces, Position to) {
        for (Piece piece : positionPieces.values()) {
            if (piece.isSameTeam(this)) {
                return false;
            }
        }
        return true;
    }
}
