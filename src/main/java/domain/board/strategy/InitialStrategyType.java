package domain.board.strategy;

import domain.board.Side;

public enum InitialStrategyType {

    OUTSIDE_MA, // 마-상-상-마 (외마)
    INSIDE_MA,  // 상-마-마-상 (안마)
    LEFT_SANG,  // 상-마-상-마 (왼상)
    RIGHT_SANG; // 마-상-마-상 (오른상)

    public InitialStrategy from(Side side) {
        if (this == OUTSIDE_MA) return new OutsideMaStrategy(side);
        if (this == INSIDE_MA) return new InsideMaStrategy(side);
        if (this == LEFT_SANG) return new LeftSangStrategy(side);
        if (this == RIGHT_SANG) return new RightSangStrategy(side);

        throw new IllegalArgumentException("일치하는 타입이 없습니다.");
    }
}
