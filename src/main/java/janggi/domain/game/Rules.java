package janggi.domain.game;

import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Rules {
    private final List<Rule> rules;

    public Rules(List<Rule> rules) {
        this.rules = rules;
    }

    public static Rules createWithDefaultRules() {
        return new Rules(List.of(new GeneralDeadRule()));
    }

    public Side winner(Collection<Piece> pieces) {
        Rule endRule = findEndedRule(pieces)
                .orElseThrow(() -> new IllegalStateException("게임이 끝나지 않았습니다."));
        return endRule.getWinSide(pieces);
    }

    public boolean isEnd(Collection<Piece> pieces) {
        return rules.stream()
                .anyMatch(rule -> rule.isEnd(pieces));
    }

    private Optional<Rule> findEndedRule(Collection<Piece> pieces) {
        return rules.stream()
                .filter(rule -> rule.isEnd(pieces))
                .findAny();
    }
}
