package janggi.strategy;

import janggi.domain.Side;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ArrangementStrategyFactory {

    private final Map<Integer, Function<Side, ArrangementStrategy>> strategies;

    public ArrangementStrategyFactory() {
        this.strategies = new HashMap<>();
        strategies.put(StrategyLabel.HEEH.getDecisionNumber(), MaSangSangMa::new);
        strategies.put(StrategyLabel.HEHE.getDecisionNumber(), MaSangMaSang::new);
        strategies.put(StrategyLabel.EHHE.getDecisionNumber(), SangMaMaSang::new);
        strategies.put(StrategyLabel.EHEH.getDecisionNumber(), SangMaSangMa::new);
    }

    public ArrangementStrategy createStrategy(int decisionNumber, Side side) {
        Function<Side, ArrangementStrategy> creator = strategies.get(decisionNumber);

        if (creator == null) {
            throw new IllegalArgumentException("일치하는 전략 번호가 없습니다: " + decisionNumber);
        }

        return creator.apply(side);
    }
}
