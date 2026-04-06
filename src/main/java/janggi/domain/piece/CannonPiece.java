package janggi.domain.piece;

import janggi.domain.movestrategy.CannonStrategy;
import janggi.domain.board.Position;
import java.util.Map;

public class CannonPiece extends Piece {
    public CannonPiece(Team team) {
        super(team, Name.CANNON, new CannonStrategy());
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.size() >= 3) {
            return false;
        }
        long count = positionPieces.values().stream()
                .filter(piece -> getName().equals(piece.getName()))
                .count();
        if (count != 0) {
            return false;
        }

        if (positionPieces.size() == 2) {
            if (positionPieces.containsKey(to)) {
                Piece piece = positionPieces.get(to);
                return !piece.isSameTeam(this);
            }
            return false;
        }

        return !positionPieces.containsKey(to);
    }
}
