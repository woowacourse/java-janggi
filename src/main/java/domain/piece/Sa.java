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
import domain.pathgenerator.DirectionPath;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.MovementStrategy;
import domain.strategy.PalaceMovementStrategy;
import java.util.List;

public class Sa extends Piece {

    private static final List<DirectionPath> PATHS = List.of(
            DirectionPath.of(NORTH),
            DirectionPath.of(SOUTH),
            DirectionPath.of(EAST),
            DirectionPath.of(WEST),
            DirectionPath.of(NORTH_EAST),
            DirectionPath.of(NORTH_WEST),
            DirectionPath.of(SOUTH_EAST),
            DirectionPath.of(SOUTH_WEST)
    );

    private static final MovementStrategy MOVEMENT_STRATEGY = new PalaceMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new NonStraightPathGenerator(PATHS);

    public Sa(Team team) {
        super(team, PieceType.SA);
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
