package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Side;
import java.util.List;
import java.util.Map;

public class Cannon extends UnLimitMovable {

    private final Side side;

    public Cannon(final Side side) {
        this.side = side;
    }

    @Override
    public void addValidDestination(final List<Position> positions, final List<Position> reachablePositions, final Map<Position, Piece> board) {
        boolean hasJumped = false;
        for (Position position : positions) {
            Piece targetPiece = board.get(position);
            if (position.isOutOfRange() || targetPiece.isNotJumpable()) {
                break;
            }
            if (!hasJumped && targetPiece.isOccupied()) {
                hasJumped = true;
                continue;
            }

            if (hasJumped && processAfterJump(reachablePositions, position, targetPiece)) {
                break;
            }
        }
    }

    private boolean processAfterJump(final List<Position> reachablePositions, final Position position,
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
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}
