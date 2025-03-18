package model;

import java.util.List;

public class Guard extends Piece {

    public Guard(Position position, Team team) {
        super(position, team);
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public List<Position> calculateMovablePositions() {
        return null;
    }
}
