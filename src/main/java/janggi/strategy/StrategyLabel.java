package janggi.strategy;

public enum StrategyLabel {

    MSMS("마상마상", 1),
    MSSM("마상상마", 2),
    SMMS("상마마상", 3),
    SMSM("상마상마", 4);

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
