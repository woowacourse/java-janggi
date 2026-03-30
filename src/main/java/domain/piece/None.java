package domain.piece;

import common.exception.JanggiException;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.strategy.MovementStrategy;

public class None extends Piece {

    public static final String DOESNT_HAVE_TEAM = "빈 칸에는 팀이 없습니다.";

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
    public Team getTeam() {
        throw new JanggiException(DOESNT_HAVE_TEAM);
    }
}
