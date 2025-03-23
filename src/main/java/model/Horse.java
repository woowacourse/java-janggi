package model;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    private final List<List<Movement>> movements = List.of(
        List.of(UP, UP_UP_LEFT), List.of(UP, UP_UP_RIGHT),
        List.of(DOWN, DOWN_DOWN_LEFT), List.of(DOWN, DOWN_DOWN_RIGHT),
        List.of(LEFT, LEFT_DOWN_LEFT), List.of(LEFT, LEFT_UP_LEFT),
        List.of(RIGHT, RIGHT_DOWN_RIGHT), List.of(RIGHT, RIGHT_UP_RIGHT));

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
            List<Position> temporaryMoves = new ArrayList<>();
            for (Movement movement : moves) {
                if (!departure.canMove(movement)) {
                    continue;
                }
                temporaryMoves.add(departure.move(movement));
            }
            temporaryPosition.add(temporaryMoves);
        }
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
            return "馬";
        }
        return "마";
    }
}
