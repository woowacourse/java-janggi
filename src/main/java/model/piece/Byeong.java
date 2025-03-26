package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Byeong extends Piece {

    private final List<Movement> movements = List.of(DOWN, LEFT, RIGHT);
    private final MovableNavigator movableNavigator;

    public Byeong() {

        super(Team.RED);
        movableNavigator = new LimitedCrossNavigator();
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        return "兵";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return movableNavigator.find(departure, arrival, movements);
    }
}
