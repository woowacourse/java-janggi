package janggi.domain.piece;

import janggi.domain.board.Dynasty;
import janggi.domain.piece.move.MoveStrategy;

public abstract class Soldier extends Piece {

    public Soldier(PieceType pieceType, Dynasty dynasty, MoveStrategy moveStrategy) {
        super(pieceType, dynasty, moveStrategy);
    }
}
