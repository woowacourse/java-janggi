package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Chariot extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final MovableNavigator movableNavigator;

    public Chariot(Team team) {
        super(team);
        movableNavigator = new UnLimitedCrossNavigator();
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "車";
        }
        return "차";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return movableNavigator.find(departure, arrival, movements);
    }
}
