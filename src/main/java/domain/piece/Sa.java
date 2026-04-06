package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Sa extends NonJumpable {
    public Sa(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.SA, team);
    }

    public Sa(Long id, MoveStrategy moveStrategy, Team team) {
        super(id, moveStrategy, PieceType.SA, team);
    }
}
