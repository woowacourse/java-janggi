package domain.piece;

import common.JanggiException;
import domain.pathgenerator.PathGenerator;
import domain.player.Team;
import domain.strategy.MovementStrategy;

public class None extends Piece {

    private static final String DOES_NOT_HAVE_TEAM = "빈 칸에는 팀이 없습니다.";
    private static final String CAN_NOT_MOVE = "빈 칸에는 기물이 없어 이동할 수 없습니다.";

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        throw new JanggiException(CAN_NOT_MOVE);
    }

    @Override
    protected PathGenerator getPathGenerator() {
        throw new JanggiException(CAN_NOT_MOVE);
    }

    @Override
    public Team getTeam() {
        throw new JanggiException(DOES_NOT_HAVE_TEAM);
    }

    @Override
    public boolean isNone() {
        return true;
    }
}
