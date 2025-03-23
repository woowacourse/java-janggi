package model;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;

public class General extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);

    public General(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Position> temporaryPosition = new ArrayList<>();
        calculatePositionOfMovement(departure, temporaryPosition);
        if (temporaryPosition.contains(arrival)) {
            return temporaryPosition;
        }
        throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
    }

    private void calculatePositionOfMovement(Position departure, List<Position> temporaryPosition) {
        for (Movement movement : movements) {
            if (!departure.canMove(movement)) {
                continue;
            }
            temporaryPosition.add(departure.move(movement));
        }
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "漢";
        }
        return "초";
    }
}
