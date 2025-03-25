package janggi.strategy;

import janggi.board.Movement;
import janggi.board.Position;

import java.util.List;

public class ChariotMovementStrategy implements MovementStrategy {

    private final List<Movement> movements;

    public ChariotMovementStrategy() {
        this.movements = List.of(

        );
    }

    @Override
    public boolean canReachGoal(Position start, Position goal) {
        for (Movement movement : movements) {
            Position movedPosition = start.move(movement);
            if (goal.equals(movedPosition)) {
                return true;
            }
        }
        throw new IllegalArgumentException("[ERROR] 차는 지정한 목적지로 이동할 수 없습니다.");
    }
}
