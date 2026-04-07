package janggi.domain.piece;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public class CannonPiece extends Piece {
    private static final int REQUIRED_PIECES_IN_PATH_COUNT = 1;
    private static final Palace PALACE = new Palace();

    public CannonPiece(Team team) {
        super(team, Name.CANNON);
    }

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return canMoveStraight(from, to) || PALACE.canMoveOnDiagonalLine(from, to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        if (canMoveStraight(from, to)) {
            return findStraightPath(from, to);
        }
        return PALACE.findDiagonalPath(from, to);
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        if (hasCannonInPath(positionPieces)) {
            return false;
        }
        if (countPiecesInPath(positionPieces, to) != REQUIRED_PIECES_IN_PATH_COUNT) {
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
