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
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;
import java.util.List;

public class Jol extends Piece {
    private static final List<DirectionPath> NON_PALACE_CHO_PATHS = List.of(
            DirectionPath.of(NORTH),
            DirectionPath.of(EAST),
            DirectionPath.of(WEST)
    );

    private static final List<DirectionPath> PALACE_CHO_PATHS = List.of(
            DirectionPath.of(NORTH),
            DirectionPath.of(EAST),
            DirectionPath.of(WEST),
            DirectionPath.of(NORTH_EAST),
            DirectionPath.of(NORTH_WEST)
    );

    private static final List<DirectionPath> NON_PALACE_HAN_PATHS = List.of(
            DirectionPath.of(SOUTH),
            DirectionPath.of(EAST),
            DirectionPath.of(WEST)
    );

    private static final List<DirectionPath> PALACE_HAN_PATHS = List.of(
            DirectionPath.of(SOUTH),
            DirectionPath.of(EAST),
            DirectionPath.of(WEST),
            DirectionPath.of(SOUTH_EAST),
            DirectionPath.of(SOUTH_WEST)
    );

    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator CHO_NON_PALACE_GENERATOR = new NonStraightPathGenerator(NON_PALACE_CHO_PATHS);
    private static final PathGenerator CHO_PALACE_GENERATOR = new NonStraightPathGenerator(PALACE_CHO_PATHS);
    private static final PathGenerator HAN_NON_PALACE_GENERATOR = new NonStraightPathGenerator(NON_PALACE_HAN_PATHS);
    private static final PathGenerator HAN_PALACE_GENERATOR = new NonStraightPathGenerator(PALACE_HAN_PATHS);

    public Jol(Team team) {
        super(team, PieceType.JOL);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        return selectGenerator(source).calculatePath(source, destination);
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return MOVEMENT_STRATEGY.validatePath(pathPieces);
    }

    private PathGenerator selectGenerator(Position source) {
        if (getTeam().isCho()) {
            return selectChoGenerator(source);
        }
        return selectHanGenerator(source);
    }

    private PathGenerator selectChoGenerator(Position source) {
        if (source.isInPalace()) {
            return CHO_PALACE_GENERATOR;
        }
        return CHO_NON_PALACE_GENERATOR;
    }

    private PathGenerator selectHanGenerator(Position source) {
        if (source.isInPalace()) {
            return HAN_PALACE_GENERATOR;
        }
        return HAN_NON_PALACE_GENERATOR;
    }
}
