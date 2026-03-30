package view.message;

import domain.board.strategy.InitialStrategyType;

public enum FormationStrategyView {

    TYPE_1(InitialStrategyType.MASANG_SANGMA, "마상상마"),
    TYPE_2(InitialStrategyType.MASANG_MASANG, "마상마상"),
    TYPE_3(InitialStrategyType.SANGMA_MASANG, "상마마상"),
    TYPE_4(InitialStrategyType.SANGMA_SANGMA, "상마상마");

    private final InitialStrategyType initialStrategyType;
    private final String message;

    FormationStrategyView(InitialStrategyType initialStrategyType, String message) {
        this.initialStrategyType = initialStrategyType;
        this.message = message;
    }

    public static InitialStrategyType from(int index) {
        if (index == 1) {
            return TYPE_1.getFormatationStrategyType();
        }

        if (index == 2) {
            return TYPE_2.getFormatationStrategyType();
        }

        if (index == 3) {
            return TYPE_3.getFormatationStrategyType();
        }

        if (index == 4) {
            return TYPE_3.getFormatationStrategyType();
        }

        throw new IllegalArgumentException("일치하는 타입이 없습니다.");
    }


    public InitialStrategyType getFormatationStrategyType() {
        return initialStrategyType;
    }

    public String getMessage() {
        return message;
    }
}
