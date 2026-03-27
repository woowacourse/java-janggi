package domain.place;

import domain.board.BoardView;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.Optional;

public interface Place {
    boolean isEmpty();

    boolean isSameSide(Side side);

    boolean isCannon();

    String getFormat();

    Optional<Side> getSide();

    boolean canMove(BoardView board, Position from, Position to);
}
