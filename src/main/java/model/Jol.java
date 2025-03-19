package model;

import java.util.List;

public class Jol extends Piece{

    public Jol(Position position) {
        super(position, Team.GREEN);
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of(
            findUpDirection(),
            findLeftDirection(),
            findRightDirection());
    }

    private List<Position> findUpDirection() {
        return List.of(position.changeColumn(-1));
    }

    private List<Position> findLeftDirection() {
        return List.of(position.changeRow(-1));
    }

    private List<Position> findRightDirection() {
        return List.of(position.changeRow(1));
    }
}

