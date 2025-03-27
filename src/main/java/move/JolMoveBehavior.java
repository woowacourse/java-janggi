package move;

import java.util.List;
import java.util.Map;
import move.direction.Direction;
import move.direction.Directions;
import piece.PieceType;
import piece.player.Team;
import piece.position.JanggiPosition;

public class JolMoveBehavior extends JanggiMoveBehavior {

    private static final Map<Team, List<Directions>> teamCanMoveDirection;
    private static final Map<Team, List<Directions>> teamCanMoveDiagonalDirection;

    static {
        final List<Directions> redMoveDirections = List.of(
                new Directions(List.of(Direction.RIGHT)),
                new Directions(List.of(Direction.LEFT)),
                new Directions(List.of(Direction.UP))
        );
        final List<Directions> blueMoveDirections = List.of(
                new Directions(List.of(Direction.RIGHT)),
                new Directions(List.of(Direction.LEFT)),
                new Directions(List.of(Direction.DOWN))
        );

        final List<Directions> redDiagonalMoveDirections = List.of(
                new Directions(List.of(Direction.UP_RIGHT)),
                new Directions(List.of(Direction.UP_LEFT))
        );

        final List<Directions> blueDiagonalCanMoveDirections = List.of(
                new Directions(List.of(Direction.DOWN_LEFT)),
                new Directions(List.of(Direction.DOWN_RIGHT))
        );

        teamCanMoveDirection = Map.of(Team.BLUE, blueMoveDirections, Team.RED, redMoveDirections);
        teamCanMoveDiagonalDirection = Map.of(Team.BLUE, blueDiagonalCanMoveDirections, Team.RED,
                redDiagonalMoveDirections);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.JOL;
    }

    @Override
    public List<JanggiPosition> calculateLegalRoute(JanggiPosition startPosition, JanggiPosition endPosition,
                                                    Team team) {
        if (isDiagonalGungsungCase(startPosition, endPosition)) {
            return calculateLegalRoute(startPosition, endPosition, teamCanMoveDiagonalDirection.get(team));
        }
        return calculateLegalRoute(startPosition, endPosition, teamCanMoveDirection.get(team));
    }
}

