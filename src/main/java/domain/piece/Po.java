package domain.piece;

import domain.BoardStatus;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;

public class Po extends Piece {
    public Po(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.PO, team);
    }

    @Override
    public void check(BoardStatus boardStatus, Position start, Position destination) {

    }
}
