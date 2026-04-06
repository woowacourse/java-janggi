package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Sang extends NonJumpable {
    public Sang(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.SANG, team);
    }

    public Sang(Long id, MoveStrategy moveStrategy, Team team) {
        super(id, moveStrategy, PieceType.SANG, team);
    }
}
