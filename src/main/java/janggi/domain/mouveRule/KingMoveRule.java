package janggi.domain.mouveRule;

import janggi.domain.board.Palace;
import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Position;

public class KingMoveRule implements MoveRule {
    private final Palace palace;

    public KingMoveRule(Palace palace) {
        this.palace = palace;
    }

    public static MoveRule ofHan() {
        return new KingMoveRule(Palace.createHanPalace());
    }

    public static MoveRule ofCho() {
        return new KingMoveRule(Palace.createChoPalace());
    }

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        return palace.canInnerGo(from, to);
    }
}
