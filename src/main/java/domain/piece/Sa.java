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

public class Sa extends Piece {

    private static final List<List<Direction>> PATHS = List.of(
            List.of(NORTH),
            List.of(SOUTH),
            List.of(EAST),
            List.of(WEST),
            List.of(NORTH_EAST),
            List.of(NORTH_WEST),
            List.of(SOUTH_EAST),
            List.of(SOUTH_WEST)
    );

    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new NonStraightPathGenerator(PATHS);

    public Sa(Team team) {
        super(team, PieceType.SA);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return MOVEMENT_STRATEGY;
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return PATH_GENERATOR;
    }

}
