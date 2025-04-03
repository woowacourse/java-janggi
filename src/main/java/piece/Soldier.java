package piece;

import direction.Movement;
import direction.Point;
import java.util.List;
import team.Team;

public class Soldier extends Piece {

    private static final List<Movement> PATH = List.of(Movement.LEFT, Movement.RIGHT, Movement.UP,
        Movement.LEFT_UP, Movement.RIGHT_UP);

    public Soldier(Team team, Point current) {
        super(PieceType.SOLDIER, team, current);
    }

    @Override
    public void move(final Pieces allPieces, final Point destination) {
        Movement destinationMovement = getDestinationMovement(destination);

        if (destinationMovement.isDiagonalMove()) {
            validatePossibleDiagonalMovePoint();
        }

        current = current.move(destinationMovement);
    }

    private void validatePossibleDiagonalMovePoint() {
        if ((!current.isPalaceCenter() && !current.isPalaceCorner())) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public int score() {
        return 2;
    }

    private Movement getDestinationMovement(final Point destination) {
        for (Movement destinationMovement : PATH) {
            if (destinationMovement.equals(Movement.UP) && team.equals(Team.HAN)) {
                destinationMovement = Movement.DOWN;
            }

            if (destinationMovement.equals(Movement.LEFT_UP) && team.equals(Team.HAN)) {
                destinationMovement = Movement.LEFT_DOWN;
            }

            if (destinationMovement.equals(Movement.RIGHT_UP) && team.equals(Team.HAN)) {
                destinationMovement = Movement.RIGHT_DOWN;
            }

            Point predictDestination = current.move(destinationMovement);
            if (predictDestination.equals(destination)) {
                return destinationMovement;
            }
        }

        throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
    }
}
