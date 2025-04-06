package model.strategy;

import java.util.List;
import model.position.Movement;
import model.position.Position;

public class JumpMoveStrategy {

    private static final JumpMoveStrategy INSTANCE = new JumpMoveStrategy();

    private JumpMoveStrategy() {}

    public static JumpMoveStrategy getInstance() {
        return INSTANCE;
    }

    public List<Position> findRoute(Position departure, Position arrival, List<List<Movement>> movements) {
        List<Movement> canArriveMovements = findCanArriveMovements(departure, arrival, movements);
        return findDirectionOfArrival(departure, canArriveMovements, movements);
    }

    private List<Position> findDirectionOfArrival(Position departure, List<Movement> canArriveMovements, List<List<Movement>> movements) {
        return canArriveMovements.stream()
            .map(departure::move)
            .toList();
    }

    private List<Movement> findCanArriveMovements(Position departure, Position arrival, List<List<Movement>> movements) {
        return movements.stream()
            .filter(movement -> departure.canMove(movement.getLast()))
            .filter(movement -> departure.move(movement.getLast()).equals(arrival))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }
}
