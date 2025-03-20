package strategy;

import java.util.List;
import java.util.Map;
import piece.Position;
import piece.Route;
import piece.Team;

public class JolMoveStrategy implements MoveStrategy {

    private static final Map<Team, List<Route>> teamCanMoveDirection;

    static {
        final List<Route> blueCanMoveDirections = List.of(
                new Route(
                        List.of(new Position(0, 1))
                ),
                new Route(
                        List.of(new Position(0, -1))
                ),
                new Route(
                        List.of(new Position(1, 0))
                )
        );
        final List<Route> redCanMoveDirections = List.of(
                new Route(
                        List.of(new Position(0, 1))
                ),
                new Route(
                        List.of(new Position(0, -1))
                ),
                new Route(
                        List.of(new Position(-1, 0))
                )
        );

        teamCanMoveDirection = Map.of(Team.BLUE, blueCanMoveDirections, Team.RED, redCanMoveDirections);
    }

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        return getLegalRoute(startPosition, endPosition, teamCanMoveDirection.get(team));
    }
}
