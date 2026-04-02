package domain.piece;

import common.exception.JanggiException;
import domain.player.Team;

public final class None implements BasicPiece {
    private static final None INSTANCE = new None();

    private None() {
    }

    public static None getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isDifferentTeam(BasicPiece other) {
        return true;
    }

    @Override
    public boolean isDifferentTeam(Team team) {
        return true;
    }

    @Override
    public boolean isType(PieceType type) {
        return false;
    }

    @Override
    public Team getTeam() {
        throw new JanggiException("None은 기물이 아닙니다.");
    }

    @Override
    public PieceType getPieceType() {
        throw new JanggiException("None은 기물이 아닙니다.");
    }

    @Override
    public boolean isNone() {
        return true;
    }
}

