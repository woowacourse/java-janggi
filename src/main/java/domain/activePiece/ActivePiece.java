package domain.activePiece;

import domain.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.List;

public abstract class ActivePiece implements Piece {
    private final Team team;
    private final PieceType type;

    protected ActivePiece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public boolean isSameTeam(ActivePiece other) {
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

    public abstract List<Position> searchRoute(Position source, Position target);

    @Override
    public String toString() {
        return team.colorize(type.getDisplayName());
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }
}
