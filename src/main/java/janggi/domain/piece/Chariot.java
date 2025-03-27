package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.direction.Direction;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.HashSet;
import java.util.Set;

public class Chariot extends Piece {

    private static final int CHARIOT_SCORE = 13;

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public double getScore() {
        return CHARIOT_SCORE;
    }

    @Override
    public Set<Route> calculateIndependentRoutes() {
        final Set<Route> rawRoutes = new HashSet<>();

        for (final Direction direction : Direction.getStraightDirections()) {
            rawRoutes.addAll(generateRoutesInDirection(direction));
        }
        return rawRoutes;
    }
}
