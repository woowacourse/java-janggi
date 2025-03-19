package model;

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
        return List.of(
            position.changeColumn(-1),
            position.changeColumnAndRow(-2, -1),
            position.changeColumnAndRow(-3, -2));
    }

    private List<Position> findUpRight() {
        return List.of(
            position.changeColumn(-1),
            position.changeColumnAndRow(-2, 1),
            position.changeColumnAndRow(-3, 2));
    }

    private List<Position> findLeftUp() {
        return List.of(
            position.changeRow(-1),
            position.changeColumnAndRow(-1, -2),
            position.changeColumnAndRow(-2, -3));
    }

    private List<Position> findLeftDown() {
        return List.of(
            position.changeRow(-1),
            position.changeColumnAndRow(1, -2),
            position.changeColumnAndRow(2, -3));
    }

    private List<Position> findRightUp() {
        return List.of(
            position.changeRow(1),
            position.changeColumnAndRow(-1, 2),
            position.changeColumnAndRow(-2, 3));
    }

    private List<Position> findRightDown() {
        return List.of(
            position.changeRow(1),
            position.changeColumnAndRow(1, 2),
            position.changeColumnAndRow(2, 3));
    }

    private List<Position> findDownLeft() {
        return List.of(
            position.changeColumn(1),
            position.changeColumnAndRow(2, -1),
            position.changeColumnAndRow(3, -2));
    }

    private List<Position> findDownRight() {
        return List.of(
            position.changeRow(1),
            position.changeColumnAndRow(2, 1),
            position.changeColumnAndRow(3, 2));
    }
}
