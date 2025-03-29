package janggi.domain.piece;

import janggi.domain.board.Dynasty;
import janggi.domain.piece.move.MoveStrategy;

public abstract class Soldier extends Piece {

    public Soldier(Dynasty dynasty, MoveStrategy moveStrategy) {
        super(PieceType.SOLIDER, dynasty, moveStrategy);
    }
}
