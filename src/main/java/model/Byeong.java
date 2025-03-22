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

    @Override
    public String toString() {
        return "兵";
    }
}


