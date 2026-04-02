package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.strategy.DiagonalStepRule;
import janggi.domain.piece.strategy.EmptySlidingRule;
import janggi.domain.piece.strategy.JumpingSlidingRule;
import janggi.domain.piece.strategy.MoveRule;
import janggi.domain.piece.strategy.SingleStepRule;

public enum PieceRule {

    GENERAL(new SingleStepRule(false)),
    CHARIOT(new EmptySlidingRule()),
    HORSE(new DiagonalStepRule(1)),
    CANNON(new JumpingSlidingRule()),
    GUARD(new SingleStepRule(false)),
    ELEPHANT(new DiagonalStepRule(2)),
    SOLDIER(new SingleStepRule(true));

    private final MoveRule moveRule;

    PieceRule(MoveRule moveRule) {
        this.moveRule = moveRule;
    }

    public void validateMove(Position source, Position destination, Camp camp, BoardChecker board) {
        moveRule.validate(source, destination, camp, board, this);
    }
}
