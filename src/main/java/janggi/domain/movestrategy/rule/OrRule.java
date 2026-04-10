package janggi.domain.movestrategy.rule;

import janggi.domain.board.BoardState;
import janggi.domain.position.Position;

public class OrRule implements MoveRule {
    private final MoveRule ruleA;
    private final MoveRule ruleB;

    public OrRule(MoveRule ruleA, MoveRule ruleB) {
        this.ruleA = ruleA;
        this.ruleB = ruleB;
    }

    @Override
    public boolean isValid(Position from, Position to, BoardState boardState) {
        return ruleA.isValid(from, to, boardState) || ruleB.isValid(from, to, boardState);
    }
}
