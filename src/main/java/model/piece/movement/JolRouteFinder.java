package model.piece.movement;

import static model.position.Movement.DIAGONAL_UP_LEFT;
import static model.position.Movement.DIAGONAL_UP_RIGHT;
import static model.position.Movement.LEFT;
import static model.position.Movement.RIGHT;
import static model.position.Movement.UP;

import java.util.List;
import model.position.Movement;
import model.strategy.LimitedBasicMoveStrategy;
import model.piece.Castle;
import model.position.Position;

public class JolRouteFinder implements RouteFinder {

    private final Castle castle = Castle.getInstance();
    private final LimitedBasicMoveStrategy limitedBasicMoveStrategy = LimitedBasicMoveStrategy.getInstance();
    private final List<Movement> movements = List.of(UP, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT);

    @Override
    public List<Position> calculateAllRoute(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements, movementsInCastle,
            departure, arrival);
        return limitedBasicMoveStrategy.findRoute(departure, arrival, decidedMovements);
    }
}
