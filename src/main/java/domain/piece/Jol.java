package domain.piece;

import domain.piece.strategy.MoveStrategy;

public class Jol extends NonJumpable {
    public Jol(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.JOL, team);
    }
}
