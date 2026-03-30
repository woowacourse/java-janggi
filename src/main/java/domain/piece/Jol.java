package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.SOUTH;
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

public class Jol extends Piece {

    private static final List<List<Direction>> CHO_PATHS = List.of(
            List.of(NORTH),
            List.of(EAST),
            List.of(WEST));

    private static final List<List<Direction>> HAN_PATHS = List.of(
            List.of(SOUTH),
            List.of(EAST),
            List.of(WEST));

    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator CHO_GENERATOR = new NonStraightPathGenerator(CHO_PATHS);
    private static final PathGenerator HAN_GENERATOR = new NonStraightPathGenerator(HAN_PATHS);

    public Jol(Team team) {
        super(team, PieceType.JOL);
    }

    @Override
    public Path calculatePath(Position source, Position destination) {
        if (getTeam().isCho()) {
            return CHO_GENERATOR.calculatePath(source, destination);
        }
        return HAN_GENERATOR.calculatePath(source, destination);
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return MOVEMENT_STRATEGY.validatePath(pathPieces);
    }
}
