package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

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
    public String getName() {
        if (getTeam() == Team.RED) {
            return "漢";
        }
        return "초";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Position> temporaryPosition = new ArrayList<>();
        calculatePositionOfMovement(departure, temporaryPosition);
        return findArrivalDirection(arrival, temporaryPosition);
    }

    private void calculatePositionOfMovement(Position departure, List<Position> temporaryPosition) {
        for (Movement movement : movements) {
            addMoveByDeparture(departure, temporaryPosition, movement);
        }
    }

    private void addMoveByDeparture(Position departure, List<Position> temporaryPosition,
        Movement movement) {
        if (!departure.canMove(movement)) {
            return;
        }
        temporaryPosition.add(departure.move(movement));
    }

    private List<Position> findArrivalDirection(Position arrival,
        List<Position> temporaryPosition) {
        return temporaryPosition.stream()
            .filter(position -> position.equals(arrival))
            .findFirst()
            .map(List::of)
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }
}
