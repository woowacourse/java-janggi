package model;

import java.util.Collections;
import java.util.List;

public class Byeong extends Piece{

    public Byeong(Position position) {
        super(position, Team.RED);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of(
            findDownDirection(),
            findLeftDirection(),
            findRightDirection());
    }

    private List<Position> findDownDirection() {
        if (position.canChangeOfColumn(1)) {
            return List.of(position.changeColumn(1));
        }
        return Collections.emptyList();
    }

    private List<Position> findLeftDirection() {
        if (position.canChangeOfRow(-1)) {
            return List.of(position.changeRow(-1));
        }
        return Collections.emptyList();
    }

    private List<Position> findRightDirection() {
        if (position.canChangeOfRow(1)) {
            return List.of(position.changeRow(1));
        }
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "兵";
    }
}
