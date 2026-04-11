package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;

public interface Piece {
    boolean isEmptyPiece();
    boolean isSamePiece(Piece other);
    boolean isSameType(PieceType pieceType);
    PieceType getType();
    boolean isSameTeam(Piece other);
    boolean isSame(Team team);
    List<Position> getPath(Position from, Position to);
    void canMove(PiecesOnPath piecesOnPath, Piece endPiece);
}
