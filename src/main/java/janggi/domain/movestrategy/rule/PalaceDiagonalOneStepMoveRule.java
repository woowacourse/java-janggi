package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;
import janggi.domain.palace.Palace;

import java.util.List;

public class PalaceDiagonalOneStepMoveRule implements MoveRule {

    private final Palace palace;

    public PalaceDiagonalOneStepMoveRule(Palace palace) {
        this.palace = palace;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        if (!from.isMatchDistance(to, 1, 1)) {
            return false;
        }
        return palace.areBothOnDiagonal(from, to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of();
    }
}
