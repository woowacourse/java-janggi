package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.team.Team;

public interface Piece {

    boolean isEmptyPiece();

    boolean isSameTeam(Team team);

    PieceType getType();

    Team getTeam();

    Path getPath(Movement movement);

    void validateCanMove(PieceOnPath piecesOnPath, Piece endPiece);
}
