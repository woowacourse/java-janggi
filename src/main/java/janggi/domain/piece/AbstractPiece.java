package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Palace;

public abstract class AbstractPiece implements Piece {

    private final Team team;

    protected AbstractPiece(Team team) {
        this.team = team;
    }

    protected Palace ownPalace() {
        return Palace.of(team);
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other.isSameType(getType());
    }

    @Override
    public boolean isSameTeam(Piece other) {
        return other.isSame(team);
    }

    @Override
    public boolean isSame(Team team) {
        return this.team == team;
    }

    protected void validateSameTeam(Piece endPiece) {
        if (isSameTeam(endPiece)) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물로 이동할 수 없습니다.");
        }
    }
}
