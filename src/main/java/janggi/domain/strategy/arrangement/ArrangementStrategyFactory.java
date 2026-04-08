package janggi.domain.strategy.arrangement;

import janggi.domain.Side;
import janggi.domain.strategy.StrategyLabel;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class ArrangementStrategyFactory {

    private final Map<StrategyLabel, Function<Side, ArrangementStrategy>> strategies;

    public ArrangementStrategyFactory() {
        this.strategies = new EnumMap<>(StrategyLabel.class);
        strategies.put(StrategyLabel.HEEH, MaSangSangMa::new);
        strategies.put(StrategyLabel.HEHE, MaSangMaSang::new);
        strategies.put(StrategyLabel.EHHE, SangMaMaSang::new);
        strategies.put(StrategyLabel.EHEH, SangMaSangMa::new);
    }

    public ArrangementStrategy createStrategy(StrategyLabel strategyLabel, Side side) {
        return strategies.get(strategyLabel)
                .apply(side);
    }
}
