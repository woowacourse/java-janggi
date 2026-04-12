package janggi.domain.strategy.arrangement;

import janggi.domain.Side;
import janggi.domain.piece.Piece;

public interface ArrangementStrategy {

    void place(Piece[][] arrangement);

    default int calculateRow(int boardMaxLength, Side side) {
        if (side.equals(Side.CHO)) {
            return boardMaxLength - 1;
        }
        return 0;
    }
}
