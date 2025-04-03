package piece;

import direction.Movement;
import direction.Point;
import java.util.List;
import team.Team;

public class Guard extends Piece {

    private static final List<Movement> PATH = List.of(Movement.LEFT, Movement.RIGHT, Movement.UP, Movement.DOWN,
            Movement.LEFT_UP, Movement.LEFT_DOWN, Movement.RIGHT_UP, Movement.RIGHT_DOWN);

    public Guard(Team team, Point current) {
        super(PieceType.GUARD, team, current);
    }

    @Override
    public void move(final Pieces allPieces, final Point destination) {
        Movement destinationMovement = getDestinationMovement(destination);
        validateIsExistPieceInPoint(allPieces, current.move(destinationMovement));

        validateOnlyMoveInPalace(destination);
        if (destinationMovement.isDiagonalMove()) {
            validatePossibleDiagonalMovePoint();
        }

        current = current.move(destinationMovement);
    }

    private void validateOnlyMoveInPalace(Point destination) {
        if (!destination.isPalace()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    private void validatePossibleDiagonalMovePoint() {
        if ((!current.isPalaceCenter() && !current.isPalaceCorner())) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public int score() {
        return 3;
    }

    private Movement getDestinationMovement(final Point destination) {
        for (Movement destinationMovement : PATH) {
            Point predictDestination = current.move(destinationMovement);
            if (predictDestination.equals(destination)) {
                return destinationMovement;
            }
        }

        throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
    }

    private void validateIsExistPieceInPoint(final Pieces pieces, final Point nextPoint) {
        if (pieces.isExistPieceInPointWithTeam(nextPoint, team)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
