package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.condition.EmptyCondition;
import janggi.domain.piece.condition.MoveCondition;
import janggi.domain.piece.condition.OnePieceExistsCondition;
import janggi.domain.piece.strategy.DiagonalStepStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.piece.strategy.SingleStepStrategy;
import janggi.domain.piece.strategy.SlidingStrategy;
import java.util.List;

public enum PieceRule {

    GENERAL(new SingleStepStrategy(false), new EmptyCondition()),
    CHARIOT(new SlidingStrategy(), new EmptyCondition()),
    HORSE(new DiagonalStepStrategy(1), new EmptyCondition()),
    CANNON(new SlidingStrategy(), new OnePieceExistsCondition()),
    GUARD(new SingleStepStrategy(false), new EmptyCondition()),
    ELEPHANT(new DiagonalStepStrategy(2), new EmptyCondition()),
    SOLDIER(new SingleStepStrategy(true), new EmptyCondition());

    private final MoveStrategy moveStrategy;
    private final MoveCondition moveCondition;

    PieceRule(MoveStrategy moveStrategy, MoveCondition moveCondition) {
        this.moveStrategy = moveStrategy;
        this.moveCondition = moveCondition;
    }

    public List<Position> findPath(Position source, Position destination, Camp camp) {
        return moveStrategy.findPath(source, destination, camp);
    }

    public void checkPath(List<Position> path, Camp camp, BoardChecker board) {
        moveCondition.checkPath(path, camp, board, this);
    }
}
