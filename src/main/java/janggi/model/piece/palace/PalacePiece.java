package janggi.model.piece.palace;

import janggi.model.Team;
import janggi.model.piece.Piece;
import java.util.List;

public abstract class PalacePiece extends Piece {

    private final Movement movement;

    }

    @Override
    public PositionPath getLegalPath(Position from, Position to) {
        return movement.move(from, to);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo) {
        return piecesOnPath.isEmpty() && !this.isSameTeam(pieceAtTo);
    }

    @Override
    public boolean canPassThrough(List<Piece> piecesOnPath) {
        return piecesOnPath.isEmpty();
    }

}
