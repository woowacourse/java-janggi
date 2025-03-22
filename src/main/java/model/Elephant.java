package model;

import java.util.Collections;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(Team team) {
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
            return "象";
        }
        return "상";
    }
}


