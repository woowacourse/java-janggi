package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.navigator.UnlimitedBasicMoveStrategy;
import model.piece.Castle;
import model.position.Position;

public class CannonDirectionFinder implements DirectionFinder {

    private final Castle castle = Castle.getInstance();
    private final UnlimitedBasicMoveStrategy unlimitedBasicMoveStrategy = UnlimitedBasicMoveStrategy.getInstance();
    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);

    @Override
    public List<Position> calculateAllDirection(Position departure, Position arrival) {
        List<Movement> decidedMovements = castle.decideMovements(movements,
            movementsInCastle, departure, arrival);
        return unlimitedBasicMoveStrategy.find(departure, arrival, decidedMovements);
    }
}
