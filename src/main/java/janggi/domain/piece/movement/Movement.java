package janggi.domain.piece.movement;

import janggi.domain.board.BoardChecker;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.movement.condition.MoveCondition;
import janggi.domain.piece.movement.strategy.MoveStrategy;
import java.util.List;

public class Movement {
    private final MoveStrategy moveStrategy;
    private final MoveCondition moveCondition;

    public Movement(MoveStrategy moveStrategy, MoveCondition moveCondition) {
        this.moveStrategy = moveStrategy;
        this.moveCondition = moveCondition;
    }

    public List<Position> findPath(Position source, Position destination) {
        return moveStrategy.findPath(source, destination);
    }

    public void checkPath(List<Position> path, Camp camp, BoardChecker board) {
        moveCondition.checkPath(path, camp, board);
    }
}
