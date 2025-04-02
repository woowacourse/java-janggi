package model.board;

import model.Position;
import model.piece.Piece;

public interface BoardSearcher {

    boolean hasPieceOn(Position position);

    Piece get(Position position);

    Piece find(Position position);

    boolean isInBoard(Position position);
}
