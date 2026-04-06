package janggi.domain.piece;

import janggi.domain.board.Palace;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public class ChariotPiece extends Piece {
    private static final int NO_BLOCKING_PIECES = 0;
    private static final Palace PALACE = new Palace();

    public ChariotPiece(Team team) {
        super(team, Name.CHARIOT);
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
        if (countPiecesInPath(positionPieces, to) != NO_BLOCKING_PIECES) {
            return false;
        }
        return canCaptureDestinationPiece(positionPieces, to);
    }

    private int countPiecesInPath(Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.containsKey(to)) {
            return positionPieces.size() - 1;
        }
        return positionPieces.size();
    }
}
