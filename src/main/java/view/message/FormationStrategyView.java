package view.message;

import domain.board.strategy.InitialStrategyType;

public enum FormationStrategyView {

    TYPE_1(InitialStrategyType.OUTSIDE_MA, "마-상-상-마 (외마)"),
    TYPE_2(InitialStrategyType.RIGHT_SANG, "마-상-마-상 (오른상)"),
    TYPE_3(InitialStrategyType.INSIDE_MA, "상-마-마-상 (안마)"),
    TYPE_4(InitialStrategyType.LEFT_SANG, "상-마-상-마 (왼상)");

    private final InitialStrategyType initialStrategyType;
    private final String message;

    FormationStrategyView(InitialStrategyType initialStrategyType, String message) {
        this.initialStrategyType = initialStrategyType;
        this.message = message;
    }

    public static InitialStrategyType from(int index) {
        if (index == 1) {
            return TYPE_1.getInitialStrategyType();
        }

        if (index == 2) {
            return TYPE_2.getInitialStrategyType();
        }

        if (index == 3) {
            return TYPE_3.getInitialStrategyType();
        }

        if (index == 4) {
            return TYPE_3.getInitialStrategyType();
        }

        throw new IllegalArgumentException("일치하는 타입이 없습니다.");
    }

    public static String format(int index) {
        if (index == 1) {
            return TYPE_1.getMessage();
        }

        if (index == 2) {
            return TYPE_2.getMessage();
        }

        if (index == 3) {
            return TYPE_3.getMessage();
        }

        if (index == 4) {
            return TYPE_3.getMessage();
        }

        throw new IllegalArgumentException("일치하는 타입이 없습니다.");
    }


    public InitialStrategyType getInitialStrategyType() {
        return initialStrategyType;
    }

    public String getMessage() {
        return message;
    }
}
