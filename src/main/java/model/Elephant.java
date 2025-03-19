package model;

import java.util.Collections;
import java.util.List;

public class Elephant extends Piece{

    public Elephant(Position position, Team team) {
        super(position, team);
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of(
            findUpLeft(), findUpRight(),
            findLeftUp(), findLeftDown(),
            findRightUp(), findRightDown(),
            findDownLeft(), findDownRight());
    }

    private List<Position> findUpLeft() {
        if (position.canChangeOfColumn(-1) && position.canChangeOfColumnAndRow(-2, -1) && position.canChangeOfColumnAndRow(-3, -2)) {
            return List.of(
                position.changeColumn(-1),
                position.changeColumnAndRow(-2, -1),
                position.changeColumnAndRow(-3, -2));
        }
        return Collections.emptyList();
    }

    private List<Position> findUpRight() {
        if (position.canChangeOfColumn(-1) && position.canChangeOfColumnAndRow(-2, 1) && position.canChangeOfColumnAndRow(-3, 2)) {
            return List.of(
                position.changeColumn(-1),
                position.changeColumnAndRow(-2, 1),
                position.changeColumnAndRow(-3, 2));
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftUp() {
        if (position.canChangeOfRow(-1) && position.canChangeOfColumnAndRow(-1, -2) && position.canChangeOfColumnAndRow(-2, -3)) {
            return List.of(
                position.changeRow(-1),
                position.changeColumnAndRow(-1, -2),
                position.changeColumnAndRow(-2, -3));
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftDown() {
        if (position.canChangeOfRow(-1) && position.canChangeOfColumnAndRow(1, -2) && position.canChangeOfColumnAndRow(2, -3)) {
            return List.of(
                position.changeRow(-1),
                position.changeColumnAndRow(1, -2),
                position.changeColumnAndRow(2, -3));
        }
        return Collections.emptyList();
    }

    private List<Position> findRightUp() {
        if (position.canChangeOfRow(1) && position.canChangeOfColumnAndRow(-1, 2) && position.canChangeOfColumnAndRow(-2, 3)) {
            return List.of(
                position.changeRow(1),
                position.changeColumnAndRow(-1, 2),
                position.changeColumnAndRow(-2, 3));
        }
        return Collections.emptyList();
    }

    private List<Position> findRightDown() {
        if (position.canChangeOfRow(1) && position.canChangeOfColumnAndRow(1, 2) && position.canChangeOfColumnAndRow(2, 3)) {
            return List.of(
                position.changeRow(1),
                position.changeColumnAndRow(1, 2),
                position.changeColumnAndRow(2, 3));
        }
        return Collections.emptyList();
    }

    private List<Position> findDownLeft() {
        if (position.canChangeOfColumn(1) && position.canChangeOfColumnAndRow(2, -1) && position.canChangeOfColumnAndRow(3, -2)) {
            return List.of(
                position.changeColumn(1),
                position.changeColumnAndRow(2, -1),
                position.changeColumnAndRow(3, -2));
        }
        return Collections.emptyList();
    }

    private List<Position> findDownRight() {
        if (position.canChangeOfColumn(1) && position.canChangeOfColumnAndRow(2, 1) && position.canChangeOfColumnAndRow(3, 2)) {
            return List.of(
                position.changeColumn(1),
                position.changeColumnAndRow(2, 1),
                position.changeColumnAndRow(3, 2));
        }
        return Collections.emptyList();
    }
}
