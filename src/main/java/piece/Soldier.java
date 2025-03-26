package piece;

import direction.Movement;
import direction.Point;
import java.util.List;
import team.Team;

public class Soldier extends Piece {

    private static final List<Movement> PATH = List.of(Movement.LEFT, Movement.RIGHT, Movement.UP);

    private final Team team;

    public Soldier(String nickname, Point current, Team team) {
        super(nickname, current);
        this.team = team;
    }

    @Override
    public void move(Pieces pieces, Point destination) {
        Movement destinationMovement = getDestinationMovement(destination);
        validateIsExistPieceInPoint(pieces, current.move(destinationMovement));

        current = current.move(destinationMovement);
    }

    private Movement getDestinationMovement(Point destination) {
        for (Movement destinationMovement : PATH) {
            if (destinationMovement.equals(Movement.UP) && team.equals(Team.HAN)) {
                destinationMovement = Movement.DOWN;
            }

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
