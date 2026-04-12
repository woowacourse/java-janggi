package domain.board;

import domain.piece.Piece;

public interface BoardState {
    Piece pieceAt(Position position);

    boolean isEmpty(Position position);
}
