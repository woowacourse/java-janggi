package move;

import java.util.List;
import java.util.Map;
import piece.PieceType;
import piece.Position;
import piece.Route;
import piece.Team;

public class JolMoveBehavior extends MoveBehavior {

    private static final Map<Team, List<Directions>> teamCanMoveDirection;

    static {
        final List<Directions> blueCanMoveDirections = List.of(
                new Directions(List.of(Direction.RIGHT)),
                new Directions(List.of(Direction.LEFT)),
                new Directions(List.of(Direction.DOWN))
        );
        final List<Directions> redCanMoveDirections = List.of(
                new Directions(List.of(Direction.RIGHT)),
                new Directions(List.of(Direction.LEFT)),
                new Directions(List.of(Direction.UP))
        );

        teamCanMoveDirection = Map.of(Team.BLUE, blueCanMoveDirections, Team.RED, redCanMoveDirections);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.JOL;
    }

    @Override
    public Route calculateLegalRoute(Position startPosition, Position endPosition, Team team) {
        return calculateLegalRoute(startPosition, endPosition, teamCanMoveDirection.get(team));
    }
}
