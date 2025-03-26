package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Horse extends Piece {

    private final List<List<Movement>> movements = List.of(
        List.of(UP, UP_AND_DIAGONAL_UP_LEFT), List.of(UP, UP_AND_DIAGONAL_UP_RIGHT),
        List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_LEFT), List.of(DOWN, DOWN_AND_DIAGONAL_DOWN_RIGHT),
        List.of(LEFT, LEFT_AND_DIAGONAL_DOWN_LEFT), List.of(LEFT, LEFT_AND_DIAGONAL_UP_LEFT),
        List.of(RIGHT, RIGHT_AND_DIAGONAL_DOWN_RIGHT), List.of(RIGHT, RIGHT_AND_DIAGONAL_UP_RIGHT));

    public Horse(Team team) {
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

    private void addMoveByDeparture(Position departure, Movement movement, List<Position> temporaryMoves) {
        if (!departure.canMove(movement)) {
            return;
        }
        temporaryMoves.add(departure.move(movement));
    }

    private List<Position> findArrivalDirection(Position arrival, List<List<Position>> temporaryPosition) {
        return temporaryPosition.stream()
            .filter(positions -> !positions.isEmpty())
            .filter(positions -> positions.getLast().equals(arrival))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "馬";
        }
        return "마";
    }
}
