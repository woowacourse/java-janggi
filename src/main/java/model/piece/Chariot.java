package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Chariot extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<List<Position>> allMovementPosition = calculatePositionOfMovement(departure, arrival);
        return allMovementPosition.stream()
            .filter(positions -> positions.contains(arrival))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

    private List<List<Position>> calculatePositionOfMovement(Position departure, Position arrival) {
        List<List<Position>> temporaryAllPositions = new ArrayList<>();
        for (Movement movement : movements) {
            addTemporaryPositions(departure, arrival, movement, temporaryAllPositions);
        }
        return temporaryAllPositions;
    }

    private void addTemporaryPositions(Position departure, Position arrival, Movement movement,
        List<List<Position>> temporaryAllPositions) {
        List<Position> temporaryPosition = new ArrayList<>();
        Position movedPosition = departure.copyOf();
        while (movedPosition.canMove(movement) && !isArrival(movedPosition, arrival)) {
            temporaryPosition.add(movedPosition.move(movement));
            movedPosition = movedPosition.move(movement);
        }
        addTemporaryPosition(arrival, temporaryPosition, temporaryAllPositions);
    }

    private void addTemporaryPosition(Position arrival, List<Position> temporaryPosition,
        List<List<Position>> temporaryAllPositions) {
        if (temporaryPosition.contains(arrival)) {
            temporaryAllPositions.add(temporaryPosition);
            return;
        }
        temporaryAllPositions.add(Collections.emptyList());
    }

    private boolean isArrival(Position movedPosition, Position arrival) {
        return movedPosition.equals(arrival);
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "車";
        }
        return "차";
    }
}
