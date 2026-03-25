package janggi.strategy;

public enum StrategyLabel {

    HEHE("마상마상", 1),
    HEEH("마상상마", 2),
    EHHE("상마마상", 3),
    EHEH("상마상마", 4);

    private final String name;
    private final int decisionNumber;

    StrategyLabel(String name, int decisionNumber) {
        this.name = name;
        this.decisionNumber = decisionNumber;
    }

    public String getName() {
        return name;
    }

    public int getDecisionNumber() {
        return decisionNumber;
    }
}
