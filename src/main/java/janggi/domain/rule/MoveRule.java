package janggi.domain.rule;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.rule.block.BlockStrategy;
import janggi.domain.rule.move.MoveStrategy;

public class MoveRule {

    private final MoveStrategy moveStrategy;
    private final BlockStrategy blockStrategy;

    public MoveRule(final MoveStrategy moveStrategy, final BlockStrategy blockStrategy) {
        this.moveStrategy = moveStrategy;
        this.blockStrategy = blockStrategy;
    }

    public void validateMove(Board board, Position departure, Position destination, Movement movement) {
        moveStrategy.validateCorrectRule(departure, destination, movement);
        blockStrategy.validateIsBlock(board, departure, destination);
    }
}
