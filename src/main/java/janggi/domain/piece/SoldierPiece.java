package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.ChoSoldierStrategy;
import janggi.domain.movestrategy.HanSoldierStrategy;
import janggi.domain.movestrategy.MoveStrategy;
import java.util.Map;

public class SoldierPiece extends Piece {
    public SoldierPiece(Team team) {
        super(team, Name.SOLDIER, createMoveStrategy(team));
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

    private static MoveStrategy createMoveStrategy(Team team) {
        if (team == Team.HAN) {
            return new HanSoldierStrategy();
        }
        return new ChoSoldierStrategy();
    }
}
