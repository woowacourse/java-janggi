package janggi.strategy;

import janggi.board.Movement;
import janggi.board.Position;

import java.util.List;

public class HorseMovementStrategy implements MovementStrategy {

    private final List<Movement> movements;

    public HorseMovementStrategy() {
        this.movements = List.of(
                Movement.RIGHT_UP_UP,
                Movement.RIGHT_RIGHT_UP,
                Movement.RIGHT_RIGHT_DOWN,
                Movement.RIGHT_DOWN_DOWN,
                Movement.LEFT_UP_UP,
                Movement.LEFT_LEFT_UP,
                Movement.LEFT_LEFT_DOWN,
                Movement.LEFT_DOWN_DOWN
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
        throw new IllegalArgumentException("[ERROR] 마는 지정한 목적지로 이동할 수 없습니다.");
    }
}
