package domain.piece;

import domain.game.Team;

public abstract class ActivePiece implements Piece {
    private final Team team;
    private final PieceDefinition type;

    protected ActivePiece(Team team, PieceDefinition type) {
        this.team = team;
        this.type = type;
    }

    private boolean isSameTeam(ActivePiece other) {
        return this.team == other.team;
    }

    public boolean isAlly(Piece other) {
        if (!(other instanceof ActivePiece)) {
            return false;
        }
        return isSameTeam((ActivePiece) other);
    }

    protected int forwardDirection() {
        return team.forwardRowDirection();
    }

    @Override
    public String display(PieceAppearance colorizer) {
        return colorizer.colorize(team, type);
    }

    @Override
    public String toString() {
        return type.name();
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }
}
