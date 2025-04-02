package janggi.strategy;

import janggi.piece.Piece;
import janggi.value.Position;
import java.util.List;

public interface MoveStrategy {

    boolean ableToMove(Position start, Position destination, List<Piece> enemy, List<Piece> allies);
}
