package model.piece;

import static model.Movement.*;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Cannon extends Piece {

    private static final int SCORE = 7;
    private static final String TYPE = "CANNON";

    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final UnLimitedBasicMoveNavigator unLimitedBasicMoveNavigator;
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final Area area;

    public Cannon(Team team) {
        super(team, SCORE, TYPE);
        this.area = new Area();
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
