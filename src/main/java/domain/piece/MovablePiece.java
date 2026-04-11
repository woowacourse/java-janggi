package domain.piece;

import domain.board.PathPieces;
import domain.position.Path;
import domain.position.Position;

public interface MovablePiece extends BasicPiece {
    Path calculatePath(Position source, Position destination);

    boolean validatePath(PathPieces pathPieces);
}

