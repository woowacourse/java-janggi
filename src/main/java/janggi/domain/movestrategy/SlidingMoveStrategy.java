package janggi.domain.movestrategy;

import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.rule.MoveRule;
import janggi.domain.position.Position;

import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy{
    private final List<MoveRule> rules;

    public SlidingMoveStrategy(List<MoveRule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        return rules.stream()
                .allMatch(rule -> rule.isValid(from, to, boardState));
    }
}
