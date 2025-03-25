package janggi.strategy;

import janggi.board.Movement;
import janggi.board.Position;

import java.util.List;

public class ElephantMovementStrategy implements MovementStrategy {

    private final List<Movement> movements;

    public ElephantMovementStrategy() {
        this.movements = List.of(
                Movement.RIGHT_RIGHT_UP_UP_UP,
                Movement.RIGHT_RIGHT_DOWN_DOWN_DOWN,
                Movement.RIGHT_RIGHT_RIGHT_UP_UP,
                Movement.RIGHT_RIGHT_RIGHT_DOWN_DOWN,
                Movement.LEFT_LEFT_UP_UP_UP,
                Movement.LEFT_LEFT_DOWN_DOWN_DOWN,
                Movement.LEFT_LEFT_LEFT_UP_UP,
                Movement.LEFT_LEFT_LEFT_DOWN_DOWN
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
        throw new IllegalArgumentException("[ERROR] 상은 지정한 목적지로 이동할 수 없습니다.");
    }
}
