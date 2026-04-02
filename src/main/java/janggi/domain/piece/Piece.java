package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Position;

public interface Piece {

    boolean isEmptyPiece();

    boolean isSamePiece(Piece other);

    PieceType getType();

    Team getTeam();

    Path getPath(Position from, Position to);

    boolean canMove(PieceOnPath piecesOnPath, Piece endPiece);
}
