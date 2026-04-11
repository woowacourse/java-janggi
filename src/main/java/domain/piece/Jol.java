package domain.piece;

import domain.board.PathPieces;
import domain.pathgenerator.DirectionPath;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PalaceConstrainedPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;

import java.util.List;

import static domain.direction.Direction.*;

public class Jol extends Piece {
    private static final List<DirectionPath> CHO_PATHS = List.of(
            DirectionPath.of(NORTH),
            DirectionPath.of(EAST),
            DirectionPath.of(WEST),
            DirectionPath.of(NORTH_EAST),
            DirectionPath.of(NORTH_WEST)
    );

    private static final List<DirectionPath> HAN_PATHS = List.of(
            DirectionPath.of(SOUTH),
            DirectionPath.of(EAST),
            DirectionPath.of(WEST),
            DirectionPath.of(SOUTH_EAST),
            DirectionPath.of(SOUTH_WEST)
    );

    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator CHO_PATH_GENERATOR =
            new PalaceConstrainedPathGenerator(new NonStraightPathGenerator(CHO_PATHS));
    private static final PathGenerator HAN_PATH_GENERATOR =
            new PalaceConstrainedPathGenerator(new NonStraightPathGenerator(HAN_PATHS));

    public Jol(Team team) {
        super(team, PieceType.JOL);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return selectGenerator().calculatePath(source, destination);
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return MOVEMENT_STRATEGY.validatePath(pathPieces);
    }

    private PathGenerator selectGenerator() {
        if (getTeam().isCho()) {
            return CHO_PATH_GENERATOR;
        }
        return HAN_PATH_GENERATOR;
    }
}
