package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.strategy.LimitedBasicMoveStrategy;
import model.piece.Castle;
import model.position.Position;

public class ByeongRouteFinder implements RouteFinder {

    private final List<Movement> movements = List.of(DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);
    private final Castle castle = Castle.getInstance();
    private final LimitedBasicMoveStrategy limitedBasicMoveStrategy = LimitedBasicMoveStrategy.getInstance();

    @Override
    public List<Position> calculateAllRoute(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements,
            movementsInCastle, departure, arrival);
        return limitedBasicMoveStrategy.findRoute(departure, arrival, decidedMovements);
    }
}
