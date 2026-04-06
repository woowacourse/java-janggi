package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Cha extends NonJumpable {
    public Cha(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.CHA, team);
    }

    public Cha(Long id, MoveStrategy moveStrategy, Team team) {
        super(id, moveStrategy, PieceType.CHA, team);
    }
}
