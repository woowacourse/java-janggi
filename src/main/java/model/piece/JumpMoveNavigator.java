package model.piece;

import java.util.List;
import model.Movement;
import model.position.Position;

public class JumpMoveNavigator {

    public List<Position> find(Position departure, Position arrival, List<List<Movement>> movements) {
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
