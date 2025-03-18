package model;

import java.util.List;

public class Byeong extends Piece{

    public Byeong(Position position) {
        super(position, Team.RED);
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
