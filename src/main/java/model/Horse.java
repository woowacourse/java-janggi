package model;

import java.util.List;

public class Horse extends Piece {

    public Horse(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<List<Position>> allDirection = List.of(
            departure.findUpAndUpRight(arrival),
            departure.findUpAndUpLeft(arrival),
            departure.findDownAndDownRight(arrival),
            departure.findDownAndDownLeft(arrival),
            departure.findLeftAndUpLeft(arrival),
            departure.findLeftAndDownLeft(arrival),
            departure.findRightAndUpRight(arrival),
            departure.findRightAndDownRight(arrival));
        return allDirection.stream()
            .filter(direction -> !direction.isEmpty())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로는 이동할 수 없습니다."));
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "馬";
        }
        return "마";
    }
}
