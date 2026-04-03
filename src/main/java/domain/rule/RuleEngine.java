package domain.rule;

import domain.board.Board;
import java.util.List;

public class RuleEngine {
    private final List<DrawRule> drawRules;

    public RuleEngine(List<DrawRule> drawRules) {
        this.drawRules = drawRules;
    }

    public boolean isDraw(Board board) {
        return drawRules.stream().anyMatch(rule -> rule.isDraw(board));
    }
}

