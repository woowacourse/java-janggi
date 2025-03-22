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
        return List.of();
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "馬";
        }
        return "마";
    }
}
