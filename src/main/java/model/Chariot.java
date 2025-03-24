package model;

import static model.Movement.DOWN;
import static model.Movement.LEFT;
import static model.Movement.RIGHT;

import java.util.ArrayList;
import java.util.List;


public class Chariot extends Piece {

    private final List<Movement> movements = List.of(Movement.UP, DOWN, LEFT, RIGHT);

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return calculatePositionOfMovement(departure, arrival);
    }

    // TODO: Depth 분리
    private List<Position> calculatePositionOfMovement(Position departure, Position arrival) {
        for (Movement movement : movements) {
            List<Position> temporaryPosition = new ArrayList<>();
            Position movedPosition = departure.copyOf();
            while (movedPosition.canMove(movement)) {
                temporaryPosition.add(movedPosition.move(movement));
                movedPosition = movedPosition.move(movement);
                if (movedPosition.equals(arrival)) {
                    return temporaryPosition;
                }
            }
        }
        throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "車";
        }
        return "차";
    }
}
