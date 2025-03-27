package piece;

import direction.Movement;
import direction.Point;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

    private static final Map<Movement, List<Movement>> MOVEMENT_PATH = Map.of(
            Movement.UP_UP_LEFT, List.of(Movement.UP),
            Movement.UP_UP_RIGHT, List.of(Movement.UP),
            Movement.RIGHT_RIGHT_UP, List.of(Movement.RIGHT),
            Movement.RIGHT_RIGHT_DOWN, List.of(Movement.RIGHT),
            Movement.DOWN_DOWN_LEFT, List.of(Movement.DOWN),
            Movement.DOWN_DOWN_RIGHT, List.of(Movement.DOWN),
            Movement.LEFT_LEFT_UP, List.of(Movement.LEFT),
            Movement.LEFT_LEFT_DOWN, List.of(Movement.LEFT)
    );

    public Horse(String nickname, Point current) {
        super(nickname, current);
    }

    @Override
    public void move(final Pieces pieces, final Point destination) {
        Movement destinationMovement = getDestinationMovement(destination);
        List<Movement> movements = MOVEMENT_PATH.get(destinationMovement);

        for (Movement pathMovement : movements) {
            Point nextPoint = current.move(pathMovement);
            validateIsExistPieceInPoint(pieces, nextPoint);
        }

        current = current.move(destinationMovement);
    }

    private Movement getDestinationMovement(Point destination) {
        for (Movement destinationMovement : MOVEMENT_PATH.keySet()) {
            Point predictDestination = current.move(destinationMovement);
            if (predictDestination.equals(destination)) {
                return destinationMovement;
            }
        }

        throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
    }

    private static void validateIsExistPieceInPoint(Pieces pieces, Point nextPoint) {
        if (pieces.isExistPieceIn(nextPoint)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
