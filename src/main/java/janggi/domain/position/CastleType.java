package janggi.domain.position;

import janggi.domain.space.piece.Team;

public enum CastleType {

    BOTTOM_CORNER(Team.CHO), BOTTOM_CENTER(Team.CHO), BOTTOM_SIDE(Team.CHO),
    TOP_CORNER(Team.HAN), TOP_CENTER(Team.HAN), TOP_SIDE(Team.HAN),
    ;

    private final Team team;

    CastleType(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(CastleType other) {
        return this.team == other.team;
    }

    public boolean isSide() {
        return this == CastleType.BOTTOM_SIDE ||
                this == CastleType.TOP_SIDE;
    }
}
