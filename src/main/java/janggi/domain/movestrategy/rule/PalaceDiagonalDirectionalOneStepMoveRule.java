package janggi.domain.movestrategy.rule;

import janggi.domain.board.BoardDirection;
import janggi.domain.board.Position;
import janggi.domain.palace.Palace;

import java.util.List;

public class PalaceDiagonalDirectionalOneStepMoveRule implements MoveRule {

    private final Palace palace;
    private final BoardDirection direction;

    public PalaceDiagonalDirectionalOneStepMoveRule(Palace palace, BoardDirection direction) {
        this.palace = palace;
        this.direction = direction;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        if (!from.isMatchDistance(to, 1, 1)) {
            return false;
        }
        if (!palace.areBothOnDiagonal(from, to)) {
            return false;
        }
        return direction.isForward(from.calculateY(to));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of();
    }
}
