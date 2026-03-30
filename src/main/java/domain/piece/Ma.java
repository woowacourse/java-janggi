package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.NORTH_EAST;
import static domain.direction.Direction.NORTH_WEST;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.SOUTH_EAST;
import static domain.direction.Direction.SOUTH_WEST;
import static domain.direction.Direction.WEST;

import domain.board.PathPieces;
import domain.direction.Direction;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;
import java.util.List;

public class Ma extends Piece {

    private static final List<List<Direction>> PATHS = List.of(
            List.of(NORTH, NORTH_EAST),
            List.of(NORTH, NORTH_WEST),
            List.of(SOUTH, SOUTH_EAST),
            List.of(SOUTH, SOUTH_WEST),
            List.of(EAST, NORTH_EAST),
            List.of(EAST, SOUTH_EAST),
            List.of(WEST, NORTH_WEST),
            List.of(WEST, SOUTH_WEST)
    );

    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new NonStraightPathGenerator(PATHS);

    public Ma(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return PATH_GENERATOR.calculatePath(source, destination);
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return MOVEMENT_STRATEGY.validatePath(pathPieces);
    }
}
