package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Chariot extends Piece {

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final Area area;
    private final UnLimitedBasicMoveNavigator unLimitedBasicMoveNavigator;

    public Chariot(Team team) {
        super(team);
        this.area = new Area();
        this.unLimitedBasicMoveNavigator = new UnLimitedBasicMoveNavigator();
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
        List<Movement> decidedMovements = decideMovements(departure, arrival);
        return unLimitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }

    private List<Movement> decideMovements(Position departure, Position arrival) {
        List<Movement> decidedMovements = new ArrayList<>(movements);
        if (area.canMoveDiagonal(departure) && area.inCastle(arrival)) {
            decidedMovements.addAll(movementsInCastle);
        }
        return decidedMovements;
    }
}
