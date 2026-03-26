package model.pieces.rule;

import java.util.List;

public abstract class MoveRule {
    private final List<MovePattern> movePatterns;

    protected MoveRule(List<MovePattern> movePatterns) {
        this.movePatterns = movePatterns;
    }
}
