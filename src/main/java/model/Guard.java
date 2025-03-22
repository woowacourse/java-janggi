package model;

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
        return List.of();
    }
/*
    private List<Position> findUpDirection(Position position) {
        if (position.canChangeOfColumn(-1)) {
            return List.of(position.changeColumn(-1));
        }
        return Collections.emptyList();
    }

    private List<Position> findDownDirection(Position position) {
        if (position.canChangeOfColumn(1)) {
            return List.of(position.changeColumn(1));
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftDirection(Position position) {
        if (position.canChangeOfRow(-1)) {
            return List.of(position.changeRow(-1));
        }
        return Collections.emptyList();
    }

    private List<Position> findRightDirection(Position position) {
        if (position.canChangeOfRow(1)) {
            return List.of(position.changeRow(1));
        }
        return Collections.emptyList();
    }

    */
    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "士";
        }
        return "사";
    }
}


