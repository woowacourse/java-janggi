package piece;

import board.Board;
import board.Position;

import java.util.List;

public class Cannon extends Piece {

    public Cannon(final TeamType teamType) {
        super(teamType);
    }

    @Override
    protected boolean withInDirection(Position src, Position destination) {
        return src.isSameLine(destination);
    }

    @Override
    protected boolean withInRangeByMovement(double distanceByPositions) {
        return true;
    }

    @Override
    protected boolean passFilter(Position src, Position destination, Board board) {
        int count = 0;
        final List<Position> positions = src.calculateBetweenPositions(destination);
        for (final Position position : positions) {
            if (board.existPieceByPosition(position)) {
                count++;
            }
            if (isSamePieceType(board, position)) {
                return false;
            }
        }
        return count == 1;
    }

    private boolean isSamePieceType(Board board, Position position) {
        Piece piece = board.getPieceByPosition(position);
        if (equalsType(piece)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Cannon;
    }
}
