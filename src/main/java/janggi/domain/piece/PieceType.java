package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.condition.EmptyCondition;
import janggi.domain.piece.condition.MoveCondition;
import janggi.domain.piece.condition.OnePieceExistsCondition;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.piece.strategy.MultiStepStraightStrategy;
import janggi.domain.piece.strategy.PalaceStrategy;
import janggi.domain.piece.strategy.SoldierStrategy;
import java.util.List;

public enum PieceType {

    GENERAL(new PalaceStrategy(), new EmptyCondition(), 0),
    CHARIOT(new MultiStepStraightStrategy(), new EmptyCondition(), 13),
    CANNON(new MultiStepStraightStrategy(), new OnePieceExistsCondition(), 7),
    HORSE(new HorseStrategy(), new EmptyCondition(), 5),
    ELEPHANT(new ElephantStrategy(), new EmptyCondition(), 3),
    GUARD(new PalaceStrategy(), new EmptyCondition(), 3),
    SOLDIER(new SoldierStrategy(), new EmptyCondition(), 2);

    private final MoveStrategy moveStrategy;
    private final MoveCondition moveCondition;
    private final double score;

    PieceType(MoveStrategy moveStrategy, MoveCondition moveCondition, int score) {
        this.moveStrategy = moveStrategy;
        this.moveCondition = moveCondition;
        this.score = score;
    }

    public List<Position> findPath(Position source, Position destination, Camp camp) {
        return moveStrategy.findPath(source, destination, camp);
    }

    public void checkPath(List<Position> path, Camp camp, BoardChecker board) {
        moveCondition.checkPath(path, camp, board, this);
    }

    public double score() {
        return score;
    }
}
