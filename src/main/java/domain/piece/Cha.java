package domain.piece;

import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;
import domain.strategy.PoMovementStrategy;

public class Cha extends Piece {
    private static final MovementStrategy movementStrategy = new BlockedMovementStrategy();
    private static final PathGenerator pathGenerator = new StraightPathGenerator();

    public Cha(Team team) {
        super(team, PieceType.CHA);
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
