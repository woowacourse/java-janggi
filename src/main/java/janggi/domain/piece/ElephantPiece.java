package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElephantPiece extends Piece {

    public ElephantPiece(Team team, MoveStrategy moveStrategy) {
        super(team, Name.ELEPHANT, moveStrategy);
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.size() >= 2) {
            return false;
        }
        for (Position position : positionPieces.keySet()) {
            if (position.equals(to)) {
                return !isSameTeam(positionPieces.get(position));
            }
            return false;
        }
        return true;
    }
}
