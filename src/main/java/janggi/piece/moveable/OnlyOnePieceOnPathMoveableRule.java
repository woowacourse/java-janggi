package janggi.piece.moveable;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.List;

public class OnlyOnePieceOnPathMoveableRule implements Moveable {

    @Override
    public boolean isMoveable(Position start, Position end, Pieces pieces) {
        final Piece startPositionPiece = pieces.getPieceByPosition(start);
        final List<Position> path = startPositionPiece.calculatePath(start, end);
        final long countPieceOnPath = pieces.countPieceOnPath(path);
        return countPieceOnPath == 1;
    }
}
