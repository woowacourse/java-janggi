package model;

import java.util.List;


public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<List<Position>> allDirection = List.of(
            departure.findUpDirectionUntilEnd(arrival),
            departure.findDownDirectionUntilEnd(arrival),
            departure.findLeftDirectionUntilEnd(arrival),
            departure.findRightDirectionUntilEnd(arrival)
        );

        return allDirection.stream()
            .filter(direction -> !direction.isEmpty())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로는 이동할 수 없습니다."));
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "車";
        }
        return "차";
    }
}



