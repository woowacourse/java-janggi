package domain.place;

import domain.board.BoardView;
import domain.place.piece.Side;
import domain.position.Position;

public interface Place {
    boolean isEmpty();

    boolean isSameSide(Side side);

    boolean isCannon();

    String getFormat();

    Side getSide();

    boolean canMove(BoardView board, Position from, Position to);
}
