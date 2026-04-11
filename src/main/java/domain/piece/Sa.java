package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.DirectionPath;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PalaceBoundPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;

import java.util.List;

import static domain.direction.Direction.*;

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

    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator PATH_GENERATOR =
            new PalaceBoundPathGenerator(new NonStraightPathGenerator(PATHS));

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
