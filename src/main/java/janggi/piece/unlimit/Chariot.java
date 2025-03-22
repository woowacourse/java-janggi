package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Side;
import java.util.List;
import java.util.Map;

public class Chariot extends UnLimitMovable {

    private final Side side;

    public Chariot(final Side side) {
        this.side = side;
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
        return PieceType.CHARIOT;
    }

    @Override
    public void addValidDestination(final List<Position> positions, final List<Position> reachablePositions,
                                     Map<Position, Piece> board) {
        for (Position position : positions) {
            Piece targetPiece = board.get(position);
            if (isBoundPosition(position, reachablePositions, targetPiece)) {
                break;
            }
            reachablePositions.add(position);
        }
    }

    private boolean isBoundPosition(final Position position, final List<Position> reachablePositions,
                                    final Piece targetPiece) {
        if (position.isOutOfRange() || isAlly(targetPiece)) {
            return true;
        }
        if (targetPiece.isOccupied() && !isAlly(targetPiece)) {
            reachablePositions.add(position);
            return true;
        }
        return false;
    }
}
