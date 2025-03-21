package model;

import java.util.Collections;
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
    public List<List<Position>> calculateAllDirection(Position position) {
        return List.of(
            findUpLeft(position), findUpRight(position),
            findLeftUp(position), findLeftDown(position),
            findRightUp(position), findRightDown(position),
            findDownLeft(position), findDownRight(position));
    }

    private List<Position> findUpLeft(Position position) {
        if (position.canChangeOfColumn(-1) && position.canChangeOfColumnAndRow(-2, -1)) {
            return List.of(
                position.changeColumn(-1),
                position.changeColumnAndRow(-2, -1));
        }
        return Collections.emptyList();
    }

    private List<Position> findUpRight(Position position) {
        if (position.canChangeOfColumn(-1) && position.canChangeOfColumnAndRow(-2, 1)) {
            return List.of(
                position.changeColumn(-1),
                position.changeColumnAndRow(-2, 1));
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftUp(Position position) {
        if (position.canChangeOfRow(-1) && position.canChangeOfColumnAndRow(-1, -2)) {
            return List.of(
                position.changeRow(-1),
                position.changeColumnAndRow(-1, -2));
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftDown(Position position) {
        if (position.canChangeOfRow(-1) && position.canChangeOfColumnAndRow(1, -2)) {
            return List.of(
                position.changeRow(-1),
                position.changeColumnAndRow(1, -2));
        }
        return Collections.emptyList();
    }

    private List<Position> findRightUp(Position position) {
        if (position.canChangeOfRow(1) && position.canChangeOfColumnAndRow(-1, 2)) {
            return List.of(
                position.changeRow(1),
                position.changeColumnAndRow(-1, 2));
        }
        return Collections.emptyList();
    }

    private List<Position> findRightDown(Position position) {
        if (position.canChangeOfRow(1) && position.canChangeOfColumnAndRow(1, 2)) {
            return List.of(
                position.changeRow(1),
                position.changeColumnAndRow(1, 2));
        }
        return Collections.emptyList();
    }

    private List<Position> findDownLeft(Position position) {
        if (position.canChangeOfColumn(1) && position.canChangeOfColumnAndRow(2, -1)) {
            return List.of(
                position.changeColumn(1),
                position.changeColumnAndRow(2, -1));
        }
        return Collections.emptyList();
    }

    private List<Position> findDownRight(Position position) {
        if (position.canChangeOfColumn(1) && position.canChangeOfColumnAndRow(2, 1)) {
            return List.of(
                position.changeColumn(1),
                position.changeColumnAndRow(2, 1));
        }
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        if (getTeam() == Team.RED) {
            return "馬";
        }
        return "마";
    }
}
