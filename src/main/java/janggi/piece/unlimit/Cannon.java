package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends UnLimitMovable {

    public Cannon(final Side side) {
        super(side);
    }

    @Override
    public List<Position> addValidDestination(final List<Position> positions, final Map<Position, Piece> board) {
        List<Position> reachableDestinations = new ArrayList<>();
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

            if (isJumped && handleAfterJumped(reachableDestinations, position, targetPiece)) {
                break;
            }
        }
        return reachableDestinations;
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
