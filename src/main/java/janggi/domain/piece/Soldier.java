package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.strategy.NoObstacleStrategy;

public abstract class Soldier extends Piece {

    private static final MoveStrategy MOVE_STRATEGY = new NoObstacleStrategy();

    public Soldier(Dynasty dynasty) {
        super(PieceType.SOLIDER, dynasty, MOVE_STRATEGY);
    }
}
