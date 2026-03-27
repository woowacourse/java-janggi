package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.WEST;

import domain.direction.Direction;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;
import java.util.List;

public class Jol extends Piece {

    private static final List<List<Direction>> choPaths = List.of(
            List.of(NORTH),
            List.of(EAST),
            List.of(WEST));

    private static final List<List<Direction>> hanPaths = List.of(
            List.of(SOUTH),
            List.of(EAST),
            List.of(WEST));

    private static final MovementStrategy STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator CHO_GENERATOR = new NonStraightPathGenerator(choPaths);
    private static final PathGenerator HAN_GENERATOR = new NonStraightPathGenerator(hanPaths);

    public Jol(Team team) {
        super(team, PieceType.JOL);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return STRATEGY;
    }

    @Override
    protected PathGenerator getPathGenerator() {
        if (getTeam().isCho()) {
            return CHO_GENERATOR;
        }
        return HAN_GENERATOR;
    }
}
