package janggi.domain.piece;

import janggi.domain.board.BoardState;
import janggi.domain.movestrategy.ChaMoveStrategy;
import janggi.domain.movestrategy.GungseongBoundMoveStrategy;
import janggi.domain.movestrategy.JolMoveStrategy;
import janggi.domain.movestrategy.MaMoveStrategy;
import janggi.domain.movestrategy.MoveStrategy;
import janggi.domain.movestrategy.PoMoveStrategy;
import janggi.domain.movestrategy.SangMoveStrategy;
import janggi.domain.position.Position;

public enum PieceType {
    CHA( new ChaMoveStrategy(), 13),
    PO( new PoMoveStrategy(), 7),
    MA( new MaMoveStrategy(), 5),
    SANG( new SangMoveStrategy(), 3),
    SA( new GungseongBoundMoveStrategy(), 3),
    HAN_GUNG( new GungseongBoundMoveStrategy(), 0),
    CHO_GUNG( new GungseongBoundMoveStrategy(), 0),
    HAN_JOL( new JolMoveStrategy(), 2),
    CHO_JOL( new JolMoveStrategy(), 2);

    private final MoveStrategy moveStrategy;
    private final int score;

    PieceType(MoveStrategy moveStrategy, int score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public boolean canMove(Position from, Position to, BoardState boardState) {
        return moveStrategy.canMove(from, to, boardState);
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public int getScore() {
        return score;
    }
}
