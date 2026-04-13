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

    GENERAL(new GeneralStrategy(), 0),
    CHARIOT(new ChariotStrategy(), 13),
    HORSE(new HorseStrategy(), 5),
    CANNON(new CannonStrategy(), 7),
    GUARD(new GuardStrategy(), 3),
    ELEPHANT(new ElephantStrategy(), 3),
    SOLDIER(new SoldierStrategy(), 2),
    ;

    private final MoveStrategy moveStrategy;
    private final double score;

    PieceRule(MoveStrategy moveStrategy, int score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public void validateMove(Position source, Position destination, BoardChecker board) {
        moveStrategy.validate(source, destination, board);
    }

    public double getScore() {
        return score;
    }
}
