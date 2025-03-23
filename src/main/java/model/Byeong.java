package model;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;

public class Byeong extends Piece {

    private final List<Movement> movements = List.of(DOWN, LEFT, RIGHT);

    public Byeong() {
        super(Team.RED);
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
        return "兵";
    }
}


