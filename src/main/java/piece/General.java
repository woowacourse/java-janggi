package piece;

import direction.Movement;
import direction.Point;
import java.util.List;

public class General extends Piece  {

    private static final List<Movement> PATH = List.of(Movement.LEFT, Movement.RIGHT, Movement.UP, Movement.DOWN);

    public General(String nickname, Point current) {
        super(nickname, current);
    }

    @Override
    public void move(Pieces pieces, Point destination) {
        Movement destinationMovement = getDestinationMovement(destination);
        validateIsExistPieceInPoint(pieces, current.move(destinationMovement));

        current = current.move(destinationMovement);
    }

    private Movement getDestinationMovement(Point destination) {
        for (Movement destinationMovement : PATH) {
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
