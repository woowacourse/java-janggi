package janggi;

import java.util.List;

public interface Piece {

    void move(final Position destination, List<Piece> enemy, List<Piece> allies);

    void ableToMove(final Position destination, List<Piece> enemy, List<Piece> allies);

    PieceType getPieceType();

    Position getPosition();
}
