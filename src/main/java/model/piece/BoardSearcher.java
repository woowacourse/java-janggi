package model.piece;

import model.Position;

public interface BoardSearcher {

    boolean hasPieceOn(Position position);

    Piece get(Position position);

    Piece find(Position position);

    boolean isInBoard(Position position);
}
