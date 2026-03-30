package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;

public interface Piece {
    boolean isEmptyPiece();
    boolean isSamePiece(Piece other);
    boolean isSameTeam(Piece piece);
    boolean isSame(Team team);
    String getDisplayName();
    List<Position> getPath(Position from, Position to);
    boolean canMove(List<Piece> piecesOnPath, Piece endPiece);
}
