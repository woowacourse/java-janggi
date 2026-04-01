package janggi.controller;

import janggi.strategy.ArrangementStrategy;
import janggi.strategy.MaSangMaSangStrategy;
import janggi.strategy.MaSangSangMaStrategy;
import janggi.strategy.SangMaMaSangStrategy;
import janggi.strategy.SangMaSangMaStrategy;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum ArrangementStrategyResolver {
    MA_SANG_MA_SANG(1, "마상마상", MaSangMaSangStrategy.getInstance()),
    SANG_MA_SANG_MA(2, "상마상마", SangMaSangMaStrategy.getInstance()),
    SANG_MA_MA_SANG(3, "상마마상", SangMaMaSangStrategy.getInstance()),
    MA_SANG_SANG_MA(4, "마상상마", MaSangSangMaStrategy.getInstance());

    private static final Map<Integer, String> STRATEGY_OPTIONS =
            Collections.unmodifiableMap(
                    Arrays.stream(values())
                            .collect(Collectors.toMap(
                                    ArrangementStrategyResolver::getDecisionNumber,
                                    ArrangementStrategyResolver::getDisplayName,
                                    (existing, replacement) -> existing,
                                    LinkedHashMap::new
                            ))
            );

    private final int decisionNumber;
    private final String displayName;
    private final ArrangementStrategy strategy;

    ArrangementStrategyResolver(int decisionNumber, String displayName, ArrangementStrategy strategy) {
        this.decisionNumber = decisionNumber;
        this.displayName = displayName;
        this.strategy = strategy;
    }

    public static ArrangementStrategy resolve(int decisionNumber) {
        return Arrays.stream(values())
                .filter(resolver -> resolver.decisionNumber == decisionNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 배치 전략 번호입니다."))
                .getStrategy();
    }

    public static Map<Integer, String> getStrategyOptions() {
        return STRATEGY_OPTIONS;
    }

    private int getDecisionNumber() {
        return decisionNumber;
    }

    private String getDisplayName() {
        return displayName;
    }

    private ArrangementStrategy getStrategy() {
        return strategy;
    }
}
