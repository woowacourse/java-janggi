package janggi.domain.movestrategy.rule;

import janggi.domain.board.Position;
import janggi.domain.palace.Palace;

import java.util.ArrayList;
import java.util.List;

public class PalaceDiagonalForwardMoveRule implements MoveRule {

    private final List<Palace> palaces;

    public PalaceDiagonalForwardMoveRule(List<Palace> palaces) {
        this.palaces = palaces;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        if (!from.isOneSameDiagonal(to)) {
            return false;
        }
        return palaces.stream()
                .anyMatch(palace -> palace.areBothOnDiagonal(from, to));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position nextPosition = from.moveDiagonal(to);
        while (!nextPosition.equals(to)) {
            path.add(nextPosition);
            nextPosition = nextPosition.moveDiagonal(to);
        }
        return path;
    }
}
