package janggi.domain.piece;

import janggi.domain.Team;

public abstract class MoveablePiece implements Piece {

    private final Team team;

    public MoveablePiece(Team team) {
        this.team = team;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return this.getClass() == other.getClass();
    }

    @Override
    public Team getTeam() {
        return team;
    }

    protected void validateSameTeam(Piece endPiece) {
        if (endPiece.getTeam() == team) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물로 이동할 수 없습니다.");
        }
    }
}
