package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Guard extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Guard(Team team) {
        super(team);
        this.limitedBasicMoveNavigator = new LimitedBasicMoveNavigator();
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "士";
        }
        return "사";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return limitedBasicMoveNavigator.find(departure, arrival, movements);
    }
}
