package model.piece;

import static model.Movement.LEFT;
import static model.Movement.RIGHT;
import static model.Movement.UP;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Jol extends Piece {

    private final List<Movement> movements = List.of(UP, LEFT, RIGHT);
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Jol() {
        super(Team.GREEN);
        this.limitedBasicMoveNavigator = new LimitedBasicMoveNavigator();
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        return "졸";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return limitedBasicMoveNavigator.find(departure, arrival, movements);
    }
}
