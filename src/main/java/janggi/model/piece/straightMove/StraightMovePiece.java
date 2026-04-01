package janggi.model.piece.straightMove;

import janggi.model.Team;
import janggi.model.piece.Piece;
import java.util.List;

public abstract class StraightMovePiece extends Piece {

    protected final Movement movement;

    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }


    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }
}
