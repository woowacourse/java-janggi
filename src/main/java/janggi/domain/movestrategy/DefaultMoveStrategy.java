package janggi.domain.movestrategy;

import janggi.domain.movestrategy.rule.MoveRule;

import java.util.List;

public class DefaultMoveStrategy extends AbstractMoveStrategy {

    public DefaultMoveStrategy(List<MoveRule> moveRules) {
        super(moveRules);
    }
}
