package domain.board.strategy;

import domain.board.Side;

public enum InitialStrategyType {

    MASANG_SANGMA,
    MASANG_MASANG,
    SANGMA_MASANG,
    SANGMA_SANGMA;

    public InitialStrategy from(Side side) {
        if (this == MASANG_SANGMA) return new MaSangSangMaStrategy(side);
        if (this == MASANG_MASANG) return new MaSangMaSangStrategy(side);
        if (this == SANGMA_MASANG) return new SangMaMaSangStrategy(side);
        if (this == SANGMA_SANGMA) return new SangMaSangMaStrategy(side);

        throw new IllegalArgumentException("일치하는 타입이 없습니다.");
    }
}
