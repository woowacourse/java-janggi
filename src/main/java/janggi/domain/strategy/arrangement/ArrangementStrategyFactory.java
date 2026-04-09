package janggi.domain.strategy.arrangement;

import janggi.domain.Side;
import janggi.domain.strategy.StrategyLabel;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class ArrangementStrategyFactory {

    private static final Map<StrategyLabel, Function<Side, ArrangementStrategy>> strategies;

    private ArrangementStrategyFactory() {
    }

    static {
        strategies = new EnumMap<>(StrategyLabel.class);
        strategies.put(StrategyLabel.HEEH, MaSangSangMa::new);
        strategies.put(StrategyLabel.HEHE, MaSangMaSang::new);
        strategies.put(StrategyLabel.EHHE, SangMaMaSang::new);
        strategies.put(StrategyLabel.EHEH, SangMaSangMa::new);
    }

    public static ArrangementStrategy createStrategy(StrategyLabel strategyLabel, Side side) {
        return strategies.get(strategyLabel)
                .apply(side);
    }
}
