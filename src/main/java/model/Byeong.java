package model;

import java.util.List;

public class Byeong extends Piece{

    public Byeong(Position position) {
        super(position, Team.RED);
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of(
            findDownDirection(),
            findLeftDirection(),
            findRightDirection());
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
