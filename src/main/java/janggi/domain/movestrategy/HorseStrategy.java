package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.List;

public class HorseStrategy implements MoveStrategy{
    private static final List<List<Integer>> destinations = List.of(
            List.of(1, 2), List.of(2, 1), List.of(1, -2), List.of(2, -1),
            List.of(-1, 2), List.of(-2, 1), List.of(-1, -2), List.of(-2, -1)
    );

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        int preX = from.getX();
        int preY = from.getY();

        int nextX = to.getX();
        int nextY = to.getY();

        for (List<Integer> destination : destinations) {
            if (nextY - preY == destination.get(1)
                    && nextX - preX == destination.get(0)) {
                return true;
            }
        }
        return false;
    }
}
