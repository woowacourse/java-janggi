package model.strategy;

import java.util.List;
import model.position.Movement;
import model.position.Position;

/***
 * Byeong, Jol, General, Guard가 사용한다.
 * 한 칸씩 움직이는 기물들이 사용한다.
 */
public class LimitedBasicMoveStrategy {

    private static final LimitedBasicMoveStrategy INSTANCE = new LimitedBasicMoveStrategy();

    private LimitedBasicMoveStrategy() {}

    public static LimitedBasicMoveStrategy getInstance() {
        return INSTANCE;
    }

    public List<Position> findRoute(Position departure, Position arrival, List<Movement> movements) {
        List<Position> arrivedDirection = findDirectionOfArrival(departure, arrival, movements);
        if (arrivedDirection.isEmpty()) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return arrivedDirection;
    }

    private List<Position> findDirectionOfArrival(Position departure, Position arrival, List<Movement> movements){
        return movements.stream()
            .filter(departure::canMove)
            .filter(movement -> departure.move(movement).equals(arrival))
            .map(departure::move)
            .toList();
    }
}
