package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.side.TeamType;

public interface Piece {

    void validateCanMove(Position start, Position end, Board board);

    String name();

    PieceType getPieceType();

    TeamType getTeamType();
}
