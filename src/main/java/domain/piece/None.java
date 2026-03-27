package domain.piece;

import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.strategy.MovementStrategy;

public class None extends Piece {

    public None() {
        super(Team.NULL, PieceType.NONE);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return null;
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return null;
    }
}
