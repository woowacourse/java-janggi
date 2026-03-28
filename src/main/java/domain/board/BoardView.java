package domain.board;

import domain.place.piece.PieceSymbol;
import domain.position.Position;

public interface BoardView {

    boolean isEmpty(Position position);

    boolean isSameSide(Position from, Position to);

    boolean isSameSymbol(Position position, PieceSymbol pieceSymbol);
}

