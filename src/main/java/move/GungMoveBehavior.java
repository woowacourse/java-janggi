package move;

import java.util.List;
import move.direction.Direction;
import move.direction.Directions;
import piece.PieceType;
import piece.Team;
import piece.position.JanggiPosition;

public class GungMoveBehavior extends JanggiMoveBehavior {

    private final List<Directions> canMoveDirections = List.of(
            new Directions(List.of(Direction.RIGHT)),
            new Directions(List.of(Direction.LEFT)),
            new Directions(List.of(Direction.DOWN)),
            new Directions(List.of(Direction.UP))
    );

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        if (!isInsideGungsungCase(startPosition, endPosition)) {
            throw new InvalidMovePosition();
        }
        return calculateLegalRoute(startPosition, endPosition, canMoveDirections);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GUNG;
    }
}
