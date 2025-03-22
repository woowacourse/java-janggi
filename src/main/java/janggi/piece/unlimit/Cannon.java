package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Side;
import java.util.List;
import java.util.Map;

public class Cannon extends UnLimitMovable {

    public Cannon(final Side side) {
        super(side);
    }

    @Override
    public void addValidDestination(final List<Position> positions, final List<Position> reachablePositions,
                                    final Map<Position, Piece> board) {
        boolean isJumped = false;
        for (Position position : positions) {
            Piece targetPiece = board.get(position);
            if (position.isOutOfRange() || targetPiece.isNotJumpable()) {
                break;
            }
            if (!isJumped && targetPiece.isOccupied()) {
                isJumped = true;
                continue;
            }

            if (isJumped && handleAfterJumped(reachablePositions, position, targetPiece)) {
                break;
            }
        }
    }

    private boolean handleAfterJumped(final List<Position> reachablePositions, final Position position,
                                      final Piece targetPiece) {
        if (targetPiece.isOccupied()) {
            if (!isAlly(targetPiece)) {
                reachablePositions.add(position);
            }
            return true;
        }
        reachablePositions.add(position);
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}
