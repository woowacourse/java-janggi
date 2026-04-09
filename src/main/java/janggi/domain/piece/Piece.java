package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.Team;

public interface Piece {

    boolean isEmptyPiece();

    boolean isSameTeam(Team team);

    boolean isSameType(PieceType type);

    double getScore();

    Team getTeam();

    PieceType getType();

    Path getPath(Movement movement);

    void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece);
}
