package janggi.strategy;

import janggi.board.Movement;
import janggi.board.Position;
import janggi.piece.Team;

import java.util.ArrayList;
import java.util.List;

public class SoldierMovementStrategy implements MovementStrategy {

    private final List<Movement> commonMovements;

    private final Team team;

    public SoldierMovementStrategy(Team team) {
        this.team = team;
        this.commonMovements = List.of(
                Movement.LEFT,
                Movement.RIGHT
        );
    }

    @Override
    public boolean canReachGoal(Position start, Position goal) {
        List<Movement> movements = new ArrayList<>(commonMovements);
        addMovementByTeam(movements);

        for (Movement movement : movements) {
            Position movedPosition = start.move(movement);
            if (goal.equals(movedPosition)) {
                return true;
            }
        }
        throw new IllegalArgumentException("[ERROR] 병은 지정한 목적지로 이동할 수 없습니다.");
    }

    private void addMovementByTeam(List<Movement> movements) {
        if (team == Team.GREEN) {
            movements.add(Movement.UP);
        }

        if (team == Team.RED) {
            movements.add(Movement.DOWN);
        }
    }
}
