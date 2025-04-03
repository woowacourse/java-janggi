package janggi.piece.moveable;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.List;

public class PieceOnPathBlockingRule implements Moveable {

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        final Piece startPositionPiece = pieces.getPieceByPosition(start);
        final List<Position> path = startPositionPiece.calculatePath(start, end);
        final boolean isPieceOnPath = pieces.containsPiece(path);
        if (isPieceOnPath) {
            return false;
        }
        return true;
    }
}
