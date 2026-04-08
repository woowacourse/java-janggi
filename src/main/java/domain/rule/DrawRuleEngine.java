package domain.rule;

import domain.board.Board;
import java.util.List;

public class DrawRuleEngine {
    private final List<DrawRule> drawRules;

    public DrawRuleEngine(List<DrawRule> drawRules) {
        this.drawRules = drawRules;
    }

    public boolean isDraw(Board board) {
        return drawRules.stream().anyMatch(rule -> rule.isDraw(board));
    }
}

