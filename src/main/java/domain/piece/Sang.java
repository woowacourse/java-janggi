package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.DirectionPath;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;

import java.util.List;

import static domain.direction.Direction.*;

public class Sang extends Piece {

    private static final List<DirectionPath> PATHS = List.of(
            DirectionPath.of(NORTH, NORTH_EAST, NORTH_EAST),
            DirectionPath.of(NORTH, NORTH_WEST, NORTH_WEST),
            DirectionPath.of(SOUTH, SOUTH_EAST, SOUTH_EAST),
            DirectionPath.of(SOUTH, SOUTH_WEST, SOUTH_WEST),
            DirectionPath.of(EAST, NORTH_EAST, NORTH_EAST),
            DirectionPath.of(EAST, SOUTH_EAST, SOUTH_EAST),
            DirectionPath.of(WEST, NORTH_WEST, NORTH_WEST),
            DirectionPath.of(WEST, SOUTH_WEST, SOUTH_WEST)
    );

    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new NonStraightPathGenerator(PATHS);

    public Sang(Team team) {
        super(team, PieceType.SANG);
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
