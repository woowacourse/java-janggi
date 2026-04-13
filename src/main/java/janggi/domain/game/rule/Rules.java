package janggi.domain.game.rule;

import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Rules {
    private final List<Rule> endRules;

    public Rules(List<Rule> endRules) {
        this.endRules = endRules;
    }

    public static Rules createWithDefaultRules() {
        return new Rules(List.of(new GeneralDeadRule()));
    }

    public Side winner(Map<Point, Piece> pieces) {
        Rule endRule = findEndedRule(pieces)
                .orElseThrow(() -> new IllegalStateException("게임이 끝나지 않았습니다."));
        return endRule.getWinSide(pieces);
    }

    public boolean isEnd(Map<Point, Piece> pieces) {
        return endRules.stream()
                .anyMatch(rule -> rule.isEnd(pieces));
    }

    private Optional<Rule> findEndedRule(Map<Point, Piece> pieces) {
        return endRules.stream()
                .filter(rule -> rule.isEnd(pieces))
                .findAny();
    }
}
