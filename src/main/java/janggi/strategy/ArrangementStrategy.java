package janggi.strategy;

import janggi.domain.Side;
import janggi.domain.piece.Piece;

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

    protected int calculateRow(int boardMaxLength, Side side) {
        if (side.equals(Side.CHO)) {
            return boardMaxLength - 1;
        }
        return 0;
    }

    public abstract void place(Piece[][] arrangement, Side side);
}
