package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.HorseStrategy;
import java.util.Map;

public class HorsePiece extends Piece {

    public HorsePiece(Team team) {
        super(team, Name.HORSE, new HorseStrategy());
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
