package janggi.controller;

import janggi.strategy.ArrangementStrategy;
import janggi.strategy.SangMaSangMa;
import janggi.view.ApplicationView;
import java.util.List;

public class JanggiFlow {
    private final ApplicationView view;

    private final List<ArrangementStrategy> strategies;

    public JanggiFlow(ApplicationView view) {
        this.view = view;
        this.strategies = List.of(new SangMaSangMa());
    }

    public void process() {
        int decisionNumber = view.requestArrangementStrategyDecision(strategies);
        ArrangementStrategy strategy = findStrategyWithCorrespondingDecisionNumber(decisionNumber);
    }

    private ArrangementStrategy findStrategyWithCorrespondingDecisionNumber(int decisionNumber) {
        return strategies.stream()
                .filter(strategy -> strategy.isDecisionNumberMatching(decisionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 전략 번호가 없습니다: " + decisionNumber));
    }
}
