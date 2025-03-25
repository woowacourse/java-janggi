package janggi.strategy;

import janggi.board.Movement;
import janggi.board.Position;

import java.util.List;

public class GuardMovementStrategy implements MovementStrategy {

    private final List<Movement> movements;

    public GuardMovementStrategy() {
        this.movements = List.of(
                Movement.UP,
                Movement.DOWN,
                Movement.LEFT,
                Movement.RIGHT
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
        throw new IllegalArgumentException("[ERROR] 사는 지정한 목적지로 이동할 수 없습니다.");
    }
}
