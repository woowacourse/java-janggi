package domain.piece;

import static domain.direction.Direction.EAST;
import static domain.direction.Direction.NORTH;
import static domain.direction.Direction.SOUTH;
import static domain.direction.Direction.WEST;

import domain.direction.Direction;
import domain.pathgenerator.CompositePathGenerator;
import domain.pathgenerator.GungsungDiagonalPathGenerator;
import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
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

    private static final MovementStrategy STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator CHO_GENERATOR = new CompositePathGenerator(List.of(
            new NonStraightPathGenerator(CHO_PATHS),
            new GungsungDiagonalPathGenerator()
    ));
    private static final PathGenerator HAN_GENERATOR = new CompositePathGenerator(List.of(
            new NonStraightPathGenerator(HAN_PATHS),
            new GungsungDiagonalPathGenerator()
    ));

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
