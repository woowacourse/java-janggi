package janggi.strategy;

public abstract class ArrangementStrategy {

    protected final StrategyLabel label;

    protected ArrangementStrategy(StrategyLabel label) {
        this.label = label;
    }

    public boolean isDecisionNumberMatching(int decisionNumber) {
        return label.getDecisionNumber() == decisionNumber;
    }

    public String name() {
        return label.getName();
    }

    public int decisionNumber() {
        return label.getDecisionNumber();
    }
}
