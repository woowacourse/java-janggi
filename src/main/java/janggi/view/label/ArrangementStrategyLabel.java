package janggi.view.label;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum ArrangementStrategyLabel {
    MA_SANG_MA_SANG(1, "마상마상"),
    SANG_MA_SANG_MA(2, "상마상마"),
    SANG_MA_MA_SANG(3, "상마마상"),
    MA_SANG_SANG_MA(4, "마상상마"),
    ;

    private static final Map<Integer, String> STRATEGY_OPTIONS =
            Collections.unmodifiableMap(
                    Arrays.stream(values())
                            .collect(Collectors.toMap(
                                    ArrangementStrategyLabel::getOptionNumber,
                                    ArrangementStrategyLabel::getDisplayName,
                                    (existing, replacement) -> existing,
                                    LinkedHashMap::new
                            ))
            );

    private final int optionNumber;
    private final String displayName;

    ArrangementStrategyLabel(int optionNumber, String displayName) {
        this.optionNumber = optionNumber;
        this.displayName = displayName;
    }

    public static ArrangementStrategyLabel createArrangementOption(int optionNumber) {
        return Arrays.stream(values())
                .filter(label -> label.optionNumber == optionNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 배치 전략 번호입니다."));
    }

    public static Map<Integer, String> getStrategyOptions() {
        return STRATEGY_OPTIONS;
    }

    private int getOptionNumber() {
        return optionNumber;
    }

    private String getDisplayName() {
        return displayName;
    }
}
