package domain.board;

import domain.Coordinate;

public interface WritableBoard {

    void movePiece(Coordinate from, Coordinate to);
}
