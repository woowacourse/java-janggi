package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.strategy.CannonStrategy;
import janggi.domain.piece.strategy.ChariotStrategy;
import janggi.domain.piece.strategy.ElephantStrategy;
import janggi.domain.piece.strategy.GeneralStrategy;
import janggi.domain.piece.strategy.GuardStrategy;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.piece.strategy.MoveStrategy;
import janggi.domain.piece.strategy.SoldierStrategy;

public enum PieceRule {

    GENERAL(new GeneralStrategy()),
    CHARIOT(new ChariotStrategy()),
    HORSE(new HorseStrategy()),
    CANNON(new CannonStrategy()),
    GUARD(new GuardStrategy()),
    ELEPHANT(new ElephantStrategy()),
    SOLDIER(new SoldierStrategy()),
    ;

    private final MoveStrategy moveStrategy;

    PieceRule(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void validateMove(Position source, Position destination, Camp camp, BoardChecker board) {
        moveStrategy.validate(source, destination, camp, board, this);
    }
}
