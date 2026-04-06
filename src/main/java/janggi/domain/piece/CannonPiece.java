package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public class CannonPiece extends Piece {
    private static final int REQUIRED_BRIDGE_COUNT = 1;

    public CannonPiece(Team team) {
        super(team, Name.CANNON);
    }

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return canMoveStraight(from, to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return findStraightPath(from, to);
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        if (hasCannonInPath(positionPieces)) {
            return false;
        }
        if (countPiecesInPath(positionPieces, to) != REQUIRED_BRIDGE_COUNT) {
            return false;
        }
        return canCaptureDestinationPiece(positionPieces, to);
    }

    private boolean hasCannonInPath(Map<Position, Piece> positionPieces) {
        return positionPieces.values().stream()
                .anyMatch(piece -> getName().equals(piece.getName()));
    }

    private int countPiecesInPath(Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.containsKey(to)) {
            return positionPieces.size() - 1;
        }
        return positionPieces.size();
    }
}
