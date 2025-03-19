package model;

import java.util.List;

public class Cannon extends Piece{

    public Cannon(Position position, Team team) {
        super(position, team);
    }

    @Override
    public List<List<Position>> calculateAllDirection() {
        return List.of();
    }
}


