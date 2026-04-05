package janggi.controller;

import static janggi.domain.piece.PieceType.MA;
import static janggi.domain.piece.PieceType.SANG;

import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import janggi.strategy.ArrangementStrategy;
import janggi.strategy.MaSangArrangementStrategy;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum ArrangementOption {
    MA_SANG_MA_SANG(1, "마상마상", List.of(MA, SANG, MA, SANG)),
    SANG_MA_SANG_MA(2, "상마상마", List.of(SANG, MA, SANG, MA)),
    SANG_MA_MA_SANG(3, "상마마상", List.of(SANG, MA, MA, SANG)),
    MA_SANG_SANG_MA(4, "마상상마", List.of(MA, SANG, SANG, MA)),
    ;

    private static final Map<Integer, String> STRATEGY_OPTIONS =
            Collections.unmodifiableMap(
                    Arrays.stream(values())
                            .collect(Collectors.toMap(
                                    ArrangementOption::getOptionNumber,
                                    ArrangementOption::getDisplayName,
                                    (existing, replacement) -> existing,
                                    LinkedHashMap::new
                            ))
            );

    private final int optionNumber;
    private final String displayName;
    private final List<PieceType> arrangement;

    ArrangementOption(int optionNumber, String displayName, List<PieceType> arrangement) {
        this.optionNumber = optionNumber;
        this.displayName = displayName;
        this.arrangement = arrangement;
    }

    public static ArrangementStrategy createStrategyOf(Side side, int optionNumber) {
        return Arrays.stream(values())
                .filter(resolver -> resolver.optionNumber == optionNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 배치 전략 번호입니다."))
                .toStrategy(side);
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

    private ArrangementStrategy toStrategy(Side side) {
        return MaSangArrangementStrategy.of(side, arrangement);
    }
}
