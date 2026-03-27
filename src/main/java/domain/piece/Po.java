package domain.piece;

import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.MovementStrategy;
import domain.strategy.PoMovementStrategy;

public class Po extends Piece {
    private static final MovementStrategy MOVEMENT_STRATEGY = new PoMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new StraightPathGenerator();

    public Po(Team team) {
        super(team, PieceType.PO);
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
