package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.move.MoveStrategy;
import java.util.List;
import java.util.Set;

public abstract class Soldier extends Piece {

    public Soldier(Dynasty dynasty, MoveStrategy moveStrategy) {
        super(PieceType.SOLIDER, dynasty, moveStrategy);
    }
}
