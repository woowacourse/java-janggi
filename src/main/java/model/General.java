package model;

import java.util.Collections;
import java.util.List;

public class General extends Piece {

    public General(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<List<Position>> allDirections = List.of(
            findUpDirection(departure, arrival),
            findDownDirection(departure, arrival),
            findLeftDirection(departure, arrival),
            findRightDirection(departure, arrival));

        return allDirections.stream()
            .filter(direction -> !direction.isEmpty())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로는 이동할 수 없습니다."));
    }

    private List<Position> findUpDirection(Position departure, Position arrival) {
        if (departure.canMoveUp() && departure.moveUp().equals(arrival)) {
            return List.of(departure.moveUp());
        }
        return Collections.emptyList();
    }

    private List<Position> findDownDirection(Position departure, Position arrival) {
        if (departure.canMoveDown() && departure.moveDown().equals(arrival)) {
            return List.of(departure.moveDown());
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftDirection(Position departure, Position arrival) {
        if (departure.canMoveLeft() && departure.moveLeft().equals(arrival)) {
            return List.of(departure.moveLeft());
        }
        return Collections.emptyList();
    }

    private List<Position> findRightDirection(Position departure, Position arrival) {
        if (departure.canMoveRight() && departure.moveRight().equals(arrival)) {
            return List.of(departure.moveRight());
        }
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "漢";
        }
        return "초";
    }
}
