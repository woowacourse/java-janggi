package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.movement.Rule;
import java.util.Collection;
import java.util.List;

public class PieceAction {
    private final List<Rule> rules;

    public PieceAction(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Position> calculateMovablePositions(BoardMediator boardMediator, Position from) {
        return rules.stream()
                .map(rule -> rule.execute(from, boardMediator))
                .flatMap(Collection::stream)
                .toList();
    }
}
