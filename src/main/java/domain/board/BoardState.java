package domain.board;

import domain.piece.Piece;
import domain.position.Position;

public interface BoardState {
    boolean isBlocked(Position position);

    Piece findBy(Position position);
}
