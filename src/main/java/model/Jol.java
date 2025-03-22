package model;

import java.util.List;

public class Jol extends Piece {

    public Jol() {
        super(Team.GREEN);
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
        return "졸";
    }
}


