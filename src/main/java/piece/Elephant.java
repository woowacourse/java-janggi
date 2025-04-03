package piece;

import direction.Movement;
import direction.Point;
import java.util.List;
import java.util.Map;
import team.Team;

public class Elephant extends Piece {

    private static final Map<Movement, List<Movement>> MOVEMENT_PATH = Map.of(
            Movement.LEFT_LEFT_LEFT_UP_UP, List.of(Movement.LEFT, Movement.LEFT_LEFT_UP),
            Movement.LEFT_LEFT_LEFT_DOWN_DOWN, List.of(Movement.LEFT, Movement.LEFT_LEFT_DOWN),
            Movement.DOWN_DOWN_DOWN_LEFT_LEFT, List.of(Movement.DOWN, Movement.DOWN_DOWN_LEFT),
            Movement.DOWN_DOWN_DOWN_RIGHT_RIGHT, List.of(Movement.DOWN, Movement.DOWN_DOWN_RIGHT),
            Movement.RIGHT_RIGHT_RIGHT_UP_UP, List.of(Movement.RIGHT, Movement.RIGHT_RIGHT_UP),
            Movement.RIGHT_RIGHT_RIGHT_DOWN_DOWN, List.of(Movement.RIGHT, Movement.RIGHT_RIGHT_DOWN),
            Movement.UP_UP_UP_LEFT_LEFT, List.of(Movement.UP, Movement.UP_UP_LEFT),
            Movement.UP_UP_UP_RIGHT_RIGHT, List.of(Movement.UP, Movement.UP_UP_RIGHT)
    );

    public Elephant(Team team, Point current) {
        super(PieceType.ELEPHANT, team, current);
    }

    @Override
    public void move(final Pieces allPieces, final Point destination) {
        Movement destinationMovement = getDestinationMovement(destination);
        List<Movement> movements = MOVEMENT_PATH.get(destinationMovement);

        for (Movement pathMovement : movements) {
            Point nextPoint = current.move(pathMovement);
            validateIsExistPieceInPoint(allPieces, nextPoint);
        }

        current = current.move(destinationMovement);
    }

    @Override
    public int score() {
        return 3;
    }

    private Movement getDestinationMovement(final Point destination) {
        for (Movement destinationMovement : MOVEMENT_PATH.keySet()) {
            Point predictDestination = current.move(destinationMovement);
            if (predictDestination.equals(destination)) {
                return destinationMovement;
            }
        }

        throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
    }

    private void validateIsExistPieceInPoint(final Pieces pieces, Point nextPoint) {
        if (pieces.isExistPieceInPoint(nextPoint)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
