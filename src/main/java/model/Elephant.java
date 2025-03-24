package model;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {

    private final List<List<Movement>> movements = List.of(
        List.of(UP, UP_UP_LEFT, UP_UP_LEFT_UP_LEFT),
        List.of(UP, UP_UP_RIGHT, UP_UP_RIGHT_UP_RIGHT),
        List.of(DOWN, DOWN_DOWN_LEFT, DOWN_DOWN_LEFT_DOWN_LEFT),
        List.of(DOWN, DOWN_DOWN_RIGHT, DOWN_DOWN_RIGHT_DOWN_RIGHT),
        List.of(LEFT, LEFT_DOWN_LEFT, LEFT_DOWN_LEFT_DOWN_LEFT),
        List.of(LEFT, LEFT_UP_LEFT, LEFT_UP_LEFT_UP_LEFT),
        List.of(RIGHT, RIGHT_DOWN_RIGHT, RIGHT_DOWN_RIGHT_DOWN_RIGHT),
        List.of(RIGHT, RIGHT_UP_RIGHT, RIGHT_UP_RIGHT_UP_RIGHT));

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<List<Position>> temporaryPosition = new ArrayList<>();
        calculatePositionOfMovement(departure, temporaryPosition);
        return findArrivalDirection(arrival, temporaryPosition);
    }

    private void calculatePositionOfMovement(Position departure, List<List<Position>> temporaryPosition) {
        for (List<Movement> moves : movements) {
            calculateMoves(departure, temporaryPosition, moves);
        }
    }

    private void calculateMoves(Position departure, List<List<Position>> temporaryPosition, List<Movement> moves) {
        List<Position> temporaryMoves = new ArrayList<>();
        for (Movement movement : moves) {
            addMoveByDeparture(departure, movement, temporaryMoves);
        }
        temporaryPosition.add(temporaryMoves);
    }

    private static void addMoveByDeparture(Position departure, Movement movement, List<Position> temporaryMoves) {
        if (!departure.canMove(movement)) {
            return;
        }
        temporaryMoves.add(departure.move(movement));
    }

    private List<Position> findArrivalDirection(Position arrival, List<List<Position>> temporaryPosition) {
        return temporaryPosition.stream()
            .filter(positions -> positions.getLast().equals(arrival))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "象";
        }
        return "상";
    }
}
