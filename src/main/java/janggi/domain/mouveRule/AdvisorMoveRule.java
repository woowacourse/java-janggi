package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.Palace;
import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

import java.util.List;

public class AdvisorMoveRule implements MoveRule {
    private final Palace palace;

    public AdvisorMoveRule(Palace palace) {
        this.palace = palace;
    }

    public static MoveRule ofHan() {
        return new AdvisorMoveRule(Palace.createHanPalace());
    }

    public static MoveRule ofCho() {
        return new AdvisorMoveRule(Palace.createChoPalace());
    }

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        return palace.canInnerGo(from, to);
    }
}
