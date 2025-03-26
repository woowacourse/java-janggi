package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class General extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final MovableNavigator movableNavigator;

    public General(Team team) {
        super(team);
        movableNavigator = new LimitedCrossNavigator();
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "漢";
        }
        return "초";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return movableNavigator.find(departure, arrival, movements);
    }
}
