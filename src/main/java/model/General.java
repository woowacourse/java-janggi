package model;

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
        return List.of();
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "漢";
        }
        return "초";
    }
}
