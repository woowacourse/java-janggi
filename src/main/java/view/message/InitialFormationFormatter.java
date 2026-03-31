package view.message;

import domain.board.Side;
import domain.board.strategy.InitialStrategy;
import domain.board.strategy.InsideMaStrategy;
import domain.board.strategy.OutsideMaStrategy;
import domain.board.strategy.RightSangStrategy;
import domain.board.strategy.LeftSangStrategy;

import java.util.Arrays;
import java.util.function.Function;

public enum InitialFormationFormatter {

    TYPE_1(1, OutsideMaStrategy::new, "마-상-상-마 (외마)"),
    TYPE_2(2, RightSangStrategy::new, "마-상-마-상 (오른상)"),
    TYPE_3(3, InsideMaStrategy::new, "상-마-마-상 (안마)"),
    TYPE_4(4, LeftSangStrategy::new, "상-마-상-마 (왼상)");

    private final int index;
    private final Function<Side, InitialStrategy> strategyFactory;
    private final String message;

    InitialFormationFormatter(int index,
                              Function<Side, InitialStrategy> strategyFactory,
                              String message) {
        this.index = index;
        this.strategyFactory = strategyFactory;
        this.message = message;
    }

    public static InitialFormationFormatter fromIndex(int index) {
        return Arrays.stream(values())
                .filter(v -> v.index == index)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 타입이 없습니다."));
    }

    public static String format(int index) {
        return fromIndex(index).message;
    }

    public InitialStrategy create(Side side) {
        return strategyFactory.apply(side);
    }
}
