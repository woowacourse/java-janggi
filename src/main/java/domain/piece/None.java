package domain.piece;

import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.strategy.MovementStrategy;

public class None extends Piece {

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return null;
    }

    @Override
    protected PathGenerator getPathGenerator() {
        return null;
    }

    @Override
    public boolean isDifferentTeam(Team team) {
        return false;
    }

    @Override
    public boolean isDifferentTeam(Piece piece) {
        return false;
    }
}
