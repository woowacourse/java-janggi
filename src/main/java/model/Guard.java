package model;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;

public class Guard extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);

    public Guard(Team team) {
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
        return findArrivalDirection(arrival, temporaryPosition);
    }

    private void calculatePositionOfMovement(Position departure, List<Position> temporaryPosition) {
        for (Movement movement : movements) {
            if (!departure.canMove(movement)) {
                continue;
            }
            temporaryPosition.add(departure.move(movement));
        }
    }

    private List<Position> findArrivalDirection(Position arrival,
        List<Position> temporaryPosition) {
        return temporaryPosition.stream()
            .filter(position -> position.equals(arrival))
            .findFirst()
            .map(List::of)
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "士";
        }
        return "사";
    }
}


