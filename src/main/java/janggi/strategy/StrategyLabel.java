package janggi.strategy;

import java.util.Arrays;

public enum StrategyLabel {

    HEHE(1),
    HEEH(2),
    EHHE(3),
    EHEH(4);

    private final int decisionNumber;

    StrategyLabel(int decisionNumber) {
        this.decisionNumber = decisionNumber;
    }

    public static StrategyLabel from(int decisionNumber) {
        return Arrays.stream(values())
                .filter(label -> label.decisionNumber == decisionNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전략 번호입니다: " + decisionNumber));
    }

    public int getDecisionNumber() {
        return decisionNumber;
    }
}
