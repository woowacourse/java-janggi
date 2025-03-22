package model;

import java.util.Collections;
import java.util.List;

public class Guard extends Piece {

    public Guard(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<List<Position>> allDirections = List.of(
            departure.findUpDirection(arrival),
            departure.findDownDirection(arrival),
            departure.findLeftDirection(arrival),
            departure.findRightDirection(arrival));

        return allDirections.stream()
            .filter(direction -> !direction.isEmpty())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로는 이동할 수 없습니다."));
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "士";
        }
        return "사";
    }
}


