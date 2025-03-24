package move;

import static direction.Movement.LEFT;
import static direction.Movement.RIGHT;
import static direction.Movement.UP;

import direction.Movement;
import direction.Point;
import java.util.List;
import piece.Pieces;

public class SoldierMovement implements MovementRule {

    private static final List<Movement> MOVEMENTS = List.of(LEFT, RIGHT, UP);

    private final int direction;

    public SoldierMovement(int direction) {
        this.direction = direction;
    }

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        return MOVEMENTS.stream()
                .map(movement -> movement.multiply(this.direction))
                .map(from::plus)
                .filter(nextPoint -> nextPoint.equals(to))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다."));
    }
}
