package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {
    private static final List<List<Integer>> destinations = List.of(
            List.of(2, 3), List.of(3, 2), List.of(2, -3), List.of(3, -2),
            List.of(-2, 3), List.of(-3, 2), List.of(-2, -3), List.of(-3, -2)
    );

    @Override
    public boolean canMove(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextY = to.getY();
        int nextX = to.getX();

        for (List<Integer> destination : destinations) {
            if (nextY - preY == destination.get(1)
                    && nextX - preX == destination.get(0)) {
                return true;
            }
        }
        return false;
    }
}
