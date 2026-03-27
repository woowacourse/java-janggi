package domain.piece;

import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;

public class Cha extends Piece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return new BlockedMovementStrategy();
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return new StraightPathGenerator();
    }
}
