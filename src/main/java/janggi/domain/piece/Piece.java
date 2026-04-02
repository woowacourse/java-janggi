package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;

public interface Piece {

    boolean isEmptyPiece();

    boolean isSamePiece(Piece other);

    PieceType getType();

    Team getTeam();

    Path getPath(Movement movement);

    void validateCanMove(PieceOnPath piecesOnPath, Piece endPiece);
}
