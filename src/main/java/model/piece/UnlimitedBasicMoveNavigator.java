package model.piece;

import java.util.ArrayList;
import java.util.List;
import model.Movement;
import model.position.Position;

public class UnlimitedBasicMoveNavigator {

    private static final UnlimitedBasicMoveNavigator INSTANCE = new UnlimitedBasicMoveNavigator();

    private UnlimitedBasicMoveNavigator() {}

    public static UnlimitedBasicMoveNavigator getInstance() {
        return INSTANCE;
    }

    public List<Position> find(Position departure, Position arrival, List<Movement> movements) {
        return movements.stream()
            .map(movement -> findDirectionByMovement(departure, arrival, movement))
            .filter(moveDirections -> moveDirections.contains(arrival))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치로 이동할 수 없습니다."));
    }

    private List<Position> findDirectionByMovement(Position departure, Position arrival, Movement movement) {
        List<Position> movedPosition = new ArrayList<>();
        while (departure.canMove(movement) && !movedPosition.contains(arrival)) {
            departure = departure.move(movement);
            movedPosition.add(departure);
        }
        return movedPosition;
    }
}
