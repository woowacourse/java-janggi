package model;

import java.util.List;


public class Chariot extends Piece{

    public Chariot(Position position, Team team) {
        super(position, team);
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of();
    }
}

