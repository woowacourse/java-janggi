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
import domain.strategy.GungsungMovementStrategy;
import domain.strategy.MovementStrategy;
import java.util.List;

public class Jang extends Piece {

    private static final List<List<Direction>> PATHS = List.of(
            List.of(NORTH),
            List.of(SOUTH),
            List.of(EAST),
            List.of(WEST)
    );

    private static final MovementStrategy MOVEMENT_STRATEGY = new GungsungMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new CompositePathGenerator(List.of(
            new NonStraightPathGenerator(PATHS),
            new GungsungDiagonalPathGenerator()
    ));

    public Jang(Team team) {
        super(team, PieceType.JANG);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return MOVEMENT_STRATEGY;
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return PATH_GENERATOR;
    }

    @Override
    public boolean isJang() {
        return true;
    }
}
