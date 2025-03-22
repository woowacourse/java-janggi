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
        return List.of();
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "車";
        }
        return "차";
    }
}



