package model;

import java.util.List;

public class General extends Piece {

    public General(Position position, Team team) {
        super(position, team);
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of(
            findUpDirection(),
            findDownDirection(),
            findLeftDirection(),
            findRightDirection());
    }

    private List<Position> findUpDirection() {
        return List.of(position.changeColumn(-1));
    }

    private List<Position> findDownDirection() {
        return List.of(position.changeColumn(1));
    }

    private List<Position> findLeftDirection() {
        return List.of(position.changeRow(-1));
    }

    private List<Position> findRightDirection() {
        return List.of(position.changeRow(1));
    }
}
