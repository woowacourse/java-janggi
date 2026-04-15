package janggi.controller.mapper;

import janggi.domain.board.strategy.ArrangementOption;
import janggi.view.label.ArrangementStrategyLabel;
import java.util.EnumMap;
import java.util.Map;

@SuppressWarnings("java:S6548")
public class ArrangementMapper {

    private static final ArrangementMapper INSTANCE = new ArrangementMapper();

    private final Map<ArrangementStrategyLabel, ArrangementOption> arrangementMatcher;

    private ArrangementMapper() {
        this.arrangementMatcher = new EnumMap<>(ArrangementStrategyLabel.class);
        arrangementMatcher.put(ArrangementStrategyLabel.MA_SANG_MA_SANG, ArrangementOption.MA_SANG_MA_SANG);
        arrangementMatcher.put(ArrangementStrategyLabel.SANG_MA_SANG_MA, ArrangementOption.SANG_MA_SANG_MA);
        arrangementMatcher.put(ArrangementStrategyLabel.SANG_MA_MA_SANG, ArrangementOption.SANG_MA_MA_SANG);
        arrangementMatcher.put(ArrangementStrategyLabel.MA_SANG_SANG_MA, ArrangementOption.MA_SANG_SANG_MA);
    }

    public static ArrangementMapper getInstance() {
        return INSTANCE;
    }

    public ArrangementOption findArrangementOption(ArrangementStrategyLabel label) {
        if (arrangementMatcher.containsKey(label)) {
            return arrangementMatcher.get(label);
        }
        throw new IllegalArgumentException("유효하지 않은 배치 전략입니다.");
    }
}
