package domain.piece;

import domain.board.Board;
import domain.board.Point;

public interface Piece {

    boolean canMove(Point source, Point destination, Board board);

    PieceType type();

    Team team();
}
