package janggi.domain.piece;

import janggi.domain.board.BoardChecker;
import janggi.domain.board.Position;
import janggi.domain.piece.condition.EmptyCondition;
import janggi.domain.piece.condition.MoveCondition;
import janggi.domain.piece.condition.OnePieceExistsCondition;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.FriendlyPalaceSingleStepStrategy;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.piece.strategy.MultiStepStraightStrategy;
import janggi.domain.piece.strategy.SoldierStrategy;
import java.util.List;

public enum PieceType {

    GENERAL(new FriendlyPalaceSingleStepStrategy(), new EmptyCondition(), 0.0),
    CHARIOT(new MultiStepStraightStrategy(), new EmptyCondition(), 13.0),
    CANNON(new MultiStepStraightStrategy(), new OnePieceExistsCondition(), 7.0),
    HORSE(new HorseStrategy(), new EmptyCondition(), 5.0),
    ELEPHANT(new ElephantStrategy(), new EmptyCondition(), 3.0),
    GUARD(new FriendlyPalaceSingleStepStrategy(), new EmptyCondition(), 3.0),
    SOLDIER(new SoldierStrategy(), new EmptyCondition(), 2.0);

    private final MoveStrategy moveStrategy;
    private final MoveCondition moveCondition;
    private final double score;

    PieceType(MoveStrategy moveStrategy, MoveCondition moveCondition, double score) {
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

    public boolean isGeneral() {
        return this == GENERAL;
    }
}
