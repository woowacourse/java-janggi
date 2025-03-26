package model.piece;

import static model.Movement.*;

import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Cannon extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final UnLimitedBasicMoveNavigator unLimitedBasicMoveNavigator;

    public Cannon(Team team) {
        super(team);
        this.unLimitedBasicMoveNavigator = new UnLimitedBasicMoveNavigator();
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public String getName() {
        if (getTeam() == Team.RED) {
            return "包";
        }
        return "포";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        return unLimitedBasicMoveNavigator.find(departure, arrival, movements);
    }
}
