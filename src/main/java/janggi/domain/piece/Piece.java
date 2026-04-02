package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.List;

public interface Piece {

    boolean isEmptyPiece();

    boolean isSamePiece(Piece other);

    PieceType getType();

    Team getTeam();

    List<Position> getPath(Position from, Position to);

    boolean canMove(List<Piece> piecesOnPath, Piece endPiece);
}
