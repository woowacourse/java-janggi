package domain.piece;

import domain.pathgenerator.CompositePathGenerator;
import domain.pathgenerator.GungsungDiagonalPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.MovementStrategy;
import domain.strategy.PoMovementStrategy;
import java.util.List;

public class Po extends Piece {
    private static final MovementStrategy MOVEMENT_STRATEGY = new PoMovementStrategy();
    private static final PathGenerator PATH_GENERATOR = new CompositePathGenerator(List.of(
            new StraightPathGenerator(),
            new GungsungDiagonalPathGenerator()
    ));

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

    @Override
    public boolean isPo() {
        return true;
    }
}
