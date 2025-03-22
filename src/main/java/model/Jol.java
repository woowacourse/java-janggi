package model;

import java.util.List;

public class Jol extends Piece {

    public Jol() {
        super(Team.GREEN);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<List<Position>> allDirections = List.of(
            departure.findUpDirection(arrival),
            departure.findLeftDirection(arrival),
            departure.findRightDirection(arrival));

        return allDirections.stream()
            .filter(direction -> !direction.isEmpty())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로는 이동할 수 없습니다."));
    }

    @Override
    public String toString() {
        return "졸";
    }
}


