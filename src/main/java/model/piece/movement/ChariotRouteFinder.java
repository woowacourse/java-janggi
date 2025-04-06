package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.strategy.UnlimitedBasicMoveStrategy;
import model.piece.Castle;
import model.position.Position;

public class ChariotRouteFinder implements RouteFinder {

    private final Castle castle = Castle.getInstance();
    private final UnlimitedBasicMoveStrategy unlimitedBasicMoveStrategy = UnlimitedBasicMoveStrategy.getInstance();
    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);

    @Override
    public List<Position> calculateAllRoute(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements,
            movementsInCastle, departure, arrival);
        return unlimitedBasicMoveStrategy.findRoute(departure, arrival, decidedMovements);
    }
}
