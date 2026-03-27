package domain.piece;

import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.MovementStrategy;
import domain.strategy.PoMovementStrategy;

public class Po extends Piece {
    private static final MovementStrategy movementStrategy = new PoMovementStrategy();
    private static final PathGenerator pathGenerator = new StraightPathGenerator();

    public Po(Team team) {
        super(team, PieceType.PO);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return pathGenerator;
    }

}
