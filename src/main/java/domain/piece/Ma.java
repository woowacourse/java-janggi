package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.NORTH_EAST;
import static domain.direction.Direction.NORTH_WEST;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.SOUTH_EAST;
import static domain.direction.Direction.SOUTH_WEST;
import static domain.direction.Direction.WEST;

import domain.direction.Direction;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;
import java.util.List;

public class Ma extends Piece {

    private static final List<List<Direction>> paths = List.of(
            List.of(NORTH, NORTH_EAST),
            List.of(NORTH, NORTH_WEST),
            List.of(SOUTH, SOUTH_EAST),
            List.of(SOUTH, SOUTH_WEST),
            List.of(EAST, NORTH_EAST),
            List.of(EAST, SOUTH_EAST),
            List.of(WEST, NORTH_WEST),
            List.of(WEST, SOUTH_WEST)
    );

    public Ma(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return new BlockedMovementStrategy();
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return new NonStraightPathGenerator(paths);
    }
}
