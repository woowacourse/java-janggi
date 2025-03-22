package model;

import java.util.Collections;
import java.util.List;

public class Byeong extends Piece {

    public Byeong() {
        super(Team.RED);
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
        return "兵";
    }
}


