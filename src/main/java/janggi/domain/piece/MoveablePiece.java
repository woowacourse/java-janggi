package janggi.domain.piece;

import janggi.domain.Team;

public abstract class MoveablePiece implements Piece {

    protected final Team team;

    public MoveablePiece(Team team) {
        this.team = team;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSameType(PieceType type) {
        return getType() == type;
    }

    @Override
    public double getScore() {
        return getType().getScore();
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    protected void validateSameTeam(Piece endPiece) {
        if (endPiece.isSameTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물로 이동할 수 없습니다.");
        }
    }
}
