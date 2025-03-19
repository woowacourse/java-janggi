package janggi.piece;

import janggi.value.Position;
import java.util.List;

public interface Piece {

    Piece move(final Position destination, List<Piece> enemy, List<Piece> allies);

    boolean ableToMove(final Position destination, List<Piece> enemy, List<Piece> allies);

    PieceType getPieceType();

    Position getPosition();
}
