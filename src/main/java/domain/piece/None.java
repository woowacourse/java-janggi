package domain.piece;

import domain.board.PathPieces;
import domain.position.Path;
import domain.position.Position;

public class None extends Piece {

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return null;
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return false;
    }

    @Override
    public boolean isDifferentTeam(Piece piece) {
        return false;
    }
}
