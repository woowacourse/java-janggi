package move;

import java.util.List;
import move.direction.Direction;
import move.direction.Directions;
import piece.PieceType;
import piece.player.Team;
import piece.position.JanggiPosition;

public class SaMoveBehavior extends JanggiMoveBehavior {

    private final List<Directions> canMoveDirections = List.of(
            new Directions(List.of(Direction.RIGHT)),
            new Directions(List.of(Direction.LEFT)),
            new Directions(List.of(Direction.DOWN)),
            new Directions(List.of(Direction.UP))
    );

    private final List<Directions> diagonalCanMoveDirections = List.of(
            new Directions(List.of(Direction.UP_RIGHT)),
            new Directions(List.of(Direction.UP_LEFT)),
            new Directions(List.of(Direction.DOWN_LEFT)),
            new Directions(List.of(Direction.DOWN_RIGHT))
    );

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        if (!isInsideGungsungCase(startPosition, endPosition)) {
            throw new InvalidMovePosition();
        }
        if (isDiagonalGungsungCase(startPosition, endPosition)) {
            return calculateLegalRoute(startPosition, endPosition, diagonalCanMoveDirections);
        }
        return calculateLegalRoute(startPosition, endPosition, canMoveDirections);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.SA;
    }
}
