package model.piece.movement;

import static model.position.Movement.*;

import java.util.List;
import model.position.Movement;
import model.strategy.LimitedBasicMoveStrategy;
import model.piece.Castle;
import model.position.Position;

public class GeneralRouteFinder implements RouteFinder {

    private final Castle castle = Castle.getInstance();
    private final LimitedBasicMoveStrategy limitedBasicMoveStrategy = LimitedBasicMoveStrategy.getInstance();
    private final List<Movement> movements = List.of(UP, DOWN, LEFT, RIGHT);
    private final List<Movement> movementsInCastle = List.of(
        DIAGONAL_UP_LEFT, DIAGONAL_UP_RIGHT,
        DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_RIGHT);

    @Override
    public List<Position> calculateAllRoute(Position departure, Position arrival) {
        validateOutOfCastle(arrival);
        List<Movement> decidedMovements = castle.decideMovements(movements, movementsInCastle, departure, arrival);
        return limitedBasicMoveStrategy.findRoute(departure, arrival, decidedMovements);
    }

    private void validateOutOfCastle(Position arrival) {
        if (!castle.inCastle(arrival)) {
            throw new IllegalArgumentException("해당 기물은 궁성 밖으로 이동할 수 없습니다.");
        }
    }
}
