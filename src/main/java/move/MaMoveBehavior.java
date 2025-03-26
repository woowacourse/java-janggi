package move;

import java.util.List;
import move.direction.Direction;
import move.direction.Directions;
import piece.PieceType;
import piece.player.Team;
import piece.position.JanggiPosition;

public class MaMoveBehavior extends JanggiMoveBehavior {

    private static final List<Directions> canMoveDirections = List.of(
            new Directions(List.of(Direction.UP, Direction.UP_LEFT)),
            new Directions(List.of(Direction.UP, Direction.UP_RIGHT)),
            new Directions(List.of(Direction.DOWN, Direction.DOWN_LEFT)),
            new Directions(List.of(Direction.DOWN, Direction.DOWN_RIGHT)),
            new Directions(List.of(Direction.RIGHT, Direction.UP_RIGHT)),
            new Directions(List.of(Direction.RIGHT, Direction.DOWN_RIGHT)),
            new Directions(List.of(Direction.LEFT, Direction.UP_LEFT)),
            new Directions(List.of(Direction.LEFT, Direction.DOWN_LEFT)
            )
    );

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        return calculateLegalRoute(startPosition, endPosition, canMoveDirections);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.MA;
    }
}
