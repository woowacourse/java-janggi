package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Piece;

public interface ArrangementStrategy {

    void place(Piece[][] arrangement, Side side);
}
