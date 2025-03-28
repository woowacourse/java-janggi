package model.piece;

import static model.Movement.DIAGONAL_UP_LEFT;
import static model.Movement.DIAGONAL_UP_RIGHT;
import static model.Movement.LEFT;
import static model.Movement.RIGHT;
import static model.Movement.UP;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.Team;
import model.position.Position;

public class Jol extends Piece {

    private static final int SCORE = 2;

    private final List<Movement> movements = List.of(UP, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT);
    private final Area area;

    private final LimitedBasicMoveNavigator limitedBasicMoveNavigator;

    public Jol() {
        super(Team.GREEN, SCORE);
        this.area = new Area();
        this.limitedBasicMoveNavigator = new LimitedBasicMoveNavigator();
    }

    @Override
    public String getName() {
        return "졸";
    }

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = decideMovements(departure, arrival);
        return limitedBasicMoveNavigator.find(departure, arrival, decidedMovements);
    }

    private List<Movement> decideMovements(Position departure, Position arrival) {
        List<Movement> decidedMovements = new ArrayList<>(movements);
        if (area.canMoveDiagonal(departure) && area.inCastle(arrival)) {
            decidedMovements.addAll(movementsInCastle);
        }
        return decidedMovements;
    }
}
