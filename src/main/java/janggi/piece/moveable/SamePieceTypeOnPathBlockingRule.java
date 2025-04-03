package janggi.piece.moveable;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.List;

public class SamePieceTypeOnPathBlockingRule implements Moveable {

    @Override
    public boolean isMoveable(Position start, Position end, Pieces pieces) {
        final Piece startPositionPiece = pieces.getPieceByPosition(start);
        final List<Position> path = startPositionPiece.calculatePath(start, end);
        boolean isSamePieceTypeOnPath = pieces.isSamePieceTypeOnPath(path, startPositionPiece);
        if (isSamePieceTypeOnPath) {
            return false;
        }
        return true;
    }
}
