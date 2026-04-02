package janggi.domain.game;

import janggi.domain.piece.unit.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Rules {
    private final List<Rule> rules;

    public Rules(List<Rule> rules) {
        this.rules = rules;
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
        return rules.stream()
                .anyMatch(rule -> rule.isEnd(pieces));
    }

    private Optional<Rule> findEndedRule(Map<Point, Piece> pieces) {
        return rules.stream()
                .filter(rule -> rule.isEnd(pieces))
                .findAny();
    }
}
