package domain;

import domain.pieces.Piece;

public interface PieceFinder {
    Piece find(Position position);
}
