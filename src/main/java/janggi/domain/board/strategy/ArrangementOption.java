package janggi.domain.board.strategy;

import static janggi.domain.piece.PieceType.MA;
import static janggi.domain.piece.PieceType.SANG;

import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import java.util.List;

public enum ArrangementOption {
    MA_SANG_MA_SANG(List.of(MA, SANG, MA, SANG)),
    SANG_MA_SANG_MA(List.of(SANG, MA, SANG, MA)),
    SANG_MA_MA_SANG(List.of(SANG, MA, MA, SANG)),
    MA_SANG_SANG_MA(List.of(MA, SANG, SANG, MA)),
    ;

    private final List<PieceType> arrangement;

    ArrangementOption(List<PieceType> arrangement) {
        this.arrangement = arrangement;
    }

    public ArrangementStrategy toStrategy(Side side) {
        return MaSangArrangementStrategy.of(side, arrangement);
    }
}
