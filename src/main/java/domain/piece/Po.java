package domain.piece;

import domain.pathgenerator.NonStraightPathGenerator;
import domain.pathgenerator.PathGenerator;
import domain.pathgenerator.StraightPathGenerator;
import domain.player.Team;
import domain.strategy.BlockedMovementStrategy;
import domain.strategy.MovementStrategy;
import domain.strategy.PoMovementStrategy;

public class Po extends Piece {

    public Po(Team team) {
        super(team, PieceType.PO);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return new PoMovementStrategy();
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return new StraightPathGenerator();
    }

}
