package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Node;

public interface Piece {

    boolean canMove(Team team, Node source, Node destination, Board board);

    PieceType type();

    boolean hasTeam(Team team);
}
