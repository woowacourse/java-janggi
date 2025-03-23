package move;

import java.util.List;
import java.util.Map;
import piece.Position;
import piece.Route;
import piece.Team;

public class JolMoveBehavior implements MoveBehavior {

    private static final Map<Team, List<Directions>> teamCanMoveDirection;

    static {
        final List<Directions> blueCanMoveDirections = List.of(
                new Directions(
                        List.of(Direction.RIGHT)
                ),
                new Directions(
                        List.of(Direction.LEFT)
                ),
                new Directions(
                        List.of(Direction.UP)
                )
        );
        final List<Directions> redCanMoveDirections = List.of(
                new Directions(
                        List.of(Direction.RIGHT)
                ),
                new Directions(
                        List.of(Direction.LEFT)
                ),
                new Directions(
                        List.of(Direction.DOWN)
                )
        );

        teamCanMoveDirection = Map.of(Team.BLUE, blueCanMoveDirections, Team.RED, redCanMoveDirections);
    }

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        return getLegalRoute(startPosition, endPosition, teamCanMoveDirection.get(team));
    }
}
