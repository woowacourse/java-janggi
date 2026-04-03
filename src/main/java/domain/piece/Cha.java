package domain.piece;

import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;

public class Cha extends Piece {
    private static final MovementStrategy MOVEMENT_STRATEGY = new BlockedMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new StraightPathGenerator();

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return MOVEMENT_STRATEGY;
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return PATH_GENERATOR;
    }
}
